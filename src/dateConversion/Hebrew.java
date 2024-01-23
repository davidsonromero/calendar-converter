package dateConversion;

public class Hebrew implements IConversionMethods {
    public Hebrew(){}
    @Override
    public int[] decodeDate(String strDate, int format) {
        int[] hebYmd = {0, 0, 0};
        String[] values = strDate.split(" ")[0].split(format < 6 ? "/" : format < 9 ? "-" : " ");
        switch (format % 3) {
            case 0: // yyyy/mm/dd, yyyy-mm-dd, yyyy mm dd
                assignValues(hebYmd, values, 0, 1, 2);
                break;
            case 1: // dd/mm/yyyy, dd-mm-yyyy, dd mm yyyy
                assignValues(hebYmd, values, 2, 1, 0);
                break;
            case 2: // mm/dd/yyyy, mm-dd-yyyy, mm dd yyyy
                assignValues(hebYmd, values, 2, 0, 1);
                break;
        }
        verifyFlawsDateArray(hebYmd);
        return ConvertDate(hebYmd);
    }

    private void assignValues(int[] ymd, String[] values, int yearIndex, int monthIndex, int dayIndex) {
        ymd[0] = Integer.parseInt(values[yearIndex]);
        ymd[1] = Integer.parseInt(values[monthIndex]) - 1;
        ymd[2] = Integer.parseInt(values[dayIndex]);
    }

    private int[] ConvertDate(int[] hebYmd) {
        int[] ymd = {0, 0, 0};



        return ymd;
    }

    private boolean isShanaMeuberet(int year) {
        int cycleStage = year % 19;
        boolean isShanaMeuberet = false;
        switch (cycleStage){
            case 0:
            case 3:
            case 6:
            case 8:
            case 11:
            case 14:
            case 17:
                isShanaMeuberet = true;
                break;
        }
        return isShanaMeuberet;
    }

    private int dayOfWeekNewYear(int[] ymd){
        return 0;
    }

    @Override
    public String encodeDate(int[] ymd, int format) {
        return null;
    }

    @Override
    public void verifyFlawsDateArray(int[] ymd) {
        if(ymd.length != 3){
            throw new IllegalArgumentException("Your ymd array must have exactly 3 elements!");
        }
        if(isShanaMeuberet(ymd[0]) && (ymd[1] > 12 || ymd[1] < 0)){
            throw new IllegalArgumentException("Invalid month!");
        }
        if(!isShanaMeuberet(ymd[0]) && (ymd[1] > 11 || ymd[1] < 0)){
            throw new IllegalArgumentException("Invalid month!");
        }
        if(ymd[0] == 0){
            throw new IllegalArgumentException("Year 0 does not exist!");
        }
        //TODO: verify month length
    }

    @Override
    public int daysInMonth(int year, int month) {
        return 0;
    }
}
