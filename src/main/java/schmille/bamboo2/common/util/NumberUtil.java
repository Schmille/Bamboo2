package schmille.bamboo2.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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
}
