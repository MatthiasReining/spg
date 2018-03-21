package com.tech11.spg;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tech11.spg.dataloader.JsonStructLoader;
import com.tech11.spg.dataloader.MessageLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class StaticPageGenerator implements Runnable, Supplier<Runnable> {

	private static final Logger LOGGER = Logger.getLogger(StaticPageGenerator.class.getName());

	private String singleTemplate;
	private File templateFolder;
	private File targetFolder;
	private File dataFolder;
	private Writer outputWriter;
	private Locale masterLanguage;
	private Locale singleTargetLanguage;

	public StaticPageGenerator setTemplateFolder(String folderPath) {
		this.templateFolder = new File(folderPath);
		return this;
	}

	public StaticPageGenerator setDataFolder(String messageFolder) {
		this.dataFolder = new File(messageFolder);
		return this;
	}

	public StaticPageGenerator setTargetFolder(String targetFolder) {
		this.targetFolder = new File(targetFolder);
		return this;
	}

	public StaticPageGenerator setMasterLanguage(Locale masterLanguage) {
		this.masterLanguage = masterLanguage;
		return this;
	}

	public StaticPageGenerator setSingleTargetLanguage(Locale singleTargetLanguage) {
		this.singleTargetLanguage = singleTargetLanguage;
		return this;
	}

	public StaticPageGenerator setOutputWriter(Writer outputWriter) {
		this.outputWriter = outputWriter;
		return this;
	}

	public StaticPageGenerator processSingleTemplate(String singleTemplate) {
		this.singleTemplate = singleTemplate;
		return this;
	}

	/**
	 * run and build output for all countries
	 */
	@Override
	public void run() {
		if (dataFolder == null)
			dataFolder = new File(SystemConfiguration.instance().getDefaultMessageFolder());

		Set<Locale> locales = new HashSet<>();
		if (singleTargetLanguage != null)
			locales.add(singleTargetLanguage);
		else
			locales = LocaleDetector.extractLocales(dataFolder);

		for (Locale locale : locales) {
			runCountry(locale);
		}
	}

	public void runCountry(Locale locale) {
		// Freemarker configuration object
		freemarker.template.Configuration config = new freemarker.template.Configuration(Configuration.VERSION_2_3_23);
		config.setDefaultEncoding(StandardCharsets.UTF_8.name());
		config.setLocale(locale);
		config.setOutputEncoding(StandardCharsets.UTF_8.name());
		config.setTimeZone(TimeZone.getTimeZone("GMT"));
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		try {
			config.setDirectoryForTemplateLoading(templateFolder);

			if (dataFolder == null)
				dataFolder = new File(SystemConfiguration.instance().getDefaultMessageFolder());

			Map<String, Object> data;

			data = new MessageLoader(dataFolder).loadMessage(locale, masterLanguage).getData();
			data.putAll(new JsonStructLoader(dataFolder).loadMessage(locale, masterLanguage).getData());

			data.put("currentLocale", locale.toString());
			data.put("currentLanguage", locale.getLanguage());
			data.put("currentCountry", locale.getCountry());

			String[] templates;

			if (singleTemplate != null)
				templates = new String[] { singleTemplate };
			else {
				templates = templateFolder.list((File dir, String name) -> {
					return (name.endsWith(".ftl") || name.endsWith(".html")) ? true : false;
				});
			}

			if (targetFolder == null)
				targetFolder = templateFolder;

			File countryTargetFolder = new File(targetFolder, convertLocale2Folder(locale));

			if (!countryTargetFolder.exists())
				countryTargetFolder.mkdirs();

			for (String templateName : templates) {
				Template template = config.getTemplate(templateName);
				if (outputWriter == null) {
					String outputFileName = templateName;
					if (templateName.endsWith(".tfl"))
						outputFileName = templateName.substring(0, templateName.lastIndexOf(".ftl"));

					String outFilePath = countryTargetFolder.getAbsolutePath() + File.separator + outputFileName;
					StringWriter out = new StringWriter();

					template.process(data, out);

					LOGGER.log(Level.INFO, () -> "Generate " + outFilePath);

					Files.write(Paths.get(outFilePath), out.toString().getBytes("UTF-8"));

				} else {
					template.process(data, outputWriter);
				}

			}

		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String convertLocale2Folder(Locale locale) {
		String folderName = locale.getLanguage().toLowerCase();

		if (locale.getCountry() == null || locale.getCountry().isEmpty())
			return folderName;

		folderName += "-" + locale.getCountry().toLowerCase();

		return folderName;
	}

	@Override
	public Runnable get() {
		return this;
	}
}
