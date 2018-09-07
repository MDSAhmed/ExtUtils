package com.ahmed.utils.tlv;

import java.util.Iterator;
import java.util.Map.Entry;

import com.ahmed.utils.CommonUtil;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class TLVUtil {

	private int ZERO_PRECEDE = 3;

	SortedMap<Integer, String> sm = new TreeMap<Integer, String>();

	public void addTagValue(int tag, String value) {
		sm.put(tag, value);
	}

	public String getTlv() {
		String result = "";
		Set<Entry<Integer, String>> s = sm.entrySet();
		Iterator<Entry<Integer, String>> i = s.iterator();

		while (i.hasNext()) {
			Entry<Integer, String> m = i.next();
			int key = m.getKey();
			String value = m.getValue();

			result = result + CommonUtil.zeroLeftPad("" + key, ZERO_PRECEDE, '0')
					+ CommonUtil.zeroLeftPad("" + value.length(), ZERO_PRECEDE, '0') + value;
		}
		return result;
	}

	public String getValue(int tag) {
		return sm.get(tag);
	}

	public void parseTLV(String tlv) {

		if (tlv.length() < (ZERO_PRECEDE * 3) + 1) {
			return;
		}

		for (int i = 0; i < tlv.length();) {
			String key = tlv.substring(i, i = i + ZERO_PRECEDE);
			System.out.println("key::" + key);
			String l = tlv.substring(i, i = i + ZERO_PRECEDE);
			int len = Integer.parseInt(l);
			String value = tlv.substring(i, i = i + len);
			sm.put(Integer.parseInt(key), value);
		}
	}

	public static void main(String[] args) {
		
		TLVUtil tlvUtil = new TLVUtil();
		
		tlvUtil.addTagValue(2, "TEST123");
		System.out.println(tlvUtil.getTlv());
		
//		tlvUtil.parseTLV("001004TEST0020041234");

		 System.out.println(tlvUtil.getValue(2));
	}

}
