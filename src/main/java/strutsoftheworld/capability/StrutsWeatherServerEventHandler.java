package strutsoftheworld.capability;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.slf4j.Logger;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.dimension.SOTWDimensions;
import strutsoftheworld.network.SOTWNetworkHandler;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StrutsWeatherServerEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (!level.isClientSide()
            && level.dimension().location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())
        ) {
            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                LOGGER.info("Sending weather state to logged in player {}", player.getName().getString());
                StrutsWeatherCapability.Packet.send(weather, PacketDistributor.PLAYER.with((ServerPlayer) player));
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ResourceKey<Level> levelTo = event.getTo();

        if (player instanceof ServerPlayer serverPlayer
            && levelTo.location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())
        ) {
            var server = serverPlayer.getServer();
            if (server == null) return;

            var level = serverPlayer.getServer().getLevel(levelTo);
            if (level == null) return;

            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                LOGGER.info("Sending weather state to recently entered target dimension player {}", player.getName().getString());
                StrutsWeatherCapability.Packet.send(weather, PacketDistributor.PLAYER.with(serverPlayer));
            });
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.LevelTickEvent.Pre event) {
        Level level = event.level;
        RandomSource rand = level.getRandom();

        if (!level.isClientSide()
            && level.dimension().location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())
        ) {
            level.getCapability(SOTWCapabilities.STRUTS_WEATHER).ifPresent(weather -> {
                weather.update();

                if (rand.nextFloat() <= weather.getRainStrengthDriftChangeProbability()) {
                    float rainStrengthDriftChange = rand.triangle(0f, weather.getMaxRainStrengthDriftChange());
                    float newRainStrengthDrift = weather.getRainStrengthDrift() + rainStrengthDriftChange;
                    weather.setRainStrengthDrift(newRainStrengthDrift);

                    StrutsWeatherCapability.Packet.send(weather, PacketDistributor.DIMENSION.with(level.dimension()));
                }
            });
        }
    }
}
