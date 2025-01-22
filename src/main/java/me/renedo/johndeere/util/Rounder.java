package me.renedo.johndeere.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {

    public static Double round(Double value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
