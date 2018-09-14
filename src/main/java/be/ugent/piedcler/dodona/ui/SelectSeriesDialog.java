/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.ui;

import be.ugent.piedcler.dodona.dto.Series;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select a series.
 */
public class SelectSeriesDialog extends JDialog {
	private JPanel contentPane;
	private JBList<Series> seriesList;
	
	@Nullable
	private Series selectedSeries;
	
	/**
	 * SelectSeriesDialog constructor.
	 *
	 * @param series the series to select from
	 */
	public SelectSeriesDialog(final Collection<Series> series) {
		this.createComponents();
		this.seriesList.addListSelectionListener(e -> this.selectedSeries = this.seriesList.getSelectedValue());
		this.seriesList.setCellRenderer(new SeriesListRenderer());
		this.seriesList.setEmptyText("No series were found in this course.");
		this.seriesList.setModel(new CollectionListModel<>(series));
		this.seriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**
	 * Creates the form components.
	 */
	private void createComponents() {
		this.setContentPane(this.contentPane);
		this.setModal(true);
	}
	
	/**
	 * Gets the selected series.
	 *
	 * @return the selected series
	 */
	@Nullable
	public Series getSelectedSeries() {
		return this.selectedSeries;
	}
}
