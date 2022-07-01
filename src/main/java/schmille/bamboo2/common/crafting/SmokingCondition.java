package schmille.bamboo2.common.crafting;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import schmille.bamboo2.common.Configuration;

public class SmokingCondition implements IConditionSerializer {

    @Override
    public void write(JsonObject json, ICondition value) {

    }

    @Override
    public ICondition read(JsonObject json) {
        return new ICondition() {
            @Override
            public ResourceLocation getID() {
                return new ResourceLocation("bamboo2:smoke");
            }

            @Override
            public boolean test() {
                return Configuration.COOKED_BAMBOO.smokable.get();
            }
        };
    }

    @Override
    public ResourceLocation getID() {
        return new ResourceLocation("bamboo2:smokable");
    }
}
