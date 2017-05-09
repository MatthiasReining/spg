package com.tech11.spg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MessageLoader extends DataLoader<MessageLoader> {

	public MessageLoader(File tmplFolder) throws IOException {
		super(tmplFolder);
	}

	@Override
	Map<String, Object> loadData(String lang) throws IOException {

		Map<String, Object> result = new HashMap<>();
		String langAppendix = "";
		if (lang != null)
			langAppendix = "_" + lang;

		String propFilePath = msgFileName + langAppendix + ".properties";
		if (!new File(propFilePath).exists()) {
			System.err.println("File " + propFilePath + " doesn't exists");
			return result;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(propFilePath), "UTF8"));
		Properties p = new Properties();
		p.load(in);
		
		//convert to Map<String,Object>
		p.entrySet().forEach(entry -> result.put(entry.getKey().toString(), entry.getValue()));

		return result;
	}

	@Override
	String getDataFileName() {
		return "messages";
	}

}
