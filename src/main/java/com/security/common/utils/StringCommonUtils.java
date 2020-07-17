package com.security.common.utils;

import com.security.common.Constants;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;

public class StringCommonUtils {
    public static final String EMPTY = "";
    public static final String LINE_SEPARATOR = getProperty("line.separator");

    private StringCommonUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gives a string consisting of the given character repeated the given number of times.
     *
     * @param ch    the character to repeat
     * @param count how many times to repeat the character
     * @return the resultant string
     */
    public static String repeat(char ch, int count) {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < count; ++i) {
            buffer.append(ch);
        }

        return buffer.toString();
    }

    /**
     * Tells whether the given string is either {@code} or consists solely of whitespace characters.
     *
     * @param target string to check
     * @return {@code true} if the target string is null or empty
     */
    public static boolean isNullOrEmpty(String target) {
        return target == null || target.isEmpty();
    }


    /**
     * Gives a string consisting of a given string prepended and appended with surrounding characters.
     *
     * @param target a string
     * @param begin  character to prepend
     * @param end    character to append
     * @return the surrounded string
     */
    public static String surround(String target, char begin, char end) {
        return begin + target + end;
    }

    /**
     * Gives a string consisting of the elements of a given array of strings, each separated by a given separator
     * string.
     *
     * @param pieces    the strings to join
     * @param separator the separator
     * @return the joined string
     */
    public static String join(String[] pieces, String separator) {
        return join(asList(pieces), separator);
    }

    /**
     * Gives a string consisting of the string representations of the elements of a given array of objects,
     * each separated by a given separator string.
     *
     * @param pieces    the elements whose string representations are to be joined
     * @param separator the separator
     * @return the joined string
     */
    public static String join(Iterable<String> pieces, String separator) {
        StringBuilder buffer = new StringBuilder();

        for (Iterator<String> iter = pieces.iterator(); iter.hasNext(); ) {
            buffer.append(iter.next());

            if (iter.hasNext()) {
                buffer.append(separator);
            }
        }

        return buffer.toString();
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
    public static String lineToCamel(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #camelToLine2(String)})
     */
    public static String camelToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String camelToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 去除String的最后一个,号
     * @return string
     */
    public static String eraseLastComma(String s){
        char c = s.charAt(s.length() - 1);
        if(c == ','){
            return s.substring(0, s.length()-1);
        }
        return s;
    }

    /**
     * 将size大于1的list，拼接成以逗号分隔的String
     * @param ls size大于1的list<String> 例如list<String> a b c
     * @return a,b,c
     */
    public static String concatListStringWithComma(List<String> ls){
        StringBuilder sb = new StringBuilder();
        sb.append(ls.get(0));
        if(ls.size() > 1){
            ls.stream().skip(1).forEach(x -> sb.append(Constants.SymbolString.COMMA).append(x));
        }
        return sb.toString();
    }

    /**
     * 将list，拼接成sql中的in条件
     * @param ls 例如list<String> a b c
     * @return ('a','b','c')
     */
    public static String transferListToSqlInCondition(List<String> ls){
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.SymbolString.LEFT_BRACKET);
        sb.append(Constants.SymbolString.SINGLE_QUOTE).append(ls.get(0)).append(Constants.SymbolString.SINGLE_QUOTE);
        if(ls.size() > 1){
            ls.stream().skip(1).forEach(x -> sb.append(Constants.SymbolString.COMMA).append(Constants.SymbolString.SINGLE_QUOTE).append(x).append(Constants.SymbolString.SINGLE_QUOTE));
        }
        sb.append(Constants.SymbolString.RIGHT_BRACKET);
        return sb.toString();
    }

    public static boolean checkAdmin(String userId){
        return Constants.SUPER_ADMIN_ID.equals(userId) || Constants.ADMIN_ID.equals(userId);
    }

    /**
     * 反转字符串
     * @param str 源字符串
     * @return 反转后的字符串
     */
    public static String reverseStr(String str){
        if(isNullOrEmpty(str)) {
            return Constants.EMPTY_STRING;
        }
        return new StringBuilder(str).reverse().toString();
    }
}
