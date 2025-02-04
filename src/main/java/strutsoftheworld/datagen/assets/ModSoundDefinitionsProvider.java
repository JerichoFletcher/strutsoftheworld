package strutsoftheworld.datagen.assets;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.sound.ModSoundEvents;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public ModSoundDefinitionsProvider(PackOutput out, ExistingFileHelper efh) {
        super(out, StrutsOfTheWorldMod.MOD_ID, efh);
    }

    private SoundDefinition.Sound modSound(String path) {
        return sound(ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, path));
    }

    @Override
    public void registerSounds() {
        add(ModSoundEvents.AMBIENT_STRUTS_FLOOR_LOOP, definition()
            .with(modSound("ambient/struts_floor_loop").volume(0.75f).stream())
        );
        add(ModSoundEvents.MUSIC_BIOME_STRUTS_FLOOR, definition()
            .with(modSound("music/game/struts/rot_weed").volume(0.5f).stream())
        );
    }
}
