package com.ahmed.utils;

public class CommonUtil {
	
	public static String zeroLeftPad(String s, int l, char c) {
		String r = s;
		for (int i = s.length(); i < l; i++) {
			r = c+r;
		}
	    return r;  
	}

}
