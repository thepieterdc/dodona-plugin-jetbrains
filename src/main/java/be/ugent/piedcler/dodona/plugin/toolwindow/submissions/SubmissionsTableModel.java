/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow.submissions;

import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.TableViewModel;
import com.intellij.util.xml.ui.BooleanColumnInfo;
import com.intellij.util.xml.ui.StringColumnInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Collections;
import java.util.List;

/**
 * Renders a list of submissions.
 */
public class SubmissionsTableModel extends TableViewModel<PartialSubmission> {
	private final ColumnInfo[] columns;
	private final List<PartialSubmission> submissions;
	
	/**
	 * SubmissionsTableModel constructor.
	 *
	 * @param submissions list of submissions to display
	 */
	SubmissionsTableModel(@Nonnull final List<PartialSubmission> submissions) {
		this.columns = new ColumnInfo[]{
			new BooleanColumnInfo("Accepted"),
			new StringColumnInfo("Summary"),
			new StringColumnInfo("Timestamp")
		};
		this.submissions = submissions;
	}
	
	@Override
	public int getColumnCount() {
		return this.columns.length;
	}
	
	@Override
	public ColumnInfo[] getColumnInfos() {
		return this.columns;
	}
	
	@Override
	public String getColumnName(final int column) {
		return this.columns[column].getName();
	}
	
	@Override
	@Nullable
	public RowSorter.SortKey getDefaultSortKey() {
		return null;
	}
	
	@NotNull
	@Override
	public List<PartialSubmission> getItems() {
		return Collections.unmodifiableList(this.submissions);
	}
	
	@Override
	public int getRowCount() {
		return this.submissions.size();
	}
	
	@Override
	public Object getRowValue(int row) {
		return this.submissions.get(row);
	}
	
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		switch (columnIndex) {
			case 0:
				return this.submissions.get(rowIndex).isAccepted();
			case 1:
				return this.submissions.get(rowIndex).getSummary();
			case 2:
				return this.submissions.get(rowIndex).getCreatedAt();
		}
		
		return null;
	}
	
	@Override
	public boolean isSortable() {
		return true;
	}
	
	@Override
	public void setItems(@NotNull final List<PartialSubmission> submissions) {
		this.submissions.clear();
		this.submissions.addAll(submissions);
		this.fireTableDataChanged();
	}
	
	@Override
	public void setSortable(boolean aBoolean) {
		//
	}
}
