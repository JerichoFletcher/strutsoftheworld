package strutsoftheworld.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import strutsoftheworld.capability.StrutsWeatherCapability;
import strutsoftheworld.dimension.SOTWDimensions;

import java.util.Optional;

public class StrutsAmbientSoundHandler implements AmbientSoundHandler {
    private final LocalPlayer player;
    private final SoundManager soundManager;

    private Optional<DynamicVolumeSoundInstance> mainAmbientSound = Optional.empty();

    public StrutsAmbientSoundHandler(LocalPlayer player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {
        if (mainAmbientSound.isEmpty() && player.level().dimension().location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())) {
            var mainAmbient = new DynamicVolumeSoundInstance(SOTWSoundEvents.AMBIENT_STRUTS_FLOOR_LOOP.get());
            soundManager.play(mainAmbient);
            mainAmbientSound = Optional.of(mainAmbient);
        }
    }

    public static class DynamicVolumeSoundInstance extends AbstractTickableSoundInstance {
        public DynamicVolumeSoundInstance(SoundEvent soundEvent) {
            super(soundEvent, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
            looping = true;
            delay = 0;
            volume = StrutsWeatherCapability.ClientData.getRainStrength();
            relative = true;
        }

        @Override
        public void tick() {
            volume = StrutsWeatherCapability.ClientData.getRainStrength();
        }
    }
}
