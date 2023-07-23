package schmille.bamboo2.common.items;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import schmille.bamboo2.Bamboo2;
import schmille.bamboo2.Ref;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class Register {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.MOD_ID);
    public static final RegistryObject<CookedBambooItem> COOKED_BAMBOO = ITEMS.register(CookedBambooItem.REGISTRY_NAME, CookedBambooItem::new);

    @SubscribeEvent
    public static void onCreativeTabEvent(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey()== CreativeModeTabs.FOOD_AND_DRINKS) {
            Bamboo2.getLogger().info("Registering CookedBamboo in food tab");
            event.accept(COOKED_BAMBOO);
        }
    }
}
