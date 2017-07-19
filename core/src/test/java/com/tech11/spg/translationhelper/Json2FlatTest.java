package com.tech11.spg.translationhelper;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class Json2FlatTest {

	@Test
	public void runTest() throws IOException {
		File folder = new File(Thread.currentThread().getClass().getResource("/").getFile());

		Json2Flat j2f = new Json2Flat();
		j2f.json2Flat(folder, Locale.ENGLISH);

		File csvFile = new File(j2f.getCSVFilePath());

		Assert.assertTrue(csvFile.exists());
	}

}
