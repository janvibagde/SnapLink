package com.janvi.util;

public class Base62 {
    private static final String CHARS=
                            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String encode(long num){
        if(num ==0) return "0";
        StringBuilder sb = new StringBuilder();
        while(num > 0){
            sb.append(CHARS.charAt((int) (num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }

    public static long decode(String str) {
        long result = 0;
        for (char c : str.toCharArray()) {
            result = result * 62 + CHARS.indexOf(c);
        }
        return result;
    }
}