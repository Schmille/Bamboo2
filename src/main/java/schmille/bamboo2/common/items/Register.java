package schmille.bamboo2.common.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import schmille.bamboo2.Ref;

public abstract class Register {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.MOD_ID);
    public static final RegistryObject COOKED_BAMBOO = ITEMS.register(CookedBambooItem.REGISTRY_NAME, CookedBambooItem::new);

}
