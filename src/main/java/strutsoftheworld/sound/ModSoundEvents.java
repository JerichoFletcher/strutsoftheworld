package strutsoftheworld.sound;

import strutsoftheworld.StrutsOfTheWorldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS,
            StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<SoundEvent> AMBIENT_STRUTS_FLOOR_LOOP = register("ambient.struts_floor_loop");
    public static final RegistryObject<SoundEvent> MUSIC_BIOME_STRUTS_FLOOR = register("music.struts_of_the_world.struts_floor");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name)
        ));
    }

    private static SoundType createSoundType(
            float volume, float pitch,
            Supplier<SoundEvent> breakSound,
            Supplier<SoundEvent> stepSound,
            Supplier<SoundEvent> placeSound,
            Supplier<SoundEvent> hitSound,
            Supplier<SoundEvent> fallSound
    ) {
        return new ForgeSoundType(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
