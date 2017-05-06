package com.tech11.spg;

import java.io.StringWriter;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StaticPageGeneratorFMIncludeTest {

	StaticPageGenerator spg;
	StringWriter result;

	@Before
	public void init() {
		result = new StringWriter();
		String templateFolder = this.getClass().getResource("/").getFile();

		spg = new StaticPageGenerator()
				.setOutputWriter(result)
				.setTemplateFolder(templateFolder)
				.setMessageFolder(templateFolder)
				.processSingleTemplate("test2.html.ftl");
	}

	@Test
	public void shouldIncludeTest1() {
		spg.setLanguage(Locale.ENGLISH).run();
		Assert.assertEquals("first line\r\nHellothird line", result.toString());
	}
}
