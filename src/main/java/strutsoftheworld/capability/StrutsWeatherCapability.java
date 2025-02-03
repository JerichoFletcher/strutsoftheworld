package strutsoftheworld.capability;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import strutsoftheworld.Globals;
import strutsoftheworld.network.ModNetworkHandler;

public class StrutsWeatherCapability implements IStrutsWeatherCapability {
    private float rainStrength = 0.1f;
    private float rainStrengthDrift = 0.0f;

    @Override
    public float getMaxRainStrengthDrift() {
        return 0.001f;
    }

    @Override
    public float getMaxRainStrengthDriftChange() {
        return 0.0001f;
    }

    @Override
    public float getRainStrengthDriftChangeProbability() {
        return 0.02f;
    }

    @Override
    public float getRainStrength() {
        return rainStrength;
    }

    @Override
    public float getRainStrengthDrift() {
        return rainStrengthDrift;
    }

    @Override
    public void setRainStrength(float value) {
        rainStrength = Math.clamp(value, 0f, 1f);
    }

    @Override
    public void setRainStrengthDrift(float value) {
        this.rainStrengthDrift = Math.clamp(value, -getMaxRainStrengthDrift(), getMaxRainStrengthDrift());
    }

    @Override
    public void update() {
        float rainStrength = getRainStrength();
        setRainStrength(rainStrength + getRainStrengthDrift());
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {
        private static final LazyOptional<IStrutsWeatherCapability> inst = LazyOptional.of(StrutsWeatherCapability::new);
        private static final String NBT_KEY_RAIN_STRENGTH = "rain_strength";
        private static final String NBT_KEY_RAIN_STRENGTH_DRIFT = "rain_strength_drift";

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return ModCapabilities.STRUTS_WEATHER.orEmpty(cap, inst);
        }

        public void invalidate() {
            inst.invalidate();
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider registry) {
            var weather = inst.orElse(new StrutsWeatherCapability());
            return CompoundTag.builder()
                    .putFloat(NBT_KEY_RAIN_STRENGTH, weather.getRainStrength())
                    .putFloat(NBT_KEY_RAIN_STRENGTH_DRIFT, weather.getRainStrengthDrift())
                    .build();
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider registry, CompoundTag nbt) {
            var weather = inst.orElseThrow(() -> new IllegalArgumentException("Capability instance not initialized"));
            weather.setRainStrength(nbt.getFloat(NBT_KEY_RAIN_STRENGTH));
        }
    }

    public record Packet(
            float rainStrength,
            float rainStrengthDrift
    ) {
        public static Packet of(IStrutsWeatherCapability instance) {
            return new Packet(
                    instance.getRainStrength(),
                    instance.getRainStrengthDrift()
            );
        }

        public static SimpleChannel registerMessage(SimpleChannel channel) {
            return channel.messageBuilder(Packet.class, ModNetworkHandler.getAvailableMessageId())
                    .encoder((msg, buf) -> {
                        buf.writeFloat(msg.rainStrength());
                        buf.writeFloat(msg.rainStrengthDrift());
                    }).decoder(msg -> new Packet(
                            msg.readFloat(),
                            msg.readFloat()
                    )).consumer(Packet::clientHandle)
                    .add();
        }

        public static void clientHandle(Packet packet, CustomPayloadEvent.Context context) {
            if (context.isClientSide()) {
                context.enqueueWork(() -> {
                    ClientData.set(packet);
                });
            }
            context.setPacketHandled(true);
        }
    }

    public static class ClientData {
        private static final Logger LOGGER = LogUtils.getLogger();
        private static float rainStrength;
        private static float rainStrengthDrift;

        private static void set(Packet packet) {
            rainStrength = packet.rainStrength();
            rainStrengthDrift = packet.rainStrengthDrift();
        }

        public static float getRainStrength() {
            return rainStrength;
        }

        public static float getRainStrengthDrift() {
            return rainStrengthDrift;
        }

        public static void update() {
            rainStrength = Math.clamp(rainStrength + rainStrengthDrift, 0f, 1f);
        }

        public static float getRainParticleProbability() {
            return rainStrength * Globals.STRUTS_ADD_RAIN_PARTICLE_P_ON_MAX_STRENGTH;
        }
    }
}

