package com.projekt.forum.utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtility {
     public static Date getCurrentDate(){
         LocalDateTime localDateTime = LocalDateTime.now();
         return Date.from(localDateTime.toInstant(ZoneOffset.ofHours(2)));
     }

}
