package strutsoftheworld;

import strutsoftheworld.block.ModBlocks;
import strutsoftheworld.item.ModCreativeModeTabs;
import strutsoftheworld.item.ModItems;
import strutsoftheworld.particle.ModParticles;
import strutsoftheworld.sound.ModSoundEvents;
import strutsoftheworld.worldgen.feature.ModFeatures;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(StrutsOfTheWorldMod.MOD_ID)
public class StrutsOfTheWorldMod {
    public static final String MOD_ID = "strutsoftheworld";
    public static final String MOD_NAME = "Struts of the World";
    public static final Logger LOGGER = LogUtils.getLogger();

    public StrutsOfTheWorldMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        // Register mod contents
        ModCreativeModeTabs.register(bus);
        ModItems.register(bus);
        ModBlocks.register(bus);
        ModFeatures.register(bus);

        ModSoundEvents.register(bus);
        ModParticles.register(bus);

        // Register our config specification to the mod container
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
