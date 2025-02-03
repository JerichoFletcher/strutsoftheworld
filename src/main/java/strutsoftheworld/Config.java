package strutsoftheworld;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    /// TODO: Define ConfigValue<T> instances

    static final ForgeConfigSpec SPEC = BUILDER.build();

    /// TODO: Define static properties

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        /// TODO: Load config values to static properties

        LOGGER.info("Loaded config values");
    }
}
