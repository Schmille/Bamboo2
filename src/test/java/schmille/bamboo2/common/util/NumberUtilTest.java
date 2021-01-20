package schmille.bamboo2.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class NumberUtilTest {

    @Test
    public void testDoubleToFloatDefault() {
        final double input = 0.8D;
        final float output = NumberUtil.doubleToFloat(input);

        Assert.assertEquals(input, output, 0.01);
    }

    @Test
    public void testDoubleToFloatUpperLimit() {
        final double input = 1.1D;
        final float output = NumberUtil.doubleToFloat(input, 0.3F, 1.0F, 0F);

        Assert.assertEquals(0.3, output, 0.000001);
    }

    @Test
    public void testDoubleToFloatLowerLimit() {
        final double input = -5D;
        final float output = NumberUtil.doubleToFloat(input, 0.3F, 1.0F, 0F);

        Assert.assertEquals(0.3, output, 0.000001);
    }

    @Test
    public void testGetPercentileDenominator() {
        final double percentile = 0.003;
        final int output = NumberUtil.getPercentileDenominator(percentile);

        Assert.assertEquals(1000, output);
    }

    @Test
    public void testRandomChanceFromPercentile() {
        final double percentile = 0.03;
        final double output = NumberUtil.randomChanceFromPercentile(percentile, new Random());

        Assert.assertTrue(output <= 1 && output >= 0);
    }
}