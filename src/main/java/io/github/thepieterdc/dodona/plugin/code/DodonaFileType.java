/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */
package io.github.thepieterdc.dodona.plugin.code;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.annotations.NonNls;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

/**
 * File types used in Dodona.
 */
public enum DodonaFileType {
	HASKELL("hs"),
	HTML("html"),
	JAVA("java"),
	JAVASCRIPT("js"),
	PROLOG("pl"),
	PYTHON("py");
	
	private final String extension;
	private final FileType fileType;
	
	/**
	 * DodonaFileType constructor.
	 *
	 * @param extension the file extension
	 */
	DodonaFileType(@NonNls final String extension) {
		this.extension = extension;
		this.fileType = FileTypeRegistry.getInstance()
			.getFileTypeByExtension(extension);
	}
	
	/**
	 * Gets the file type corresponding to the given extension.
	 *
	 * @param extension the extension to match
	 * @return the file type
	 */
	@Nonnull
	public static Optional<FileType> forExtension(final String extension) {
		return Arrays.stream(DodonaFileType.values())
			.filter(dft -> dft.extension.equals(extension))
			.map(dft -> dft.fileType)
			.findAny();
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Nonnull
	public String getExtension() {
		return this.extension;
	}
	
	@Override
	public String toString() {
		return String.format("DodonaFileType[extension=%s]", this.extension);
	}
}
