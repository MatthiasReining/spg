package com.tech11.spg.dataloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

public class JsonStructLoader extends DataLoader {

	public JsonStructLoader(File tmplFolder) throws IOException {
		super(tmplFolder);
	}
	
	JsonStructLoader() {
		//only for unit testing
	}

	@Override
	Map<String, Object> loadData(Locale locale, Locale fallbackLocale) throws IOException {

		Map<String, Object> p = new HashMap<>();

		if (fallbackLocale != null)
			p.putAll(loadData(fallbackLocale));

		updateData(p, loadData(locale));

		return p;
	}

	void updateData(Map<String, Object> base, Map<String, Object> newMap) {
		for (Entry<String, Object> e : newMap.entrySet()) {
			String key = e.getKey();
			Object value = e.getValue();
			Object baseObj = base.get(key);

			if (value instanceof Map && baseObj != null && baseObj instanceof Map) {

				@SuppressWarnings("unchecked")
				Map<String, Object> baseMap = (Map<String, Object>) baseObj;
				@SuppressWarnings("unchecked")
				Map<String, Object> newMapEntry = (Map<String, Object>) value;

				updateData(baseMap, newMapEntry);
			} else
				base.put(e.getKey(), e.getValue());
		}
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
