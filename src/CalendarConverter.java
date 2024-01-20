//Default culture will be Gregorian, since I, the author, am using Gregorian calendar.
public class CalendarConverter {
    private int year;

    private int month;

    private int day;

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

    private Gregorian gregorian = new Gregorian();
    private Hebrew hebrew = new Hebrew();

    CalendarConverter() {
        this("1900-01-01", 0, DateCulture.GREGORIAN);
    }
    CalendarConverter(String strDate, int format, DateCulture culture) {
        int ymd[] = new int[3];
        switch (culture){
            case CHINESE:
                //TODO: decode Chinese date
                break;
            case GREGORIAN:
                ymd = this.gregorian.DecodeDate(strDate, format);
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

    public String getCultureYear() {
        return getCultureYear(DateCulture.GREGORIAN);
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

    public String getCultureMonth(){ return getCultureMonth(DateCulture.GREGORIAN); }

    public String getCultureMonth(DateCulture culture){
        String cultureMonth = "";
        switch (culture) {
            case GREGORIAN:
                cultureMonth = GregorianMonth.values()[month].toString();
        }
        return cultureMonth;
    }

    public String getCultureDay(){ return getCultureDay(DateCulture.GREGORIAN); }

    public String getCultureDay(DateCulture culture){
        String cultureDay = "";
        switch (culture) {
            case GREGORIAN:
                cultureDay = Integer.toString(day);
        }
        return cultureDay;
    }

    public String DayOfWeek(String date, int format){
        int[] ymd = {this.year, this.month, this.day};
        int dayOfWeek = 0;
        int[] daysInMonth = {31, isLeapYear(ymd[0]) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};
        for(int i = 1; i < ymd[0]; i++){
            if(isLeapYear(i)){
                dayOfWeek += 366;
            } else {
                dayOfWeek += 365;
            }
        }
        for(int i = 0; i < ymd[1]; i++){
            dayOfWeek += daysInMonth[i];
        }
        dayOfWeek += ymd[2];
        dayOfWeek %= 7;
        return switch (dayOfWeek) {
            case 0 -> "Sunday";
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            default -> "Saturday";
        };
    }

    public int daysInMonth(DateCulture culture){
        int daysInMonth = 0;
        switch (culture) {
            case GREGORIAN:
                daysInMonth = this.gregorian.DaysInMonth(year, month);
        }
        return daysInMonth;
    }

    public String getDate(DateCulture culture, int format){
        String date = "";
        int[] ymd = {year, month, day};
        switch (culture){
            case GREGORIAN:
                date = this.gregorian.EncodeDate(ymd, format);
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
        return this.gregorian.IsLeapYear(this.year);
    }
    public boolean isLeapYear(int year){
        return this.gregorian.IsLeapYear(year);
    }
}
