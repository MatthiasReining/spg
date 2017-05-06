package com.tech11.spg;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class StaticPageGenerator {
	
	private static Logger LOGGER = Logger.getLogger(StaticPageGenerator.class.getName());
	
	private String singleTemplate;
	private File templateFolder;
	private File targetFolder;
	private Writer outputWriter;
	private Locale locale;

	public StaticPageGenerator setTemplateFolder(String folderPath) {
		this.templateFolder = new File(folderPath);
		return this;
	}
	
	public StaticPageGenerator setTargetFolder(String targetFolder) {
		this.targetFolder = new File(targetFolder);
		return this;
	}

	public StaticPageGenerator setOutputWriter(Writer outputWriter) {
		this.outputWriter = outputWriter;
		return this;
	}

	public StaticPageGenerator setLanguage(Locale locale) {
		this.locale = locale;
		return this;
	}

	public StaticPageGenerator processSingleTemplate(String singleTemplate) {
		this.singleTemplate = singleTemplate;
		return this;
	}

	public void run() {
		// Freemarker configuration object
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDefaultEncoding("UTF-8");
		config.setLocale(Locale.GERMAN);
		config.setOutputEncoding("UTF-8");
		config.setTimeZone(TimeZone.getTimeZone("GMT"));
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		try {
			config.setDirectoryForTemplateLoading(templateFolder);

			String defaultLang = "en";

			MessageLoader ml = MessageLoader.init(templateFolder, defaultLang);
			if (locale != null)
				ml.loadLang(locale.toString());
			Properties msg = ml.getMessages();

			String[] templates;

			if (singleTemplate != null)
				templates = new String[] { singleTemplate };
			else
				templates = templateFolder.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(".ftl"))
							return true;
						return false;
					}
				});
			
			if (targetFolder == null)
				targetFolder = templateFolder;
			else {
				if (!targetFolder.exists())
					targetFolder.mkdirs();
			}

			for (String templateName : templates) {				
				Template template = config.getTemplate(templateName);
				if (outputWriter == null) {
					String outputFileName = templateName.substring(0, templateName.lastIndexOf(".ftl"));
					String outFilePath = targetFolder.getAbsolutePath() + File.separator + outputFileName;
					Writer out = new FileWriter(outFilePath);

					LOGGER.log(Level.INFO, () -> "Generate " + outFilePath);
					
					template.process(msg, out);
				} else {
					template.process(msg, outputWriter);
				}

			}
		

		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
