package strutsoftheworld;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import strutsoftheworld.block.SOTWBlocks;
import strutsoftheworld.item.SOTWCreativeModeTabs;
import strutsoftheworld.item.SOTWItems;
import strutsoftheworld.network.SOTWNetworkHandler;
import strutsoftheworld.particle.SOTWParticles;
import strutsoftheworld.sound.SOTWSoundEvents;
import strutsoftheworld.worldgen.feature.SOTWFeatures;

@Mod(StrutsOfTheWorldMod.MOD_ID)
public class StrutsOfTheWorldMod {
    public static final String MOD_ID = "strutsoftheworld";
    public static final String MOD_NAME = "Struts of the World";
    public static final Logger LOGGER = LogUtils.getLogger();

    public StrutsOfTheWorldMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);

        // Register mod contents
        SOTWCreativeModeTabs.register(bus);
        SOTWItems.register(bus);
        SOTWBlocks.register(bus);
        SOTWFeatures.register(bus);

        SOTWSoundEvents.register(bus);
        SOTWParticles.register(bus);

        // Register our config specification to the mod container
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent context) {
        context.enqueueWork(SOTWNetworkHandler::register);
    }
}
