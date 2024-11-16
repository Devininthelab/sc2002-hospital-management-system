package org.example.utils;

public class ColorText {

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";

    /**
     * Get text with color: Possible colors are RED, GREEN, YELLOW
     * @param text
     * @return text with color
     */
    public static String getStatusWithColor(String text) {
        String color = "";
        switch (text){
            case "COMPLETED":
                color = GREEN;
                break;
            case "ACCEPTED":
                color = YELLOW;
                break;
            case "REJECTED":
                color = RED;
                break;
            default:
                break;
        }
        return color + text + RESET;
    }

    public static String getIntText(String text, String color) {
        return color + text + RESET;
    }
}