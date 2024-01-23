package dateConversion;

public class Gregorian implements IConversionMethods {
    public Gregorian(){}
    @Override
    public int[] decodeDate(String strDate, int format){ // Add " BC" after date for indicating Before Christ dates
        int[] ymd = {0, 0, 0};
        String[] values = strDate.split(" ")[0].split(format < 6 ? "/" : format < 9 ? "-" : " ");
        switch (format % 3) {
            case 0: // yyyy/mm/dd, yyyy-mm-dd, yyyy mm dd
                assignValues(ymd, values, 0, 1, 2);
                break;
            case 1: // dd/mm/yyyy, dd-mm-yyyy, dd mm yyyy
                assignValues(ymd, values, 2, 1, 0);
                break;
            case 2: // mm/dd/yyyy, mm-dd-yyyy, mm dd yyyy
                assignValues(ymd, values, 2, 0, 1);
                break;
        }
        verifyFlawsDateArray(ymd);
        if(strDate.contains("BC")){
            ymd[0] *= -1;
        }
        return ymd;
    }

    private void assignValues(int[] ymd, String[] values, int yearIndex, int monthIndex, int dayIndex) {
        ymd[0] = Integer.parseInt(values[yearIndex]);
        ymd[1] = Integer.parseInt(values[monthIndex]) - 1;
        ymd[2] = Integer.parseInt(values[dayIndex]);
    }

    @Override
    public String encodeDate(int[] ymd, int format){
        verifyFlawsDateArray(ymd);
        boolean bc = false;
        if(ymd[0] < 0){
            ymd[0] *= -1;
            bc = true;
        }
        return switch (format) {
            case 0 -> "%d/%d/%d".formatted(ymd[0], ymd[1] + 1, ymd[2]) + (bc ? " BC" : "");// yyyy/mm/dd
            case 1 -> "%d/%d/%d".formatted(ymd[2], ymd[1] + 1, ymd[0]) + (bc ? " BC" : "");// dd/mm/yyyy
            case 2 -> "%d/%d/%d".formatted(ymd[1] + 1, ymd[2], ymd[0]) + (bc ? " BC" : "");// mm/dd/yyyy
            case 3 -> "%d-%d-%d".formatted(ymd[0], ymd[1] + 1, ymd[2]) + (bc ? " BC" : "");// yyyy-mm-dd
            case 4 -> "%d-%d-%d".formatted(ymd[2], ymd[1] + 1, ymd[0]) + (bc ? " BC" : "");// dd-mm-yyyy
            case 5 -> "%d-%d-%d".formatted(ymd[1] + 1, ymd[2], ymd[0]) + (bc ? " BC" : "");// mm-dd-yyyy
            case 6 -> "%d %d %d".formatted(ymd[0], ymd[1] + 1, ymd[2]) + (bc ? " BC" : "");// yyyy mm dd
            case 7 -> "%d %d %d".formatted(ymd[2], ymd[1] + 1, ymd[0]) + (bc ? " BC" : "");// dd mm yyyy
            case 8 -> "%d %d %d".formatted(ymd[1] + 1, ymd[2], ymd[0]) + (bc ? " BC" : "");// mm dd yyyy
            default -> "";
        };
    }

    @Override
    public void verifyFlawsDateArray(int[] ymd){ // Default format: yyyy - mm - dd
        if(ymd.length != 3){
            throw new IllegalArgumentException("Your ymd array must have exactly 3 elements!");
        }
        if(ymd[1] > 11 || ymd[1] < 0){
            throw new IllegalArgumentException("Invalid month!");
        }
        if(ymd[2] > 31 || ymd[2] < 1){
            throw new IllegalArgumentException("Invalid day!");
        }
        if(IsLeapYear(ymd[0]) && ymd[1] == 1 && ymd[2] > 29){
            throw new IllegalArgumentException("Invalid day!");
        }
        if(!IsLeapYear(ymd[0]) && ymd[1] == 1 && ymd[2] > 28){
            throw new IllegalArgumentException("Invalid day!");
        }
        if(ymd[1] == 3 || ymd[1] == 5 || ymd[1] == 8 || ymd[1] == 10){
            if(ymd[2] > 30){
                throw new IllegalArgumentException("Invalid day!");
            }
        }
        if(ymd[0] == 0){
            throw new IllegalArgumentException("Year 0 does not exist!");
        }
        if(ymd[0] == 1582 && ymd[1] == 9 && ymd[2] > 4 && ymd[2] < 15){
            throw new IllegalArgumentException("Date does not exist due to dateConversion.Gregorian calendar reform!\nSee more in https://en.wikipedia.org/wiki/Adoption_of_the_Gregorian_calendar");
        }
    }

    @Override
    public int daysInMonth(int year, int month){
        month -= 1;
        if(month == 9 && year == 1582){
            return 21;
        }
        if(month == 1){
            if(IsLeapYear(year)){
                return 29;
            }
            return 28;
        }
        if(month == 3 || month == 5 || month == 8 || month == 10){
            return 30;
        }
        return 31;
    }

    public boolean IsLeapYear(int year){
        if(year >= -44){//44 bc is the first leap year
            if (year % 4 == 0) {
                if (year > 1600) {
                    if (year % 400 == 0) {
                        return true;
                    } else if (year % 100 == 0) {
                        return false;
                    }
                }
                return true;
            } else if (year == -1) {//1 bc is also a leap year
                return true;
            }
            return false;
        }
        return false;
    }
}
