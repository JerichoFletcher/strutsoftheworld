package strutsoftheworld.datagen.assets;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.particle.ModParticles;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider {
    public ModParticleDescriptionProvider(PackOutput out, ExistingFileHelper efh) {
        super(out, efh);
    }

    @Override
    protected void addDescriptions() {
        sprite(ModParticles.WASTE_RAINDROP.get(), modTexture("raindrop"));
        spriteSet(ModParticles.WASTE_RAINDROP_SPLASH.get(), modTexture("splash"), 4, false);
    }

    private static ResourceLocation modTexture(String path) {
        return ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, path);
    }
}
