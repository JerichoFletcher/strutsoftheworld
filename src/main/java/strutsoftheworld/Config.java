package strutsoftheworld;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    /// TODO: Define config spec entries

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // TODO: Add public config fields

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        /// TODO: Set public config fields to values from config event

    }
}
