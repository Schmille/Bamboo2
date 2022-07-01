package schmille.bamboo2;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import schmille.bamboo2.common.Configuration;
import schmille.bamboo2.common.crafting.CampfireCondition;
import schmille.bamboo2.common.crafting.CookCondition;
import schmille.bamboo2.common.crafting.SmokingCondition;
import schmille.bamboo2.common.items.Register;
import schmille.bamboo2.common.util.FoodUtil;
import schmille.bamboo2.common.util.NumberUtil;

import java.lang.reflect.Field;

@Mod(Ref.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Bamboo2 {

    private static final Logger LOGGER = LogManager.getLogger(Ref.MOD_ID);

    public Bamboo2() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.SPEC);
        Configuration.loadConfig();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        Register.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        if (Configuration.RAW_BAMBOO.edible.get()) {
            try {
                Field food = getField();
                if (!food.trySetAccessible()) {
                    getLogger().error("Bamboo reflection failed! Could not make accessible!");
                    return;
                }
                food.set(Items.BAMBOO, FoodUtil.createRawBamboo());
                getLogger().info("Bamboo food reflection succeeded");
            } catch (IllegalAccessException e) {
                getLogger().error("Bamboo reflection failed", e);
            }
        }
    }

    private static Field getField() {
        return ObfuscationReflectionHelper.findField(Item.class, "f_41380_");
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private void commonSetup(FMLCommonSetupEvent evt) {
        if (Configuration.COMPOSTER.can_decompose.get()) {
            ComposterBlock.COMPOSTABLES.computeFloat(Items.BAMBOO, (iItemProvider, aFloat) -> NumberUtil.doubleToFloat(Configuration.COMPOSTER.compost_chance.get()));
        }

        CraftingHelper.register(new CookCondition());
        CraftingHelper.register(new SmokingCondition());
        CraftingHelper.register(new CampfireCondition());
    }

}
