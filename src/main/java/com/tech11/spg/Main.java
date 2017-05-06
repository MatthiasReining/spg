package com.tech11.spg;

import java.io.File;
import java.util.Locale;

public class Main {
	
	public static void main(String... args) {
		
		if (args.length != 3) {
			System.err.println("Please use SPG with java -jar spg.jar <TEMPLATE_FOLDER> <TARGET_FOLDER> <LANGUAGE>");
			System.exit(1);
		}

		String templateFolder = convertIntoAbsolutePath(args[0]);
		String targetFolder = convertIntoAbsolutePath(args[1]);
		Locale locale = new Locale(args[2]);
		
		
		//String folderPath = "D:\\hero\\workspace-neon\\spg\\src\\test\\resources";
		//String outputPath = "D:\\hero\\workspace-neon\\spg\\src\\test\\resources\\de";
		
		new StaticPageGenerator()
				.setTemplateFolder(templateFolder)
				.setTargetFolder(targetFolder)
				.setLanguage(locale)
				.run();
	}
	
	static String convertIntoAbsolutePath(String path) {
		if (path.contains(":") || path.startsWith("/"))
			return path;
		
		return new File(path).getAbsolutePath();
	}

}
