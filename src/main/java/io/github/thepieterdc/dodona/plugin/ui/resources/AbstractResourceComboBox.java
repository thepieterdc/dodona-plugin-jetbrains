/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources;

import com.intellij.openapi.ui.ComboBox;
import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener;
import io.github.thepieterdc.dodona.resources.Resource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A combo box containing resources.
 */
public abstract class AbstractResourceComboBox<T extends Resource> extends ComboBox<T>
	implements ResourceSelector<T> {
	private final ItemSelectedListener<? super T> listener;
	private final DefaultComboBoxModel<T> model;
	
	/**
	 * AbstractResourceComboBox constructor.
	 *
	 * @param renderer the value renderer
	 * @param listener the selection listener
	 */
	protected AbstractResourceComboBox(final ListCellRenderer<T> renderer,
	                                   final ItemSelectedListener<? super T> listener) {
		super(0);
		this.listener = listener;
		this.model = new DefaultComboBoxModel<>();
		this.addActionListener(e -> this.listener.onItemSelected(
			this.getSelectedResource().orElse(null)
		));
		this.setModel(this.model);
		this.setRenderer(renderer);
	}
	
	@Nonnull
	@Override
	public Optional<T> getSelectedResource() {
		return Optional.ofNullable(this.getItemAt(this.getSelectedIndex()));
	}
	
	@Nonnull
	@Override
	public Optional<Long> getSelectedResourceId() {
		return this.getSelectedResource().map(Resource::getId);
	}
	
	@Override
	public void setResources(final Collection<? extends T> resources) {
		// Remove the previous items.
		this.model.removeAllElements();
		
		// Set the new items.
		resources.stream().sorted().forEach(this.model::addElement);
		
		// Auto-select the first item.
		if (!resources.isEmpty()) {
			this.setSelectedIndex(0);
		}
	}
	
	@Override
	public void setSelectedResource(final Predicate<? super T> predicate) {
		final int items = this.model.getSize();
		for (int i = 0; i < items; ++i) {
			if (predicate.test(this.model.getElementAt(i))) {
				this.setSelectedIndex(i);
				return;
			}
		}
		
		// Clear the selection.
		this.setSelectedItem(null);
	}
	
	@Override
	public void setSelectedResource(@Nullable final T resource) {
		Optional.ofNullable(resource).ifPresent(this::setSelectedItem);
	}
}
