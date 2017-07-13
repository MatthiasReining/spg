package com.tech11.spg.dataloader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tech11.spg.SystemConfiguration;

public abstract class DataLoader {

	protected File tmplFolder;
	protected String msgFileName;
	Map<String, Object> data = new HashMap<>();

	DataLoader(File tmplFolder) throws IOException {
		this.tmplFolder = tmplFolder;
		String defaultLanguage = SystemConfiguration.instance().getDefaultLang();
		msgFileName = tmplFolder.getAbsolutePath() + File.separator + getDataFileName();
		loadDefaultMessages(defaultLanguage);
	}

	public <T extends DataLoader> T loadDefaultMessages(String lang) throws IOException {
		// FIXME return loadLang(lang);
		return null;
	}

	public <T extends DataLoader> T loadMessages(Locale locale) throws IOException {
		return loadMessage(locale, null);
	}

	@SuppressWarnings("unchecked")
	public <T extends DataLoader> T loadMessage(Locale locale, Locale fallbackLocale) throws IOException {
		data.putAll(loadData(locale, fallbackLocale));
		return (T) this;
	}

	abstract Map<String, Object> loadData(Locale locale, Locale fallbackLang) throws IOException;

	abstract String getDataFileName();

	abstract String getFileExtension();

	public Map<String, Object> getData() {
		return data;
	}

	protected File getMessagesFile(Locale locale) {

		String msgPath = tmplFolder.getAbsolutePath() + File.separator + getDataFileName();
		File result;

		if (locale == null) {
			msgPath += getFileExtension();
			result = new File(msgPath);

			if (result.exists())
				return result;
			System.err.println("File " + msgPath + " doesn't exists");
			return null;
		}
		
		String tmpName = getDataFileName() + "_" + locale.getLanguage();
		if (!locale.getCountry().isEmpty())
			tmpName += "-" + locale.getCountry();
		final String testName = tmpName + getFileExtension();

		String[] msgFileNames = tmplFolder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {				
				if (name.equalsIgnoreCase(testName))
					return true;
				return false;
			}
		});

		if (msgFileNames.length == 0) {
			System.err.println("File " + msgPath + "+<locale> doesn't exists");
			return null;
		}
		result = new File(tmplFolder, msgFileNames[0]);
		if (!result.exists()) {
			System.err.println("File " + msgFileNames[0] + " doesn't exists");
			return null;
		}
		return result;
	}

}