import enumerators.*;
import dateConversion.*;

//Default culture will be dateConversion.Gregorian, since I, the author, am using the Gregorian calendar.
public class CalendarConverter {
    private int year;
    private int month;
    private int day;
    public final int FIRST_WEEK_DAY_OF_CALENDAR = 6; // 01/01/01 AD was a Saturday
    public final int LAST_WEEK_DAY_BC = 5; // 12/31/1 BC was a Friday

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public void setYear(int year) {
        if(year == 0)
            throw new IllegalArgumentException("Year 0 does not exist!");
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private final Gregorian GREGORIAN = new Gregorian();
    private final Hebrew HEBREW = new Hebrew();

    CalendarConverter() {
        this("1900-01-01", 3, DateCulture.GREGORIAN);
    }
    CalendarConverter(String strDate, int format, DateCulture culture) {
        int ymd[] = new int[3];
        switch (culture){
            case CHINESE:
                //TODO: decode Chinese date
                break;
            case GREGORIAN:
                ymd = this.GREGORIAN.decodeDate(strDate, format);
                break;
            case HEBREW:
                //TODO: decode Hebrew date
                break;
            case ISLAMIC:
                //TODO: decode Islamic date
                break;
            case JULIAN:
                //TODO: decode Julian date
                break;
        }
        this.year = ymd[0];
        this.month = ymd[1];
        this.day = ymd[2];
    }

    public String getCultureYear(DateCulture culture) {
        String cultureYear = "";
        switch (culture) {
            case GREGORIAN:
                cultureYear = year < 0 ? Math.abs(year) + " BC" : year + " AD";
                break;
        }
        return cultureYear;
    }

    public String getCultureMonth(DateCulture culture){
        String cultureMonth = "";
        switch (culture) {
            case GREGORIAN:
                cultureMonth = GregorianMonth.values()[month].toString();
        }
        return cultureMonth;
    }

    public String getCultureDay(DateCulture culture){
        String cultureDay = "";
        switch (culture) {
            case GREGORIAN:
                cultureDay = Integer.toString(day);
        }
        return cultureDay;
    }

    //Since the base date will always be Gregorian, there is no need to create this method for other calendars.
    public String dayOfWeek(){
        int day = this.day;
        double month = this.month + 1;
        double year = this.year;
        if (year > 0){
            int dayOfWeek = this.FIRST_WEEK_DAY_OF_CALENDAR;
            for (int i = 1; i < year; i++) {
                if(i != 1582){
                    if (isLeapYear(i))
                        dayOfWeek += 366;
                    else
                        dayOfWeek += 365;
                } else {
                    dayOfWeek += 355;
                }
            }
            for (int i = 1; i < month; i++) {
                dayOfWeek += this.GREGORIAN.daysInMonth((int)year, i);
            }
            if (year == 1582 && month == 10 && day >= 15) {
                dayOfWeek += day - 11;
            } else {
                dayOfWeek += day - 1;
            }

            return switch (dayOfWeek % 7) {
                case 0 -> "Sunday";
                case 1 -> "Monday";
                case 2 -> "Tuesday";
                case 3 -> "Wednesday";
                case 4 -> "Thursday";
                case 5 -> "Friday";
                case 6 -> "Saturday";
                default -> "Error";
            };
        } else if (year == 0){
            throw new IllegalArgumentException("Year 0 does not exist!");
        } else {
            int dayOfWeek = this.LAST_WEEK_DAY_BC;
            for (int i = -1; i > year; i--) {
                if (isLeapYear(i))
                    dayOfWeek -= 366;
                else
                    dayOfWeek -= 365;
            }
            for (int i = 12; i > month; i--) {
                dayOfWeek -= this.GREGORIAN.daysInMonth((int)year, i);
            }
            dayOfWeek -= this.GREGORIAN.daysInMonth((int)year, (int)month) - day;
            return switch (dayOfWeek % 7) {
                case 0 -> "Sunday";
                case -6, 1 -> "Monday";
                case -5, 2 -> "Tuesday";
                case -4, 3 -> "Wednesday";
                case -3, 4 -> "Thursday";
                case -2, 5  -> "Friday";
                case -1, 6 -> "Saturday";
                default -> "Error";
            };
        }
    }

    public int daysInMonth(DateCulture culture){
        int daysInMonth = 0;
        switch (culture) {
            case GREGORIAN:
                daysInMonth = this.GREGORIAN.daysInMonth(year, month);
        }
        return daysInMonth;
    }

    public String getDate(DateCulture culture, int format){
        String date = "";
        int[] ymd = {year, month, day};
        switch (culture){
            case GREGORIAN:
                date = this.GREGORIAN.encodeDate(ymd, format);
                break;
        }
        return date;
    }

    public String getMonthName(DateCulture culture){
        String monthName = "";
        switch (culture){
            case GREGORIAN:
                monthName = GregorianMonth.values()[month].toString();
                break;
        }
        return monthName;
    }

    public boolean isLeapYear(){
        return this.GREGORIAN.IsLeapYear(this.year);
    }
    public static boolean isLeapYear(int year){
        return new Gregorian().IsLeapYear(year);
    }
}
