package com.tech11.spg;

import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "build", requiresProject = false, defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class SpgMojo extends AbstractMojo {

	/**
	 * Location of the file.
	 */
	//@Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)	
	//private File outputDirectory;

	@Parameter(property = "templateFolder", required = true)
	private String templateFolder;

	@Parameter(property = "targetFolder", required = true)
	private String targetFolder;

	@Parameter(property = "masterLocale", required = true)
	private String masterLocaleTxt;

	@Override
	public void execute() throws MojoExecutionException {

		Locale locale = new Locale(masterLocaleTxt);

		SystemConfiguration.instance().setTemplateFolder(templateFolder);
		SystemConfiguration.instance().setTargetFolder(targetFolder);
		SystemConfiguration.instance().setLocale(locale);

		new StaticPageGenerator()
				.setTemplateFolder(templateFolder)
				.setTargetFolder(targetFolder)
				.setMasterLanguage(locale)
				.run(); 
		

		// throw new MojoExecutionException("Error creating file " + touch, e);
	}
}
