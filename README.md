# spg
Static Page Generator (based on Freemarker)

The static page generator is separated into `core` and `maven-plugin`. 


Using as self-contained jar use should compile as the `core` project with profile `-Pself-contained`. The result will be a jar file that contains all dependent libraries.
Following the first 3 parameters are mandatory.

	<TEMPLATE_FOLDER>
	<TARGET_FOLDER>
	<LANGUAGE>
	
Example:

	java -jar spg.jar /dev/website/v2/raw /dev/website/v2/en en
	
If you would like to monitor the template folder on going you can add the system property `spg.watch`.

Example:

	java -Dspg.watch -jar spg.jar /dev/website/v2/raw /dev/website/v2/en en 


Using as maven plugin you can use it by following command:

	mvn com.tech11:spg-maven-plugin:build -DtemplateFolder=${WORKSPACE}/home/v2/raw -DtargetFolder=/tech11-website/home/v2/en -Dlocale=en
	
