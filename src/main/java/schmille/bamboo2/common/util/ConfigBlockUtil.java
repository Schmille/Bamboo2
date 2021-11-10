package schmille.bamboo2.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import schmille.bamboo2.common.Configuration;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigBlockUtil {

    private static final List<ResourceLocation> blocks = new ArrayList<>();

    public static boolean canSpreadTo(Block block) {
        tryFillList();

        return blocks.contains(block.getRegistryName());
    }

    private static void tryFillList() {
        if(!blocks.isEmpty())
            return;

        String[] split = Configuration.BAMBOO_SPREAD.spread_to.get().split(",");
        for(String s : split)
            blocks.add(ResourceLocation.tryParse(s.trim()));
    }
}
