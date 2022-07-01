package schmille.bamboo2.common.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import schmille.bamboo2.common.util.FoodUtil;

public class CookedBambooItem extends Item {

    public static final String REGISTRY_NAME = "cooked_bamboo";

    public CookedBambooItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(FoodUtil.createCookedBamboo()));
    }
}
