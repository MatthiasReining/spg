package com.tech11.spg.dataloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

public class JsonStructLoader extends DataLoader {

	public JsonStructLoader(File tmplFolder) throws IOException {
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
		

		File file = getMessagesFile(locale);
		if (file == null)
			return new HashMap<>();

		String data = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())), "UTF-8");
		JSONObject json = new JSONObject(data);
		return json.toMap();
	}

	@Override
	String getDataFileName() {
		return "messages";
	}

	@Override
	String getFileExtension() {
		return ".json";
	}

}
