package org.example.utils;

/**
 * Utility class to convert timeslot integer to string representation
 */
public class TimeslotToInt {
    /**
     * Convert a timeslot integer to a string representation
     * @param timeslot the timeslot integer
     * @return the string representation of the timeslot
     */
    public static String timeslotToString(int timeslot) {
        switch (timeslot) {
            case 0:
                return "9:00 - 10:00";
            case 1:
                return "10:00 - 11:00";
            case 2:
                return "11:00 - 12:00";
            case 3:
                return "12:00 - 13:00";
            case 4:
                return "13:00 - 14:00";
            case 5:
                return "14:00 - 15:00";
            case 6:
                return "15:00 - 16:00";
            case 7:
                return "16:00 - 17:00";
        }
        return "Error in timeslotToString";
    }
}
