package schmille.bamboo2.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import schmille.bamboo2.common.util.FoodUtil;

public class CookedBambooItem extends Item {

    public CookedBambooItem() {
        super(new Item.Properties().group(ItemGroup.FOOD).food(FoodUtil.createCookedBamboo()));
        this.setRegistryName("cooked_bamboo");
    }
}
