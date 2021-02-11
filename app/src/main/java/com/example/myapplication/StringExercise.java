package com.example.myapplication;

public class StringExercise {

    private static final char ASTERISK = '*';
    private static final char BACKSLASH = '\\';

    public static boolean checkIsSubString(String mainString, String checkString) {
        int nextIndex = getNextIndex(0, checkString.charAt(0), mainString);
        if (nextIndex == -1) {
            return false;
        }
        boolean result = checkFromIndex(nextIndex, mainString, checkString);
        while (nextIndex < mainString.length() - 1 && nextIndex > -1) {
            if (!result) {
                nextIndex = getNextIndex(nextIndex + 1, checkString.charAt(0), mainString);
                if (nextIndex > -1) {
                    result = checkFromIndex(nextIndex, mainString, checkString);
                }
            } else {
                return result;
            }
        }
        return result;
    }

    private static int getNextIndex(int start, char charAt, String mainString) {
        if (charAt == ASTERISK || charAt == BACKSLASH) {
            return start;
        }
        for (int i = start; i < mainString.length(); i++) {
            if (mainString.charAt(i) == charAt) {
                return i;
            }
        }
        return -1;
    }

    private static boolean checkFromIndex(int nextIndex, String mainString, String checkString) {
        for (int i = 0, j = 0; i < checkString.length(); i++, j++) {
            if (i < (checkString.length() - 2) && checkString.charAt(i) == BACKSLASH
                    && checkString.charAt(i + 1) == ASTERISK) {
                i++;
                if (mainString.charAt(j + nextIndex) == BACKSLASH) {
                    j++;
                }
                if (checkString.charAt(i) != mainString.charAt(j + nextIndex)) {
                    return false;
                }
            }
            if (i > 0 && checkString.charAt(i - 1) != BACKSLASH
                    && checkString.charAt(i) == ASTERISK) {
                if (i < checkString.length() - 1 && (j + nextIndex) < (mainString.length() - 1)
                        && checkString.charAt(i + 1) !=
                        mainString.charAt(j + nextIndex + 1)) {
                    i--;
                } else {
                    if (j + nextIndex == mainString.length() - 1
                            && checkString.charAt(checkString.length() - 1) != ASTERISK
                            && checkString.charAt(checkString.length() - 2) != BACKSLASH) {
                        return false;
                    }
                }
            } else {
                if ((j + nextIndex) < (mainString.length() - 2) &&
                        mainString.charAt(j + nextIndex)
                                != checkString.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

}