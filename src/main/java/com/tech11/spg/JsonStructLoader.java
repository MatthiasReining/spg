package com.tech11.spg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonStructLoader extends DataLoader<JsonStructLoader> {

	public JsonStructLoader(File tmplFolder) throws IOException {
		super(tmplFolder);
	}

	@Override
	Map<String, Object> loadData(String lang) throws IOException {
		
		Map<String, Object> p = new HashMap<>();

		String langAppendix = "";
		if (lang != null)
			langAppendix = "_" + lang;

		String propFilePath = msgFileName + langAppendix + ".json";
		if (!new File(propFilePath).exists()) {
			System.err.println("File " + propFilePath + " doesn't exists");
			return p;
		}

		String data = new String(Files.readAllBytes(Paths.get(propFilePath)), "UTF-8");		
		JSONObject json = new JSONObject(data);
		return json.toMap();
	}

	@Override
	String getDataFileName() {
		return "struct";
	}

}
