package com.tech11.spg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class MessageLoader {
	String msgFileName;

	Properties messages = new Properties();

	public static MessageLoader init(File tmplFile) throws IOException {
		return init(tmplFile, null);
	}

	public static MessageLoader init(File tmplFolder, String defaultLanguage) throws IOException {
		MessageLoader ml = new MessageLoader();

		ml.msgFileName = tmplFolder.getAbsolutePath() + File.separator + "messages";
		ml.loadDefault(defaultLanguage);
		return ml;
	}

	public MessageLoader loadDefault(String lang) throws IOException {
		return loadLang(lang);
	}

	public MessageLoader loadLang(String lang) throws IOException {
		Properties p = loadMsgProperties(lang);
		messages.putAll(p);
		return this;
	}

	public Properties getMessages() {
		return messages;
	}

	Properties loadMsgProperties(String lang) throws IOException {
		Properties p = new Properties();

		String langAppendix = "";
		if (lang != null)
			langAppendix = "_" + lang;

		String propFilePath = msgFileName + langAppendix + ".properties";
		if (!new File(propFilePath).exists()) {
			System.err.println("File " + propFilePath + " doesn't exists");
			return p;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(propFilePath), "UTF8"));
		p.load(in);
		return p;
	}

}
