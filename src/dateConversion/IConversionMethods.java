package dateConversion;

public interface IConversionMethods {
    int[] DecodeDate(String strDate, int format);
    String EncodeDate(int[] ymd, int format);
    void VerifyFlawsDateArray(int[] ymd);
    int DaysInMonth(int year, int month);
}
