public class Gregorian implements IConversionMethods {
    Gregorian(){}
    @Override
    public int[] DecodeDate(String strDate, int format){ // Add " BC" after date for indicating Before Christ dates
        int[] ymd = {0, 0, 0};
        String[] values;
        switch (format) {
            case 0: // yyyy/mm/dd
                values = strDate.split(" ")[0].split("/");
                ymd[0] = Integer.parseInt(values[0]);
                ymd[1] = Integer.parseInt(values[1]) - 1;
                ymd[2] = Integer.parseInt(values[2]);
                break;
            case 1: // dd/mm/yyyy
                values = strDate.split(" ")[0].split("/");
                ymd[0] = Integer.parseInt(values[2]);
                ymd[1] = Integer.parseInt(values[1]) - 1;
                ymd[2] = Integer.parseInt(values[0]);
                break;
            case 2: // mm/dd/yyyy
                values = strDate.split(" ")[0].split("/");
                ymd[0] = Integer.parseInt(values[2]);
                ymd[1] = Integer.parseInt(values[0]) - 1;
                ymd[2] = Integer.parseInt(values[1]);
                break;
            case 3: // yyyy-mm-dd
                values = strDate.split(" ")[0].split("-");
                ymd[0] = Integer.parseInt(values[0]);
                ymd[1] = Integer.parseInt(values[1]) - 1;
                ymd[2] = Integer.parseInt(values[2]);
                break;
            case 4: // dd-mm-yyyy
                values = strDate.split(" ")[0].split("-");
                ymd[0] = Integer.parseInt(values[2]);
                ymd[1] = Integer.parseInt(values[1]) - 1;
                ymd[2] = Integer.parseInt(values[0]);
                break;
            case 5: // mm-dd-yyyy
                values = strDate.split(" ")[0].split("-");
                ymd[0] = Integer.parseInt(values[2]);
                ymd[1] = Integer.parseInt(values[0]) - 1;
                ymd[2] = Integer.parseInt(values[1]);
                break;
            case 6: // yyyy mm dd
                values = strDate.split(" ")[0].split(" ");
                ymd[0] = Integer.parseInt(values[0]);
                ymd[1] = Integer.parseInt(values[1]) - 1;
                ymd[2] = Integer.parseInt(values[2]);
                break;
            case 7: // dd mm yyyy
                values = strDate.split(" ")[0].split(" ");
                ymd[0] = Integer.parseInt(values[2]);
                ymd[1] = Integer.parseInt(values[1]) - 1;
                ymd[2] = Integer.parseInt(values[0]);
                break;
            case 8: // mm dd yyyy
                values = strDate.split(" ")[0].split(" ");
                ymd[0] = Integer.parseInt(values[2]);
                ymd[1] = Integer.parseInt(values[0]) - 1;
                ymd[2] = Integer.parseInt(values[1]);
                break;
        }
        VerifyFlawsDateArray(ymd);
        if(strDate.contains("BC")){
            ymd[0] *= -1;
        }
        return ymd;
    }

    @Override
    public String EncodeDate(int[] ymd, int format){
        VerifyFlawsDateArray(ymd);
        boolean bc = false;
        if(ymd[0] < 0){
            ymd[0] *= -1;
            bc = true;
        }
        return switch (format) {
            case 0 -> "%d/%d/%d".formatted(ymd[0], ymd[1] + 1, ymd[2]) + (bc ? " BC" : "");
            case 1 -> "%d/%d/%d".formatted(ymd[2], ymd[1] + 1, ymd[0]) + (bc ? " BC" : "");
            case 2 -> "%d/%d/%d".formatted(ymd[1] + 1, ymd[2], ymd[0]) + (bc ? " BC" : "");
            case 3 -> "%d-%d-%d".formatted(ymd[0], ymd[1] + 1, ymd[2]) + (bc ? " BC" : "");
            case 4 -> "%d-%d-%d".formatted(ymd[2], ymd[1] + 1, ymd[0]) + (bc ? " BC" : "");
            case 5 -> "%d-%d-%d".formatted(ymd[1] + 1, ymd[2], ymd[0]) + (bc ? " BC" : "");
            default -> "";
        };
    }

    @Override
    public void VerifyFlawsDateArray(int[] ymd){ // Default format: yyyy - mm - dd
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
            throw new IllegalArgumentException("Date does not exist due to Gregorian calendar reform!\nSee more in https://en.wikipedia.org/wiki/Adoption_of_the_Gregorian_calendar");
        }
    }

    @Override
    public int DaysInMonth(int year, int month){
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
        if (year % 4 == 0) {
            if (year > 1600) {
                if (year % 400 == 0) {
                    return true;
                } else if (year % 100 == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
