package com.mindhub.homebanking.Utils;

public final class CardUtils {


    private CardUtils() {
    }

    //public static void getCvvN() {
    public static int getCvvN() {
        int cvvN = (int) ((Math.random() * (999 - 100) + 100));
    return cvvN;
    }

        //public static void getCardNumber() {
        public static String getCardNumber () {
            //String CardNumber="";
            String CardNumber = (int) ((Math.random() * (9999 - 1000)) + 1000)
                    + " " + (int) ((Math.random() * (9999 - 1000)) + 1000)
                    + " " + (int) ((Math.random() * (9999 - 1000)) + 1000)
                    + " " + (int) ((Math.random() * (9999 - 1000)) + 1000);
            return CardNumber;
        }

}