package strutsoftheworld.capability;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import strutsoftheworld.Globals;
import strutsoftheworld.network.SOTWNetworkHandler;

public class StrutsWeatherCapability {
    public static final float MAX_RAIN_STRENGTH_DRIFT = 0.0007f;
    public static final float MAX_RAIN_STRENGTH_DRIFT_CHANGE = 0.0001f;
    public static final float RAIN_STRENGTH_DRIFT_CHANGE_PROBABILITY = 0.02f;

    private float rainStrength = 0.1f;
    private float rainStrengthDrift = 0.0f;

    public float getMaxRainStrengthDrift() {
        return MAX_RAIN_STRENGTH_DRIFT;
    }

    public float getMaxRainStrengthDriftChange() {
        return MAX_RAIN_STRENGTH_DRIFT_CHANGE;
    }

    public float getRainStrengthDriftChangeProbability() {
        return RAIN_STRENGTH_DRIFT_CHANGE_PROBABILITY;
    }

    public float getRainStrength() {
        return rainStrength;
    }

    public float getRainStrengthDrift() {
        return rainStrengthDrift;
    }

    public void setRainStrength(float value) {
        rainStrength = Math.clamp(value, 0f, 1f);
    }

    public void setRainStrengthDrift(float value) {
        this.rainStrengthDrift = Math.clamp(value, -getMaxRainStrengthDrift(), getMaxRainStrengthDrift());
    }

    public void update() {
        float rainStrength = getRainStrength();
        setRainStrength(rainStrength + getRainStrengthDrift());
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {
        private static final LazyOptional<StrutsWeatherCapability> inst = LazyOptional.of(StrutsWeatherCapability::new);
        private static final String NBT_KEY_RAIN_STRENGTH = "rain_strength";
        private static final String NBT_KEY_RAIN_STRENGTH_DRIFT = "rain_strength_drift";

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return SOTWCapabilities.STRUTS_WEATHER.orEmpty(cap, inst);
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
        private Packet(StrutsWeatherCapability capInstance) {
            this(
                capInstance.getRainStrength(),
                capInstance.getRainStrengthDrift()
            );
        }

        private static void clientHandle(Packet packet, CustomPayloadEvent.Context context) {
            if (context.isClientSide()) {
                context.enqueueWork(() -> ClientData.set(packet));
            }
            context.setPacketHandled(true);
        }

        public static SimpleChannel registerMessage(SimpleChannel channel) {
            return channel.messageBuilder(Packet.class, SOTWNetworkHandler.getAvailableMessageId())
                .encoder((msg, buf) -> {
                    buf.writeFloat(msg.rainStrength());
                    buf.writeFloat(msg.rainStrengthDrift());
                }).decoder(msg -> new Packet(
                    msg.readFloat(),
                    msg.readFloat()
                )).consumer(Packet::clientHandle)
                .add();
        }

        public static void send(StrutsWeatherCapability capInstance, PacketDistributor.PacketTarget target) {
            SOTWNetworkHandler.instance().send(new Packet(capInstance), target);
        }
    }

    public static class ClientData {
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

