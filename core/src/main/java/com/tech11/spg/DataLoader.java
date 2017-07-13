package com.tech11.spg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class DataLoader {

	protected String msgFileName;
	Map<String, Object> data = new HashMap<>();

	DataLoader(File tmplFolder) throws IOException {
		String defaultLanguage = SystemConfiguration.instance().getDefaultLang();
		msgFileName = tmplFolder.getAbsolutePath() + File.separator + getDataFileName();
		loadDefault(defaultLanguage);
	}

	public <T extends DataLoader> T loadDefault(String lang) throws IOException {
		//FIXME return loadLang(lang);
		return null;
	}

	public <T extends DataLoader> T loadLang(Locale locale) throws IOException {
		return loadLang(locale, null);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends DataLoader> T loadLang(Locale locale, Locale fallbackLocale) throws IOException {
		data.putAll( loadData(locale, fallbackLocale));
		return (T) this;
	}

	abstract Map<String, Object> loadData(Locale locale, Locale fallbackLang) throws IOException;
	abstract String getDataFileName();

	public Map<String, Object> getData() {
		return data;
	}

}