package com.tech11.spg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class Main {

	public static void main(String... args) throws IOException {

		if (args.length != 3) {
			System.err.println("Please use SPG with java -jar spg.jar <TEMPLATE_FOLDER> <TARGET_FOLDER> <MASTERLANGUAGE> <SINGLETARGETLANGUAGE>");
			System.exit(1);
		}

		String templateFolder = convertIntoAbsolutePath(args[0]);
		String targetFolder = convertIntoAbsolutePath(args[1]);
		Locale locale = new Locale(args[2]);

		SystemConfiguration.instance().setTemplateFolder(templateFolder);
		SystemConfiguration.instance().setTargetFolder(targetFolder);
		SystemConfiguration.instance().setLocale(locale);

		// String folderPath =
		// "D:\\hero\\workspace-neon\\spg\\src\\test\\resources";
		// String outputPath =
		// "D:\\hero\\workspace-neon\\spg\\src\\test\\resources\\de";

		StaticPageGenerator spg = new StaticPageGenerator()
				.setTemplateFolder(templateFolder)
				.setTargetFolder(targetFolder)
				.setMasterLanguage(locale);		
		spg.run();

		if (System.getProperty("spg.watch") == null)
			return;

		Path dir = Paths.get(templateFolder);

		new WatchDir(dir, true).processEvents(spg);

	}

	static String convertIntoAbsolutePath(String path) {
		if (path.contains(":") || path.startsWith("/"))
			return path;

		return new File(path).getAbsolutePath();
	}

}
