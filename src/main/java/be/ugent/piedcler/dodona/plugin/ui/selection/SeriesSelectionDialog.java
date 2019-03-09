/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.ui.selection;

import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;
import be.ugent.piedcler.dodona.plugin.util.observable.ObservableValue;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select a series.
 */
public class SeriesSelectionDialog extends SelectionDialog<Series> {
	private JPanel contentPane;
	private JBList<Series> seriesList;
	
	private final ObservableValue<Series> selectedSeries;
	
	/**
	 * SelectSeriesDialog constructor.
	 *
	 * @param series the series to select from
	 */
	public SeriesSelectionDialog(final Collection<Series> series) {
		this.selectedSeries = new ObservableValue<>(null);
		
		this.setContentPane(this.contentPane);
		this.setModal(true);
		
		this.seriesList.addListSelectionListener(e -> this.selectedSeries.setValue(this.seriesList.getSelectedValue()));
		this.seriesList.setCellRenderer(new SeriesListRenderer());
		this.seriesList.setEmptyText("No series were found in this course.");
		this.seriesList.setModel(new CollectionListModel<>(series));
		this.seriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		if (series.isEmpty()) {
			this.contentPane.setPreferredSize(new Dimension(250, -1));
		} else {
			this.contentPane.setPreferredSize(new Dimension(450, 300));
		}
	}
	
	@Override
	public void addListener(@Nonnull final SelectedItemListener<Series> listener) {
		this.selectedSeries.addListener(listener::onItemSelected);
	}
	
	@Nullable
	@Override
	public Series getSelectedItem() {
		return this.selectedSeries.getValue();
	}
	
	@Override
	public boolean hasItems() {
		return !this.seriesList.isEmpty();
	}
}
