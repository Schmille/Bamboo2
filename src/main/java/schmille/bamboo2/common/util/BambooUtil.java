package schmille.bamboo2.common.util;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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

    public static void doSpread(IWorld world, BlockPos pos) {
        Iterable<BlockPos> i = BlockPos.getAllInBoxMutable(
               pos.add(-Configuration.BAMBOO_SPREAD.spread_x.get(), -Configuration.BAMBOO_SPREAD.spread_y_bottom.get(), -Configuration.BAMBOO_SPREAD.spread_z.get()),
               pos.add(Configuration.BAMBOO_SPREAD.spread_x.get(), Configuration.BAMBOO_SPREAD.spread_y_top.get(), Configuration.BAMBOO_SPREAD.spread_z.get()));

        List<BlockPos> positions = new LinkedList<>();
        for(BlockPos bp: i)
            positions.add(bp.toImmutable());

        Collections.shuffle(positions);
        for(BlockPos bp : positions) {
            BlockState state = world.getBlockState(bp);

            if(state.getBlock() instanceof AirBlock) {
                BlockState down = world.getBlockState(bp.down(1));

                if(ConfigBlockUtil.canSpreadTo(down.getBlock())) {
                    world.setBlockState(bp, Blocks.BAMBOO_SAPLING.getDefaultState(), (1|2));
                    Bamboo2.getLogger().debug(String.format("doSpread: Placed bamboo sapling at %d %d %d", bp.getX(), bp.getY(), bp.getZ()));
                    break;
                }
            }
        }
    }

    public static BlockPos findRoot(IWorld world, BlockPos pos) {
        BlockPos last = pos;

        for(int i = 0; i < 255; i++) {
            BlockState state = world.getBlockState(pos.down(i));

            if(!state.getBlock().equals(Blocks.BAMBOO))
                break;

            last = pos.down(i);
        }

        return last;
    }

    @SubscribeEvent
    public static void onCropGrowth(BlockEvent.CropGrowEvent event) {

        if(!Configuration.BAMBOO_SPREAD.do_bamboo_spread.get())
            return;

        if(event.getWorld().isRemote())
            return;

        if(!(event.getState().getBlock() instanceof BambooBlock))
            return;

        if(Configuration.BAMBOO_SPREAD.only_spread_grown.get() && ((BambooBlock) event.getState().getBlock()).canGrow(event.getWorld(), event.getPos(), event.getState(), event.getWorld().isRemote()))
            return;

            double chance = NumberUtil.randomChanceFromPercentile(Configuration.BAMBOO_SPREAD.spread_chance.get(), event.getWorld().getRandom());
            if(chance <= Configuration.BAMBOO_SPREAD.spread_chance.get()) {
                Bamboo2.getLogger().debug(String.format("onCropGrowth: Bamboo spread triggered with value (%f <= %f) on Block %d %d %d",
                        chance, Configuration.BAMBOO_SPREAD.spread_chance.get(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()));
                doSpread(event.getWorld(), findRoot(event.getWorld(), event.getPos()));
        }
    }

    public static EffectInstance newSlownessEffect() {
        return new EffectInstance(Effects.SLOWNESS, Configuration.RAW_BAMBOO.slowness_duration.get() * 20, Configuration.RAW_BAMBOO.slowness_level.get() - 1);
    }
}
