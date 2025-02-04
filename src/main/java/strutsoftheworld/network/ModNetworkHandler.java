package strutsoftheworld.network;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import org.slf4j.Logger;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.capability.StrutsWeatherCapability;

import javax.annotation.Nullable;

public class ModNetworkHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final int PROTOCOL_VERSION = 1;
    private static int AVAILABLE_MESSAGE_ID = 0;

    public static final ResourceLocation CHANNEL_NAME = ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, "main");
    private static @Nullable SimpleChannel INSTANCE = null;

    public static void register() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Channel " + INSTANCE.getName() + " already initialized");
        }

        var channel = ChannelBuilder.named(CHANNEL_NAME)
            .networkProtocolVersion(PROTOCOL_VERSION)
            .acceptedVersions((s, v) -> v == PROTOCOL_VERSION)
            .simpleChannel();
        channel = StrutsWeatherCapability.Packet.registerMessage(channel);
        INSTANCE = channel.build();

        LOGGER.info("Registered channel {}", INSTANCE.getName());
    }

    public static SimpleChannel instance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Channel is not initialized");
        }
        return INSTANCE;
    }

    public static int getAvailableMessageId() {
        return AVAILABLE_MESSAGE_ID++;
    }
}
