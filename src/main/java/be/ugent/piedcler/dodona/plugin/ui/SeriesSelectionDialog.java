/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
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
	
	@Nullable
	private Series selectedSeries;
	
	/**
	 * SelectSeriesDialog constructor.
	 *
	 * @param series the series to select from
	 */
	public SeriesSelectionDialog(final Collection<Series> series) {
		this.createComponents();
		this.seriesList.addListSelectionListener(e -> this.selectedSeries = this.seriesList.getSelectedValue());
		this.seriesList.setCellRenderer(new SeriesListRenderer());
		this.seriesList.setEmptyText("No series were found in this course.");
		this.seriesList.setModel(new CollectionListModel<>(series));
		this.seriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Override
	public void addListener(@Nonnull final SelectedItemListener<Series> listener) {
		//TODO implement me
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
		return this.selectedSeries;
	}
	
	@Override
	public boolean hasItems() {
		return !this.seriesList.isEmpty();
	}
}
