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

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    static {
        final var clientSpec = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT = clientSpec.getLeft();
        CLIENT_SPEC = clientSpec.getRight();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            CLIENT.load();
        }
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            CLIENT.load();
        }
    }

    public static class ClientConfig {
        public float strutsRainDensity;

        private final ForgeConfigSpec.FloatValue strutsRainDensityCfg;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder
                .comment("Config for weather and special effects.")
                .push("effects");
            strutsRainDensityCfg = builder
                .comment("Controls the density of the Struts rain, affecting how many particles are spawned.")
                .defineInRange("strutsMaxRainParticleSpawnProbability", 0.67f, 0f, 1f);
            builder.pop();
        }

        public void load() {
            strutsRainDensity = strutsRainDensityCfg.get();
        }
    }
}
