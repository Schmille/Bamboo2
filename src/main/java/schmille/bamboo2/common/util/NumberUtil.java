package schmille.bamboo2.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 * This class has been created to handle the values read via this mods configuration file in a way that is useful to its operation
 */
public abstract class NumberUtil {

    /**
     * Converts a double to a float by formatting it to a String and then parsing it again <br>
     * using <code>default = 0.3</code>, <code>upper = 999999.999999</code>, <code>lower = 0</code>
     * @param in the value to be converted
     * @return <code>in</code> converted to a float value
     */
    public static float doubleToFloat(double in) {
        return doubleToFloat(in,0.3F,999999.999999F,0);
    }

    /**
     * Converts a double to a float by formatting it to a String and then parsing it again
     * @param in the value to be converted
     * @param defaultVal the value to be output if <code>in</code> is not within specified borders
     * @param upper the upper limit
     * @param lower the lower limit
     * @return <code>in</code> converted to a float value
     */
    public static float doubleToFloat(double in, float defaultVal, float upper, float lower) {
        if(in > upper || in < lower)
            return  defaultVal;

        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyLocalizedPattern("000000.000000");
        String s = df.format(in);
        return Float.parseFloat(s);
    }

    /**
     * Takes a double value and returns an integer representing an order of magnitude. <br>
     * For example <code>percent = 0.03</code> will return <code>100</code>. <br>
     * In other words, this method answers the question:
     * "Which multiple of 10 do I need to multiply with to get a representation of <code>percent</code> as a whole number?"
     * @param percent a percent value
     * @return multiple of 10
     */
    public static int getPercentileDenominator(double percent) {
        int denominator = 10;

        while((denominator * percent) % 1 != 0)
            denominator *= 10;

        return denominator;
    }

    /**
     * Takes a percent value and returns new random double.
     * @param percentile a percentile input
     * @param random a the Random object to use
     * @return a value from 0 to 1
     */
    public static double randomChanceFromPercentile(double percentile, Random random) {
        if(percentile == 1D)
            return 1;

        if(percentile == 0D)
            return 0;

        // Assume 0.000001 as lowest reasonable minimum
        percentile = Math.max(percentile, 0.000001);
        int denominator = getPercentileDenominator(percentile);

        return random.nextInt(denominator) / ((double) denominator);
    }
}
