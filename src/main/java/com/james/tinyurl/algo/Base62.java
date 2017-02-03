package com.james.tinyurl.algo;

/**
 * This class I stole from this poor guy.
 * https://gist.github.com/jdcrensh/4670128,
 * Special thanks to him. I will implemente my own later
 * Created by haozhexu on 1/30/17.
 */
public class Base62 {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int BASE = ALPHABET.length();

    private Base62() {
        throw new UnsupportedOperationException();
    }

    /**
     * Give a base10 integer, return a String hash which is 62 based
     * @param i base10 integer
     * @return a String hash which is 62 based
     */
    public static String fromBase10(int i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return "a";
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    private static int fromBase10(int i, final StringBuilder sb) {
        int rem = i % BASE;
        sb.append(ALPHABET.charAt(rem));
        return i / BASE;
    }

    /**
     * Transforms the given string hash into a base10 integer
     * @param str string hash in question
     * @return a base10 integer
     */
    public static int toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static int toBase10(char[] chars) {
        int n = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private static int toBase10(int n, int pow) {
        return n * (int) Math.pow(BASE, pow);
    }
}