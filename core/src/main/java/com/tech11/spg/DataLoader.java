package com.tech11.spg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
		return loadLang(lang);
	}

	@SuppressWarnings("unchecked")
	public <T extends DataLoader> T loadLang(String lang) throws IOException {
		Map<String, Object> p = loadData(lang);
		data.putAll(p);		
		return (T) this;
	}

	abstract Map<String, Object> loadData(String lang) throws IOException;
	abstract String getDataFileName();

	public Map<String, Object> getData() {
		return data;
	}

}