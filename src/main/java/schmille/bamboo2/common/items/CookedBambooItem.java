package schmille.bamboo2.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import schmille.bamboo2.common.foodstuff.ModFood;

public class CookedBambooItem extends Item {

    public CookedBambooItem() {
        super(new Item.Properties().group(ItemGroup.FOOD).food(ModFood.initCookedBamboo()));
        this.setRegistryName("cooked_bamboo");
    }
}
