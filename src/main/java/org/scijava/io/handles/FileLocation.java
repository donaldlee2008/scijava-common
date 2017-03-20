/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2017 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.io.handles;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.scijava.io.AbstractLocation;
import org.scijava.io.Location;

/**
 * {@link Location} backed by a {@link File} on disk.
 *
 * @author Curtis Rueden
 * @see FileHandle
 */
public class FileLocation extends AbstractLocation {

	private final File file;

	public FileLocation(final File file) {
		this.file = file;
	}

	public FileLocation(final String path) {
		this(new File(path));
	}

	public FileLocation(final URI path) {
		this(new File(path));
	}

	// -- FileLocation methods --

	/** Gets the associated {@link File}. */
	public File getFile() {
		return file;
	}

	// -- Location methods --

	@Override
	public URI getURI() {
		return getFile().toURI();
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public boolean isBrowsable() {
		return true;
	}

	@Override
	public Location getParent() throws IOException {
		return new FileLocation(file.getParentFile());
	}

	@Override
	public Set<Location> getChildren() throws IOException {
		File[] files = file.listFiles();
		if (files == null) {
			return Collections.emptySet();
		}

		Set<Location> out = new HashSet<>(files.length);
		for (File child : files) {
			out.add(new FileLocation(child));
		}
		return out;
	}
}
