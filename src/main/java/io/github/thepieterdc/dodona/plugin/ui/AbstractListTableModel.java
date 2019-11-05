/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Model for tables..
 */
public abstract class AbstractListTableModel<T> extends AbstractTableModel {
	private final Function<T, Object>[] columnAccessors;
	private final String[] columnNames;
	private final Class<?>[] columnTypes;
	
	private final List<T> items;
	
	/**
	 * AbstractListTableModel constructor.
	 *
	 * @param columnNames     the names for the columns
	 * @param columnTypes     the types of the values in the columns
	 * @param columnAccessors accessors for values
	 */
	public AbstractListTableModel(final String[] columnNames,
	                              final Class<?>[] columnTypes,
	                              final Function<T, Object>[] columnAccessors) {
		super();
		this.columnAccessors = columnAccessors.clone();
		this.columnNames = columnNames.clone();
		this.columnTypes = columnTypes.clone();
		this.items = new ArrayList<>();
	}
	
	@Nullable
	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if (columnIndex > this.columnTypes.length) {
			return null;
		}
		return this.columnTypes[columnIndex];
	}
	
	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}
	
	@Nullable
	@Override
	public String getColumnName(final int column) {
		if (column > this.columnNames.length) {
			return null;
		}
		return this.columnNames[column];
	}
	
	/**
	 * Gets the item at the given row.
	 *
	 * @param rowIndex the index of the row
	 * @return the item if exists
	 */
	@Nonnull
	public Optional<T> getItemAtRow(final int rowIndex) {
		return Optional.of(rowIndex)
			.filter(i -> i < this.getRowCount())
			.map(this.items::get);
	}
	
	@Override
	public int getRowCount() {
		return this.items.size();
	}
	
	@Nullable
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (columnIndex < this.columnNames.length) {
			return this.getItemAtRow(rowIndex)
				.map(item -> this.columnAccessors[columnIndex].apply(item))
				.orElse(null);
		}
		return null;
	}
	
	/**
	 * Replaces the items in the list.
	 *
	 * @param nw the items to set
	 */
	public void replaceItems(final Collection<? extends T> nw) {
		this.items.clear();
		this.items.addAll(nw);
		this.fireTableDataChanged();
	}
}
