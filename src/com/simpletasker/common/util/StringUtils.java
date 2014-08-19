package com.simpletasker.common.util;

public class StringUtils {

	public static String getSpace(int spaces){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < spaces; i++){
			builder.append(" ");
		}
		return builder.toString();
	}
	
}
