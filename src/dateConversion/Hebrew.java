package dateConversion;

public class Hebrew implements IConversionMethods {
    public Hebrew(){}
    @Override
    public int[] DecodeDate(String strDate, int format) {
        int[] ymd = {0, 0, 0};
        int[] hebYmd = {0, 0, 0};

        String[] values;
        switch (format) {
            case 0: // yyyy/mm/dd
                values = strDate.split(" ")[0].split("/");
                hebYmd[0] = Integer.parseInt(values[0]);
                hebYmd[1] = Integer.parseInt(values[1]) - 1;
                hebYmd[2] = Integer.parseInt(values[2]);
                break;
            case 1: // dd/mm/yyyy
                values = strDate.split(" ")[0].split("/");
                hebYmd[0] = Integer.parseInt(values[2]);
                hebYmd[1] = Integer.parseInt(values[1]) - 1;
                hebYmd[2] = Integer.parseInt(values[0]);
                break;
            case 2: // mm/dd/yyyy
                values = strDate.split(" ")[0].split("/");
                hebYmd[0] = Integer.parseInt(values[2]);
                hebYmd[1] = Integer.parseInt(values[0]) - 1;
                hebYmd[2] = Integer.parseInt(values[1]);
                break;
            case 3: // yyyy-mm-dd
                values = strDate.split(" ")[0].split("-");
                hebYmd[0] = Integer.parseInt(values[0]);
                hebYmd[1] = Integer.parseInt(values[1]) - 1;
                hebYmd[2] = Integer.parseInt(values[2]);
                break;
            case 4: // dd-mm-yyyy
                values = strDate.split(" ")[0].split("-");
                hebYmd[0] = Integer.parseInt(values[2]);
                hebYmd[1] = Integer.parseInt(values[1]) - 1;
                hebYmd[2] = Integer.parseInt(values[0]);
                break;
            case 5: // mm-dd-yyyy
                values = strDate.split(" ")[0].split("-");
                hebYmd[0] = Integer.parseInt(values[2]);
                hebYmd[1] = Integer.parseInt(values[0]) - 1;
                hebYmd[2] = Integer.parseInt(values[1]);
                break;
            case 6: // yyyy mm dd
                values = strDate.split(" ")[0].split(" ");
                hebYmd[0] = Integer.parseInt(values[0]);
                hebYmd[1] = Integer.parseInt(values[1]) - 1;
                hebYmd[2] = Integer.parseInt(values[2]);
                break;
            case 7: // dd mm yyyy
                values = strDate.split(" ")[0].split(" ");
                hebYmd[0] = Integer.parseInt(values[2]);
                hebYmd[1] = Integer.parseInt(values[1]) - 1;
                hebYmd[2] = Integer.parseInt(values[0]);
                break;
            case 8: // mm dd yyyy
                values = strDate.split(" ")[0].split(" ");
                hebYmd[0] = Integer.parseInt(values[2]);
                hebYmd[1] = Integer.parseInt(values[0]) - 1;
                hebYmd[2] = Integer.parseInt(values[1]);
                break;
        }
        VerifyFlawsDateArray(hebYmd);
        ymd = ConvertDate(hebYmd);
        return ymd;
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
    public String EncodeDate(int[] ymd, int format) {
        return null;
    }

    @Override
    public void VerifyFlawsDateArray(int[] ymd) {
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
    public int DaysInMonth(int year, int month) {
        return 0;
    }
}
