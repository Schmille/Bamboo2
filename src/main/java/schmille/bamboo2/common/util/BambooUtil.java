package schmille.bamboo2.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.fml.common.Mod;
import schmille.bamboo2.common.Configuration;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public abstract class BambooUtil {

    public static MobEffectInstance newSlownessEffect() {
        return new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Configuration.RAW_BAMBOO.slowness_duration.get() * 20, Configuration.RAW_BAMBOO.slowness_level.get() - 1);
    }

    private static List<BlockPos> listBlockPos(int xMax, int yMax, int zMax, int xMin, int yMin, int zMin) {
        final List<BlockPos> output = new LinkedList<>();
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    output.add(new BlockPos(x, y, z));
                }
            }
        }
        return output;
    }
}
