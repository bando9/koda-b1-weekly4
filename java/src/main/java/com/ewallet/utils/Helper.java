package com.ewallet.utils;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Helper {

    public String FormattedToRupiah(long amount){

        Locale localIndonesia = Locale.of("id", "ID");
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localIndonesia);
        
        formatter.setMaximumFractionDigits(0);
        
        return formatter.format(amount);
    }

    public String formatDate(LocalDateTime date){
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy",Locale.forLanguageTag("id-ID"));
         return date.format(formatter);
    }


}
