package schmille.bamboo2;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import schmille.bamboo2.common.Configuration;
import schmille.bamboo2.common.crafting.CookCondition;
import schmille.bamboo2.common.foodstuff.ModFood;
import schmille.bamboo2.common.items.CookedBambooItem;
import schmille.bamboo2.common.util.NumberUtil;

@Mod(Ref.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Bamboo2 {

    private static final Logger LOGGER = LogManager.getLogger();

    public Bamboo2()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.SPEC);
        Configuration.loadConfig();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent evt)
    {
        if(Configuration.COMPOSTER.can_decompose.get())
        {
            ComposterBlock.CHANCES.computeFloat(Items.BAMBOO, (iItemProvider, aFloat) -> NumberUtil.doubleToFloat(Configuration.COMPOSTER.compost_chance.get()));
        }

        CraftingHelper.register(new CookCondition());
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event)
    {
        if(Configuration.RAW_BAMBOO.edible.get())
        {
            Items.BAMBOO.food = ModFood.initRawBamboo();
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new CookedBambooItem());
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
