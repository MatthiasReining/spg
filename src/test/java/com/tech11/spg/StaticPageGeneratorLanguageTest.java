package com.tech11.spg;

import java.io.StringWriter;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StaticPageGeneratorLanguageTest {

	StaticPageGenerator spg;
	StringWriter result;

	@Before
	public void init() {
		result = new StringWriter();
		String templateFolder = this.getClass().getResource("/").getFile();

		spg = new StaticPageGenerator()
				.setOutputWriter(result)
				.setTemplateFolder(templateFolder)
				.processSingleTemplate("test1.html.ftl");
	}

	@Test
	public void shouldUseEnglishAsDefaultMessage() {
		spg.run();		
		Assert.assertEquals("Hello", result.toString());
	}
	
	@Test
	public void shouldUseEnglishMessage() {
		spg.setLanguage(Locale.ENGLISH).run();		
		Assert.assertEquals("Hello", result.toString());
	}
	
	@Test
	public void shouldUseGermanMessage() {
		spg.setLanguage(Locale.GERMAN).run();		
		Assert.assertEquals("Hallo", result.toString());
	}
}
