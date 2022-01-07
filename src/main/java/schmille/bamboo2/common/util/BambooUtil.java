package schmille.bamboo2.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import schmille.bamboo2.Bamboo2;
import schmille.bamboo2.common.Configuration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public abstract class BambooUtil {

    public static void doSpread(LevelAccessor world, BlockPos pos) {
        List<BlockPos> positions = BambooUtil.listBlockPos(pos, Configuration.BAMBOO_SPREAD);
        Collections.shuffle(positions);
        for(BlockPos bp : positions) {
            BlockState state = world.getBlockState(bp);

            if(state.getBlock() instanceof AirBlock) {
                BlockState down = world.getBlockState(bp.below(1));

                if(ConfigBlockUtil.canSpreadTo(down.getBlock())) {
                    world.setBlock(bp, Blocks.BAMBOO_SAPLING.defaultBlockState(), (1|2));
                    Bamboo2.getLogger().debug(String.format("doSpread: Placed bamboo sapling at %d %d %d", bp.getX(), bp.getY(), bp.getZ()));
                    break;
                }
            }
        }
    }

    public static BlockPos findRoot(LevelAccessor world, BlockPos pos) {
        BlockPos last = pos;

        for(int i = 0; i < 255; i++) {
            BlockState state = world.getBlockState(pos.below(i));

            if(!state.getBlock().equals(Blocks.BAMBOO))
                break;

            last = pos.below(i);
        }

        return last;
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onCropGrowth(BlockEvent.CropGrowEvent event) {

        if(!Configuration.BAMBOO_SPREAD.do_bamboo_spread.get())
            return;

        if(event.getWorld().isClientSide())
            return;

        if(event.getState().getBlock() instanceof BambooBlock bamboo) {

            if (Configuration.BAMBOO_SPREAD.only_spread_grown.get() && !checkGrownAbove(event.getWorld(), event.getPos()))
                return;

            double chance = NumberUtil.randomChanceFromPercentile(Configuration.BAMBOO_SPREAD.spread_chance.get(), event.getWorld().getRandom());
            if (chance <= Configuration.BAMBOO_SPREAD.spread_chance.get()) {
                Bamboo2.getLogger().debug(String.format("onCropGrowth: Bamboo spread triggered with value (%f <= %f) on Block %d %d %d",
                        chance, Configuration.BAMBOO_SPREAD.spread_chance.get(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()));
                doSpread(event.getWorld(), findRoot(event.getWorld(), event.getPos()));
            }
        }
    }

    public static MobEffectInstance newSlownessEffect() {
        return new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Configuration.RAW_BAMBOO.slowness_duration.get() * 20, Configuration.RAW_BAMBOO.slowness_level.get() - 1);
    }

    private static List<BlockPos> listBlockPos(int xMax, int yMax, int zMax, int xMin, int yMin, int zMin) {
        final List<BlockPos> output = new LinkedList<>();
        for(int x = xMin; x <= xMax; x++) {
            for(int y = yMin; y <= yMax; y++) {
                for(int z = zMin; z <= zMax; z++) {
                    output.add(new BlockPos(x, y, z));
                }
            }
        }
        return output;
    }

    private static boolean checkGrownAbove(LevelAccessor world, BlockPos pos) {
        var above = pos.above();
        var state = world.getBlockState(above);

        if (state.getBlock() instanceof BambooBlock ba) {
            return state.getValue(BlockStateProperties.STAGE) == BambooBlock.STAGE_DONE_GROWING;
        }
        else {
            return false;
        }
    }

    public static List<BlockPos> listBlockPos(BlockPos base, Configuration.BambooSpread config) {
        int x = base.getX();
        int y = base.getY();
        int z = base.getZ();

        return listBlockPos(x + config.spread_x.get(), y + config.spread_y_top.get(), z + config.spread_z.get(),
                x - config.spread_z.get(), y - config.spread_y_bottom.get(), z - config.spread_z.get());
    }
}
