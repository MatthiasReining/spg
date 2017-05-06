package com.tech11.spg;

import java.io.File;
import java.util.Locale;

public class SystemConfiguration {

	private static SystemConfiguration instance = new SystemConfiguration();
	
	private String templateFolder;
	private String targetFolder;
	private Locale locale;

	private SystemConfiguration() {
		// no public constructor
	}

	public static SystemConfiguration instance() {
		return instance;
	}

	public String getDefaultMessageFolder() {
		return System.getProperty("spg.messageFolder", templateFolder + File.separator + "i18n" + File.separator);
	}
	
	public String getDefaultLang() {
		return System.getProperty("spg.defaultLocale", "en");
	}
	
	
	public Locale getDefaultLocale() {
		return new Locale(System.getProperty("spg.defaultLocale", "en"));
	}
	
	public Locale getLocale() {
		if (locale == null)
			return new Locale( System.getProperty("spg.locale", getDefaultLocale().toString()));
		
		return locale;
	}

	
	public String getTemplateFolder() {
		if (templateFolder == null)
			return System.getProperty("spg.templateFolder", new File("").getAbsolutePath());
		
		return templateFolder;
	}
	
	public String getTargetFolder() {
		if (targetFolder == null)
			return System.getProperty("spg.targetFolder", getTemplateFolder());
		
		return targetFolder;		
	}

	
	public void setTemplateFolder(String templateFolder) {
		this.templateFolder = templateFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	

}
