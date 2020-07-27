package schmille.bamboo2.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

//Highly annoying util class, needed because forge config spec can seemingly not load float values
public abstract class NumberUtil {
    public static float doubleToFloat(double in)
    {
        return doubleToFloat(in,0.3F,999999.999999F,0);
    }

    public static float doubleToFloat(double in, float defaultVal, float upper, float lower)
    {
        if(in > upper || in < lower)
            return  defaultVal;

        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyLocalizedPattern("000000.000000");
        String s = df.format(in);
        return Float.parseFloat(s);
    }

    public static int getPercentileDenominator(double percent) {
        int denominator = 10;

        while((denominator * percent) % 1 != 0)
            denominator *= 10;

        return denominator;
    }

    public static double randomChanceFromPercentile(double percentile, Random random) {
        if(percentile == 1D)
            return 1;

        if(percentile == 0D)
            return 0;

        // Assume 0.000001 as lowest reasonable minimum
        percentile = percentile > 0.000001 ? percentile : 0.000001;
        int denominator = getPercentileDenominator(percentile);

        return random.nextInt(denominator) / ((double) denominator);
    }
}
