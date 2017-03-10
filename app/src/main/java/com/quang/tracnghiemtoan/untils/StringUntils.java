package com.quang.tracnghiemtoan.untils;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by QUANG on 1/13/2017.
 */

public class StringUntils {

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }

    public static String unAccentAndLower(String s) {
        String temp = unAccent(s);
        temp = temp.toLowerCase().trim();
        return temp;
    }
}
