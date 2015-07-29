package in.webtuts.google.date;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class FixedHolidays implements Holidays {

    private final Set<Holiday> holidays;

    public FixedHolidays() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        addHolidays(holidays);
        this.holidays = Collections.unmodifiableSet(holidays);
    }

    protected abstract void addHolidays(Set<Holiday> holidays);

    @Override
    public final boolean contains(Date date) {
        Holiday holiday = new Holiday(date);
        return holidays.contains(holiday);
    }

}