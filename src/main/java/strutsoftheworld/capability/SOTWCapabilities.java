package strutsoftheworld.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.dimension.SOTWDimensions;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SOTWCapabilities {
    public static final Capability<StrutsWeatherCapability> STRUTS_WEATHER = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation STRUTS_WEATHER_LOC = createLoc("struts_weather");

    private static ResourceLocation createLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name);
    }

    @SubscribeEvent
    public static void attachLevelCapabilities(AttachCapabilitiesEvent<Level> event) {
        if (event.getObject().dimension().location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())) {
            var weatherProv = new StrutsWeatherCapability.Provider();
            event.addCapability(STRUTS_WEATHER_LOC, weatherProv);
            event.addListener(weatherProv::invalidate);
        }
    }
}
