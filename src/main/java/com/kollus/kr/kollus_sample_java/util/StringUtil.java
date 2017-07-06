package com.kollus.kr.kollus_sample_java.util;

import java.text.NumberFormat;
import java.text.ParsePosition;
/**
 * String 유틸 클래스
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class StringUtil {
	public static boolean isNumeric(Object obj){
		
		String src = obj.toString();
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(src, pos);		
		return src.length() == pos.getIndex();
	}
}
