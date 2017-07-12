package com.tech11.spg;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


public class LocaleDetectorTest {

	@Test
	public void shouldDetect_de_de() {		
		String[] fileNames = {"test_de-DE.json", "abc_de-de.json", "messages_de-de.properties"};
		
		Set<Locale> locales = LocaleDetector.extractLocales(fileNames);
		System.out.println(locales);
		Assert.assertEquals(1,  locales.size());
		Assert.assertTrue(locales.contains(new Locale("de","de")));		
	}
	
	@Test
	public void shouldDetectMix() {		
		String[] fileNames = {"test_de-DE.json", "abc_de.json", "messages_en.properties", "messages_de-de.properties"};
		
		Set<Locale> locales = LocaleDetector.extractLocales(fileNames);
		Assert.assertEquals(3,  locales.size());
		System.out.println(locales);
		Assert.assertTrue(locales.contains(new Locale("de","de")));
		Assert.assertTrue(locales.contains(new Locale("de")));
		Assert.assertTrue(locales.contains(new Locale("en")));
	}
}
