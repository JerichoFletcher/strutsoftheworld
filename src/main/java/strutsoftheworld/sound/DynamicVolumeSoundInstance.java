package strutsoftheworld.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

import java.util.function.Supplier;

public class DynamicVolumeSoundInstance extends AbstractTickableSoundInstance {
    protected Supplier<Float> volumeSupplier;

    public DynamicVolumeSoundInstance(SoundEvent soundEvent, Supplier<Float> volumeSupplier) {
        super(soundEvent, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        looping = true;
        delay = 0;
        relative = true;

        this.volumeSupplier = volumeSupplier;
        volume = volumeSupplier.get();
    }

    @Override
    public void tick() {
        volume = volumeSupplier.get();
    }
}
