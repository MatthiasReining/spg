package com.tech11.spg;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LocaleDetector {

	private LocaleDetector() {
	}

	public static Set<Locale> extractLocales(File directory) {
		String[] fileNames = directory.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".json") || name.endsWith(".properties"))
					return true;
				return false;
			}
		});
		return extractLocales(fileNames);
	}

	public static Set<Locale> extractLocales(String[] fileNames) {
		Set<Locale> locales = new HashSet<>();

		for (String fileName : fileNames) {
			if (!(fileName.contains(".") && fileName.contains("_")))
				continue;

			String languageTag = fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('.'));
			locales.add(Locale.forLanguageTag(languageTag));
		}
		return locales;
	}
}
