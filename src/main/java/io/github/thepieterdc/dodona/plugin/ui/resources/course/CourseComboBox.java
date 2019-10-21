/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.resources.course;

import io.github.thepieterdc.dodona.plugin.ui.listeners.ItemSelectedListener;
import io.github.thepieterdc.dodona.plugin.ui.resources.AbstractResourceComboBox;
import io.github.thepieterdc.dodona.resources.Course;

import java.util.Collection;
import java.util.Comparator;

/**
 * A combo box containing courses.
 */
public class CourseComboBox extends AbstractResourceComboBox<Course> {
	/**
	 * CourseComboBox constructor.
	 *
	 * @param listener the listener
	 */
	public CourseComboBox(final ItemSelectedListener<? super Course> listener) {
		super(new CourseNameRenderer(), listener);
	}
	
	@Override
	public void setResources(final Collection<? extends Course> resources) {
		// Remove the previous items.
		this.removeAllItems();
		
		// Set the new items.
		resources.stream().sorted(Comparator.reverseOrder()).forEach(this::addItem);
	}
}
