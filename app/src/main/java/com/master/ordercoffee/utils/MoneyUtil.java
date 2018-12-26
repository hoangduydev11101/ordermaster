package com.master.ordercoffee.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyUtil {

    public static String formatMoney(long monney) {
        DecimalFormat decimalFormat = new DecimalFormat("###.###.###");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(monney);
    }

    public static long getMoney(String target) {
        if (TextUltil.stringIsNullOrEmpty(target))
            return 0;

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(target);
        String number = matcher.replaceAll("");

        return TextUltil.stringIsNullOrEmpty(number) ? 0 : Long.parseLong(number);
    }

}
