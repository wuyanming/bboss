/*
 *  Copyright 2008 bbossgroups
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.frameworkset.security.ecc;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: ECCHelper.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2014年4月22日
 * @author biaoping.yin
 * @version 3.8.0
 */
public class ECCHelper {
	
	public static final String  ECC_SIMPLE = "SIMPLE";
	public static final String  ECC_ECIES = "ECIES";
	private static Map<String,String> ecccoderClasses = new HashMap<String,String>();
	private static Map<String,ECCCoderInf> ecccoders = new HashMap<String,ECCCoderInf>();
	static 
	{
		ecccoderClasses.put("SIMPLE", "org.frameworkset.security.ecc.ECCCoder");
		ecccoderClasses.put("ECIES", "org.frameworkset.security.ecc.FlexiECCCoder");
	}
	public static ECCCoderInf getECCCoder()
	{
		return getECCCoder(ECC_ECIES);
	}
	public static ECCCoderInf getECCCoder(String type)
	{
		if(type == null)
			type = ECC_ECIES;
		ECCCoderInf ecccoderInf = ecccoders.get(type);
		if(ecccoderInf != null)
			return ecccoderInf;
		synchronized(ecccoders)
		{
			ecccoderInf = ecccoders.get(type);
			if(ecccoderInf != null)
				return ecccoderInf;
			String clazz = ecccoderClasses.get(type);
			if(clazz == null)
				throw new java.lang.IllegalArgumentException("不支持的ecc加密类型:"+type+"，系统只支持"+ECC_SIMPLE+"和"+ECC_ECIES+"两种机制.");
			try {
				ecccoderInf = (ECCCoderInf)Class.forName(clazz).newInstance();
			} catch (InstantiationException e) {
				throw new java.lang.IllegalArgumentException("不支持的ecc加密类型:"+type+"，系统只支持"+ECC_SIMPLE+"和"+ECC_ECIES+"两种机制.",e);
			} catch (IllegalAccessException e) {
				throw new java.lang.IllegalArgumentException("不支持的ecc加密类型:"+type+"，系统只支持"+ECC_SIMPLE+"和"+ECC_ECIES+"两种机制.",e);
			} catch (ClassNotFoundException e) {
				throw new java.lang.IllegalArgumentException("不支持的ecc加密类型:"+type+"，系统只支持"+ECC_SIMPLE+"和"+ECC_ECIES+"两种机制.",e);
			}
			ecccoders.put(type, ecccoderInf);
			return ecccoderInf;
		}
	}

}
