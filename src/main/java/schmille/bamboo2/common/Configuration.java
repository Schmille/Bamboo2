package schmille.bamboo2.common;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import schmille.bamboo2.Bamboo2;
import schmille.bamboo2.Ref;

public class Configuration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    //
    public static final Composter COMPOSTER = new Composter(BUILDER);
    public static final RawBamboo RAW_BAMBOO = new RawBamboo(BUILDER);
    public static final CookedBamboo COOKED_BAMBOO = new CookedBamboo(BUILDER);
    //
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static void loadConfig() {
        final String path = String.format("%s-common.toml", Ref.MOD_ID);
        Bamboo2.getLogger().info("Processing config: " + path);

        final CommentedFileConfig file = CommentedFileConfig.builder(path).sync().preserveInsertionOrder().writingMode(WritingMode.REPLACE).build();
        Bamboo2.getLogger().info("Built config: " + path);

        file.load();
        Bamboo2.getLogger().info("Loaded config: " + path);
        SPEC.setConfig(file);

        Bamboo2.getLogger().info("Config done.");
    }

    public static class Composter {
        public final ForgeConfigSpec.ConfigValue<Boolean> can_decompose;
        public final ForgeConfigSpec.ConfigValue<Double> compost_chance;

        public Composter(ForgeConfigSpec.Builder builder) {
            builder.push("Composter");

            can_decompose = builder
                    .comment("If true, bamboo can be composted")
                    .define("can_decompose", true);

            compost_chance = builder
                    .comment("Sets the chance for bamboo to partly fill a composter. The higher the value, the less bamboo is needed to fill a composter.\n(Leaves = 0.3, Sugar Cane = 0.5, Wheat = 0.65, Bread = 0.85, Cake = 1.0)")
                    .define("compose_chance", 0.3D);

            builder.pop();
        }
    }

    public static class RawBamboo {
        public final ForgeConfigSpec.ConfigValue<Boolean> edible;
        public final ForgeConfigSpec.ConfigValue<Integer> hunger_value;
        public final ForgeConfigSpec.ConfigValue<Double> saturation_value;

        public final ForgeConfigSpec.ConfigValue<Boolean> apply_slowness;
        public final ForgeConfigSpec.ConfigValue<Integer> slowness_duration;
        public final ForgeConfigSpec.ConfigValue<Integer> slowness_level;
        public final ForgeConfigSpec.ConfigValue<Double> effect_chance;

        public RawBamboo(ForgeConfigSpec.Builder builder) {
            builder.push("Raw Bamboo");

            edible = builder
                    .comment("If true, bamboo can be eaten.")
                    .define("can_eat_raw", true);

            hunger_value = builder
                    .comment("How much hunger will be restored when eaten.")
                    .define("raw_hunger_value", 2);

            saturation_value = builder
                    .comment("Modifier for how fast the hungerbar will deplete (lower means faster)")
                    .define("raw_saturation_value", 0.3D);
            builder.pop();
            builder.push("Raw bamboo slowness");

            apply_slowness = builder
                    .comment("If true, eating raw bamboo will apply slowness")
                    .define("raw_apply_slowness", true);

            slowness_duration = builder
                    .comment("Duration of the slowness effect (in seconds)")
                    .define("raw_slowness_duration", 5);

            slowness_level = builder
                    .comment("Slowness level")
                    .define("raw_slowness_level", 1);

            effect_chance = builder
                    .comment("Chance that the slowness effect is triggered")
                    .define("raw_slowness_chance", 1.0D);

            builder.pop();
        }
    }

    public static class CookedBamboo {
        public final ForgeConfigSpec.ConfigValue<Boolean> cookable;
        public final ForgeConfigSpec.ConfigValue<Boolean> smokable;
        public final ForgeConfigSpec.ConfigValue<Boolean> campfire_cooking;
        public final ForgeConfigSpec.ConfigValue<Integer> hunger_value;
        public final ForgeConfigSpec.ConfigValue<Double> saturation_value;

        public CookedBamboo(ForgeConfigSpec.Builder builder) {
            builder.push("Cooked Bamboo");

            cookable = builder
                    .comment("If false, you will not be able to obtain cooked bamboo through smelting")
                    .define("cookable", true);

            smokable = builder
                    .comment("If false, you will not be able to obtain cooked bamboo through smoking")
                    .define("smokable", true);

            campfire_cooking = builder
                    .comment("If false, you will not be able to obtain cooked bamboo through campfire cooking")
                    .define("campfire_cooking", true);

            hunger_value = builder
                    .comment("How much hunger will be restored when eaten.")
                    .define("cooked_hunger_value", 4);

            saturation_value = builder
                    .comment("Modifier for how fast the hungerbar will deplete (lower means faster)")
                    .define("cooked_saturation_value", 0.5D);

            builder.pop();
        }
    }
}
