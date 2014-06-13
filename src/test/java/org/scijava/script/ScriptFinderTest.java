/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2014 Board of Regents of the University of
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

package org.scijava.script;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scijava.Context;
import org.scijava.plugin.Plugin;
import org.scijava.test.TestUtils;
import org.scijava.util.FileUtils;

/**
 * Tests the {@link ScriptFinder}.
 * 
 * @author Curtis Rueden
 */
public class ScriptFinderTest {

	private static File scriptsDir;

	// -- Test setup --

	@BeforeClass
	public static void setUp() throws IOException {
		scriptsDir = TestUtils.createTemporaryDirectory("script-finder-");
		TestUtils.createPath(scriptsDir, "ignored.foo");
		TestUtils.createPath(scriptsDir, "Plugins/quick.foo");
		TestUtils.createPath(scriptsDir, "Plugins/brown.foo");
		TestUtils.createPath(scriptsDir, "Plugins/fox.foo");
		TestUtils.createPath(scriptsDir, "Plugins/The_Lazy_Dog.foo");
		TestUtils.createPath(scriptsDir, "Math/add.foo");
		TestUtils.createPath(scriptsDir, "Math/subtract.foo");
		TestUtils.createPath(scriptsDir, "Math/multiply.foo");
		TestUtils.createPath(scriptsDir, "Math/divide.foo");
		TestUtils.createPath(scriptsDir, "Math/Trig/cos.foo");
		TestUtils.createPath(scriptsDir, "Math/Trig/sin.foo");
		TestUtils.createPath(scriptsDir, "Math/Trig/tan.foo");
	}

	@AfterClass
	public static void tearDown() {
		FileUtils.deleteRecursively(scriptsDir);
	}

	// -- Unit tests --

	@Test
	public void testFindScripts() {
		final Context context = new Context(ScriptService.class);
		final ScriptService scriptService = context.service(ScriptService.class);
		scriptService.addScriptDirectory(scriptsDir);

		final ScriptFinder scriptFinder = new ScriptFinder(scriptService);

		final ArrayList<ScriptInfo> scripts = new ArrayList<ScriptInfo>();
		scriptFinder.findScripts(scripts);
		assertEquals(11, scripts.size());
		assertMenuPath("Math > add", scripts, 0);
		assertMenuPath("Math > divide", scripts, 1);
		assertMenuPath("Math > multiply", scripts, 2);
		assertMenuPath("Math > subtract", scripts, 3);
		assertMenuPath("Math > Trig > cos", scripts, 4);
		assertMenuPath("Math > Trig > sin", scripts, 5);
		assertMenuPath("Math > Trig > tan", scripts, 6);
		assertMenuPath("Plugins > brown", scripts, 7);
		assertMenuPath("Plugins > fox", scripts, 8);
		assertMenuPath("Plugins > quick", scripts, 9);
		assertMenuPath("Plugins > The Lazy Dog", scripts, 10);
	}

	// -- Helper methods --

	private void assertMenuPath(final String menuString,
		final ArrayList<ScriptInfo> scripts, final int i)
	{
		assertEquals(menuString, scripts.get(i).getMenuPath().getMenuString());
	}

	// -- Helper classes --

	/** "Handles" scripts with .foo extension. */
	@Plugin(type = ScriptLanguage.class)
	public static class FooScriptLanguage extends AbstractScriptLanguage {

		@Override
		public List<String> getExtensions() {
			return Arrays.asList("foo");
		}

		@Override
		public ScriptEngine getScriptEngine() {
			// NB: Should never be called by the unit tests.
			throw new IllegalStateException();
		}

	}

}
