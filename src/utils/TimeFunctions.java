package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFunctions {
    static DateTimeFormatter pattern1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    static DateTimeFormatter pattern2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String currentDateYTime(){
        LocalDateTime dateYHour = LocalDateTime.now();
        return String.format("%s",dateYHour.format(pattern1));
    }

    public static String currentDate(){
        LocalDate date = LocalDate.now();
        return String.format("%s",date.format(pattern2));
    }

    public static String in5years(){
        LocalDate in5Years= LocalDate.now().plusYears(5);
        return String.format("%s",in5Years.format(pattern2));
    }

    public static String getDate(){
        int year = DialogHelper.int3In("Type a year",1900,2024);
        int month = DialogHelper.int3In("Type a month by its number",1,12);
        int day = 0;
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day = DialogHelper.int3In("Type a day",1,31);
            case 2 -> {
                if ((year%4)==0){ day = DialogHelper.int3In("Type a day",1,29);
                }
                else { day = DialogHelper.int3In("Type a day", 1, 28);}
            }
            case 4,6,9,11 -> day = DialogHelper.int3In("Type a day",1,30);
        }
        return String.valueOf(LocalDate.of(year ,month,day));
    }
}
