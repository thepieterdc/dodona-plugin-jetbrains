/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select a series.
 */
public class SeriesSelectionDialog extends SelectionDialog<Series> {
	private JPanel contentPane;
	private JBList<Series> seriesList;
	
	private final SimpleObjectProperty<Series> selectedSeries;
	
	/**
	 * SelectSeriesDialog constructor.
	 *
	 * @param series the series to select from
	 */
	public SeriesSelectionDialog(final Collection<Series> series) {
		this.selectedSeries = new SimpleObjectProperty<>(null);
		
		this.createComponents();
		this.seriesList.addListSelectionListener(e -> this.selectedSeries.set(this.seriesList.getSelectedValue()));
		this.seriesList.setCellRenderer(new SeriesListRenderer());
		this.seriesList.setEmptyText("No series were found in this course.");
		this.seriesList.setModel(new CollectionListModel<>(series));
		this.seriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Override
	public void addListener(@Nonnull final SelectedItemListener<Series> listener) {
		this.selectedSeries.addListener((o, od, nw) -> listener.onItemSelected(nw));
	}
	
	/**
	 * Creates the form components.
	 */
	private void createComponents() {
		this.setContentPane(this.contentPane);
		this.setModal(true);
	}
	
	@Nullable
	@Override
	public Series getSelectedItem() {
		return this.selectedSeries.get();
	}
	
	@Override
	public boolean hasItems() {
		return !this.seriesList.isEmpty();
	}
}
