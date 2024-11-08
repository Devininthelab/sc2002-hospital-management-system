package org.example.utils;

public class DateToNumber {
    /**
     * Convert a date string to a number, this function is used for the doctor's schedule
     * Date should be from Monday to Saturday
     * Monday is 0, Tuesday is 1, ..., Saturday is 5
     *
     * @param date the date string in the format
     * @return the number representation of the date
     */
    public static int dateToNumber(String date) {
        switch (date) {
            case "Monday":
                return 0;
            case "Tuesday":
                return 1;
            case "Wednesday":
                return 2;
            case "Thursday":
                return 3;
            case "Friday":
                return 4;
            case "Saturday":
                return 5;
            default:
                return -1;
        }
    }
}
