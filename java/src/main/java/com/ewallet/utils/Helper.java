package com.ewallet.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    public String FormattedToRupiah(long amount){

        Locale localIndonesia = Locale.of("id", "ID");
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localIndonesia);
        
        formatter.setMaximumFractionDigits(0);
        
        return formatter.format(amount);
    }


}
