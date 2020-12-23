package schmille.bamboo2.common.util;

import net.minecraft.item.Food;
import schmille.bamboo2.common.Configuration;

public abstract class FoodUtil {

    public static Food createRawBamboo() {

        float sat_value = NumberUtil.doubleToFloat(Configuration.RAW_BAMBOO.saturation_value.get());

        Food.Builder raw_bamboo_builder = new Food.Builder();
        raw_bamboo_builder.hunger(Configuration.RAW_BAMBOO.hunger_value.get())
                .saturation(sat_value);

        if (Configuration.RAW_BAMBOO.apply_slowness.get())
            raw_bamboo_builder.effect(BambooUtil::newSlownessEffect, NumberUtil.doubleToFloat(Configuration.RAW_BAMBOO.effect_chance.get()));

        return raw_bamboo_builder.build();
    }

    public static Food createCookedBamboo() {

        float sat_value = NumberUtil.doubleToFloat(Configuration.COOKED_BAMBOO.saturation_value.get());
        return (new Food.Builder()).hunger(Configuration.COOKED_BAMBOO.hunger_value.get()).saturation(sat_value).build();
    }
}
