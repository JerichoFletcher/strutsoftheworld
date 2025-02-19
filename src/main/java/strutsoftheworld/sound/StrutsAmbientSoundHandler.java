package strutsoftheworld.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.sounds.SoundManager;
import strutsoftheworld.capability.StrutsWeatherCapability;
import strutsoftheworld.dimension.SOTWDimensions;

import javax.annotation.Nullable;

public class StrutsAmbientSoundHandler implements AmbientSoundHandler {
    private final LocalPlayer player;
    private final SoundManager soundManager;

    @Nullable
    private DynamicVolumeSoundInstance mainAmbientSound;

    public StrutsAmbientSoundHandler(LocalPlayer player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {
        if (player.level().dimension().location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())) {
            if (mainAmbientSound == null) {
                mainAmbientSound = new DynamicVolumeSoundInstance(
                    SOTWSoundEvents.AMBIENT_STRUTS_FLOOR_LOOP.get(),
                    StrutsWeatherCapability.ClientData::getRainStrength
                );
            }

            if (!soundManager.isActive(mainAmbientSound)) {
                soundManager.play(mainAmbientSound);
            }
        }
    }
}
