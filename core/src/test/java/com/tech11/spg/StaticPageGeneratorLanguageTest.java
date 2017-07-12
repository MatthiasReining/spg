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
				.setDataFolder(templateFolder)
				.processSingleTemplate("test1.html.ftl");
	}

	@Test
	public void shouldUseEnglishAsDefaultMessage() {
		spg.setSingleTargetLanguage(Locale.ENGLISH).run();
		Assert.assertEquals("Hello", result.toString());
	}
	
	@Test
	public void shouldUseEnglishMessage() {
		spg.setSingleTargetLanguage(Locale.ENGLISH).run();		
		Assert.assertEquals("Hello", result.toString());
	}
	
	@Test
	public void shouldUseGermanMessage() {
		spg.setSingleTargetLanguage(Locale.GERMAN).run();		
		Assert.assertEquals("Hallo", result.toString());
	}
}
