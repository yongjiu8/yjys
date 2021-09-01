package com.example.yjys.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static List<String> getSubStrArray(String str, String start, String end) {
        List<String> res = new ArrayList<>();
        String tmp = str;
        int s = -1;
        do {
            s = tmp.indexOf(start);
            int e = tmp.indexOf(end,s+start.length());

            if (s==-1 || e==-1){
                break;
            }

            res.add(tmp.substring(s+start.length(),e));
            if (e == tmp.length()-1){
                break;
            }
            tmp = tmp.substring(e+1);

        }while (s > -1);
        return res;
    }

}
