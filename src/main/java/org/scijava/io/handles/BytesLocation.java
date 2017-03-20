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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Set;

import org.scijava.io.AbstractLocation;
import org.scijava.io.Location;

/**
 * {@link Location} backed by a {@link ByteBuffer}.
 * <p>
 * Note that the backing {@link ByteBuffer} reference is mutable, and might
 * change to a different, larger buffer if a sufficiently large amount of data
 * is written to this location via a wrapping {@link BytesHandle}.
 * </p>
 * 
 * @author Curtis Rueden
 * @see BytesHandle
 */
public class BytesLocation extends AbstractLocation {

	private ByteBuffer bytes;

	public BytesLocation(final ByteBuffer bytes) {
		this.bytes = bytes;
	}

	public BytesLocation(final byte[] bytes) {
		this(ByteBuffer.wrap(bytes));
	}

	public BytesLocation(final byte[] bytes, final int offset, final int length) {
		this(ByteBuffer.wrap(bytes, offset, length));
	}

	/**
	 * Creates a {@link BytesLocation} backed by a {@link ByteBuffer} with the
	 * specified initial capacity.
	 */
	public BytesLocation(final int initialCapacity) {
		this(ByteBuffer.allocate(initialCapacity));
	}

	// -- ByteArrayLocation methods --

	/** Gets the associated {@link ByteBuffer}. */
	public ByteBuffer getByteBuffer() {
		return bytes;
	}

	/** Sets the associated {@link ByteBuffer}. */
	public void setByteBuffer(final ByteBuffer bytes) {
		this.bytes = bytes;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
}