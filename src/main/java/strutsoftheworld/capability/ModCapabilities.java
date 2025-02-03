package strutsoftheworld.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.dimension.ModDimensions;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCapabilities {
    public static final Capability<IStrutsWeatherCapability> STRUTS_WEATHER = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation STRUTS_WEATHER_LOC = createLoc("struts_weather");

    private static ResourceLocation createLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name);
    }

    @SubscribeEvent
    public static void attachLevelCapabilities(AttachCapabilitiesEvent<Level> event) {
        if (event.getObject().dimension().location().equals(ModDimensions.STRUTS_OF_THE_WORLD.location())) {
            var weatherProv = new StrutsWeatherCapability.Provider();
            event.addCapability(STRUTS_WEATHER_LOC, weatherProv);
            event.addListener(weatherProv::invalidate);
        }
    }
}
