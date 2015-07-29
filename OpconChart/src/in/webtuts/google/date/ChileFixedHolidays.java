package in.webtuts.google.date;

import java.util.Calendar;
import java.util.Set;

public class ChileFixedHolidays extends FixedHolidays {

    @Override
    protected void addHolidays(Set<Holiday> holidays) {
        holidays.add(new Holiday(Calendar.JANUARY, 1));
        holidays.add(new Holiday(Calendar.APRIL, 3));
        holidays.add(new Holiday(Calendar.MAY, 1));
        holidays.add(new Holiday(Calendar.MAY, 21));
        holidays.add(new Holiday(Calendar.JUNE, 29));
        holidays.add(new Holiday(Calendar.JULY, 16));
        holidays.add(new Holiday(Calendar.SEPTEMBER, 18));
        holidays.add(new Holiday(Calendar.OCTOBER, 12));
        holidays.add(new Holiday(Calendar.DECEMBER, 8));
        holidays.add(new Holiday(Calendar.DECEMBER, 25));
    }

    
}