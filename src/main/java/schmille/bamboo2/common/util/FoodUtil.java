package schmille.bamboo2.common.util;

import net.minecraft.world.food.FoodProperties;
import schmille.bamboo2.common.Configuration;

public abstract class FoodUtil {

    public static FoodProperties createRawBamboo() {

        float sat_value = NumberUtil.doubleToFloat(Configuration.RAW_BAMBOO.saturation_value.get());

        FoodProperties.Builder raw_bamboo_builder = new FoodProperties.Builder();
        raw_bamboo_builder
                .nutrition(Configuration.RAW_BAMBOO.hunger_value.get())
                .saturationMod(sat_value);

        if (Configuration.RAW_BAMBOO.apply_slowness.get())
            raw_bamboo_builder.effect(BambooUtil::newSlownessEffect, NumberUtil.doubleToFloat(Configuration.RAW_BAMBOO.effect_chance.get()));

        return raw_bamboo_builder.build();
    }

    public static FoodProperties createCookedBamboo() {
        float sat_value = NumberUtil.doubleToFloat(Configuration.COOKED_BAMBOO.saturation_value.get());
        return (new FoodProperties.Builder()).nutrition(Configuration.COOKED_BAMBOO.hunger_value.get()).saturationMod(sat_value).build();
    }
}
