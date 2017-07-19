package com.tech11.spg.translationhelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.tech11.spg.dataloader.DataLoader;
import com.tech11.spg.dataloader.JsonStructLoader;

public class Json2Flat {
	
	String csvFilePath;

	public void json2Flat(File folder, Locale locale) throws IOException {
		DataLoader dl = new JsonStructLoader(folder);
		Map<String, Object> data = dl.loadMessages(locale).getData();

		File dataFile = dl.getMessagesFile(locale);
		csvFilePath = dataFile.getAbsolutePath().replaceFirst(".json$", ".csv");

		try (FileWriter fw = new FileWriter(csvFilePath)) {

			iterateMapAndPublish(fw, "", data);
		}
	}
	
	public void mergeCSV2Json(File folder, Locale locale) throws IOException {
		DataLoader dl = new JsonStructLoader(folder);		
		File dataFile = dl.getMessagesFile(locale);
		csvFilePath = dataFile.getAbsolutePath().replaceFirst(".json$", ".csv");
		
		throw new java.lang.UnsupportedOperationException();

	}
	
	public String getCSVFilePath() {
		return csvFilePath;
	}

	void iterateMapAndPublish(FileWriter fw, String jsonPath, ArrayList<Object> al) {
		final AtomicInteger counter = new AtomicInteger(0);
		al.stream().forEach(e -> {
			String entryJsonPath = jsonPath + "[" + (counter.getAndIncrement()) + "]";

			if (e instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> subMap = (Map<String, Object>) e;
				iterateMapAndPublish(fw, entryJsonPath + ".", subMap);
			} else if (e instanceof ArrayList) {
				@SuppressWarnings("unchecked")
				ArrayList<Object> subList = (ArrayList<Object>) e;
				iterateMapAndPublish(fw, entryJsonPath, subList);

			} else {
				publish(fw, entryJsonPath, e);
			}
		});
	}

	void iterateMapAndPublish(FileWriter fw, String jsonPath, Map<String, Object> map) {
		map.entrySet().stream().forEach(e -> {
			String entryJsonPath = jsonPath + e.getKey();
			if (e.getValue() instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> subMap = (Map<String, Object>) e.getValue();
				iterateMapAndPublish(fw, entryJsonPath + ".", subMap);
			} else if (e.getValue() instanceof ArrayList) {
				@SuppressWarnings("unchecked")
				ArrayList<Object> subList = (ArrayList<Object>) e.getValue();
				iterateMapAndPublish(fw, entryJsonPath, subList);
			} else {
				publish(fw, entryJsonPath, e.getValue());
			}
		});
	}

	void publish(FileWriter fw, String key, Object value) {
		try {
			fw.write(key + ";\"" + value + "\"\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
