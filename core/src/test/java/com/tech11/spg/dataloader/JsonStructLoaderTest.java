package com.tech11.spg.dataloader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JsonStructLoaderTest {

	@SuppressWarnings("unchecked")
	@Test
	public void checkUpdateData() throws IOException {
		JsonStructLoader jsl = new JsonStructLoader();

		Map<String, Object> base = new HashMap<>();
		base.put("key1", "value1");
		base.put("key2", "value2");
		Map<String, Object> subBase = new HashMap<String, Object>();
		subBase.put("sb1", "sb1Value");
		subBase.put("sb2", "sb2Value");
		base.put("map1", subBase);
		
		Map<String, Object> newMap = new HashMap<>();
		newMap.put("key1", "newMap1Value");
		Map<String, Object> subNewMap= new HashMap<String, Object>();
		subNewMap.put("sb2", "subNewMap2Value");
		newMap.put("map1", subNewMap);

		jsl.updateData(base, newMap);
		
		Assert.assertEquals("sb1Value",((Map<String, Object>)(base.get("map1"))).get("sb1"));
		Assert.assertEquals("subNewMap2Value",((Map<String, Object>)(base.get("map1"))).get("sb2"));
		Assert.assertEquals("newMap1Value",base.get("key1"));
		Assert.assertEquals("value2",base.get("key2"));


	}

}
