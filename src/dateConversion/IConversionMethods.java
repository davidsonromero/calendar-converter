package dateConversion;

public interface IConversionMethods {
    int[] decodeDate(String strDate, int format);
    String encodeDate(int[] ymd, int format);
    void verifyFlawsDateArray(int[] ymd);
    int daysInMonth(int year, int month);
}
