package strutsoftheworld.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.capability.StrutsWeatherCapability;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SOTWDimensionSpecialEffects {
    public static final ResourceLocation STRUTS_EFFECTS = createLoc("struts");

    private static ResourceLocation createLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name);
    }

    @SubscribeEvent
    public static void registerEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(STRUTS_EFFECTS, new StrutsEffects());
    }

    public static class StrutsEffects extends DimensionSpecialEffects {
        public StrutsEffects() {
            super(Float.NaN, true, SkyType.NONE, false, false);
        }

        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
            return fogColor
                .scale(0.9f * brightness + 0.1f)
                .scale(1f - 0.6f * StrutsWeatherCapability.ClientData.getRainStrength());
        }

        @Override
        public boolean isFoggyAt(int pX, int pY) {
            return true;
        }
    }
}
