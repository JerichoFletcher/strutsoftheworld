package strutsoftheworld.sound;

import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

public class SOTWMusics {
    public static final Music BIOME_STRUTS_FLOOR = createGameMusic(SOTWSoundEvents.MUSIC_BIOME_STRUTS_FLOOR);

    private static Music createGameMusic(RegistryObject<SoundEvent> soundEvent) {
        return createMusic(soundEvent, 12000, 24000, false);
    }

    private static Music createMusic(RegistryObject<SoundEvent> soundEvent, int minDelay, int maxDelay, boolean replaceCurrent) {
        return new Music(soundEvent.getHolder().orElseThrow(), minDelay, maxDelay, replaceCurrent);
    }
}
