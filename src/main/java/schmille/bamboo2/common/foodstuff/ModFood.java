package schmille.bamboo2.common.foodstuff;

import net.minecraft.item.Food;
import schmille.bamboo2.common.Configuration;
import schmille.bamboo2.common.util.BambooUtil;
import schmille.bamboo2.common.util.NumberUtil;

public class ModFood {

    public static Food initRawBamboo() {
        float sat_value = NumberUtil.doubleToFloat(Configuration.RAW_BAMBOO.saturation_value.get());

        Food.Builder raw_bamboo_builder = new Food.Builder();
        raw_bamboo_builder.hunger(Configuration.RAW_BAMBOO.hunger_value.get())
                .saturation(sat_value);

        if (Configuration.RAW_BAMBOO.apply_slowness.get())
            raw_bamboo_builder.effect(BambooUtil::newSlownessEffect, NumberUtil.doubleToFloat(Configuration.RAW_BAMBOO.effect_chance.get()));

        return raw_bamboo_builder.build();
    }

    public static Food initCookedBamboo()
    {
        float sat_value = NumberUtil.doubleToFloat(Configuration.COOKED_BAMBOO.saturation_value.get());

        Food.Builder cooked_bamboo_builder = new Food.Builder();
        cooked_bamboo_builder
                .hunger(Configuration.COOKED_BAMBOO.hunger_value.get())
                .saturation(sat_value);

        return cooked_bamboo_builder.build();
    }
}
