package com.tech11.spg.dataloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class MessageLoader extends DataLoader {

	public MessageLoader(File tmplFolder) throws IOException {
		super(tmplFolder);
	}

	@Override
	Map<String, Object> loadData(Locale locale, Locale fallbackLocale) throws IOException {
		Map<String, Object> p = new HashMap<>();

		if (fallbackLocale != null)
			p.putAll(loadData(fallbackLocale));

		p.putAll(loadData(locale));

		return p;
	}

	Map<String, Object> loadData(Locale locale) throws IOException {

		Map<String, Object> result = new HashMap<>();
		File file = getMessagesFile(locale);
		if (file == null)
			return new HashMap<>();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		Properties p = new Properties();
		p.load(in);

		// convert to Map<String,Object>
		p.entrySet().forEach(entry -> result.put(entry.getKey().toString(), entry.getValue()));

		return result;
	}

	@Override
	String getDataFileName() {
		return "messages";
	}

	@Override
	String getFileExtension() {
		return ".properties";
	}

}
