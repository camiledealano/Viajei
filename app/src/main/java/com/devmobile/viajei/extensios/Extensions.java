package com.devmobile.viajei.extensios;

import java.text.NumberFormat;
import java.util.Locale;

public class Extensions {
    public static double parseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }

    public static int parseInt(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }


    public static String formatToBRL(double value){
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeBR);
        return currencyFormatter.format(value);
    }
}
