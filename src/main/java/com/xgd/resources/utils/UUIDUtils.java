package com.xgd.resources.utils;

import java.util.UUID;

public class UUIDUtils {
	public static String getUUIDStr32(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
}
