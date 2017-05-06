package com.tech11.spg;

import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StaticPageGeneratorFullFolder {

	StaticPageGenerator spg;
	StringWriter result;

	@Before
	public void init() {
		result = new StringWriter();
		String templateFolder = this.getClass().getResource("/").getFile();

		spg = new StaticPageGenerator()
				.setOutputWriter(result)
				.setTemplateFolder(templateFolder);				
	}

	@Test
	public void shouldProcessAllTemplatesinFolder() {
		spg.run();
		Assert.assertEquals("Hellofirst line\r\nHellothird line", result.toString());
	}
	
}
