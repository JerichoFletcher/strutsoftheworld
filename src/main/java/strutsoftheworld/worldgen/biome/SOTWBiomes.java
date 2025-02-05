package strutsoftheworld.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.registries.ForgeRegistries;
import strutsoftheworld.Globals;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.sound.SOTWSoundEvents;
import strutsoftheworld.worldgen.feature.SOTWPlacedFeatures;

public class SOTWBiomes {
    public static final ResourceKey<Biome> STRUTS_FLOOR = registerKey("struts_floor");

    public static void bootstrap(BootstrapContext<Biome> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(STRUTS_FLOOR, new Biome.BiomeBuilder()
            .temperature(0.6f)
            .downfall(0f)
            .hasPrecipitation(true)
            .mobSpawnSettings(MobSpawnSettings.EMPTY)
            .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SOTWPlacedFeatures.ROT_WEED_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SOTWPlacedFeatures.SCATTERED_TRASH_PILE)
                .build()
            ).specialEffects(new BiomeSpecialEffects.Builder()
                .skyColor(Globals.STRUTS_FLOOR_SKY_COLOR.toRGBInt())
                .fogColor(Globals.STRUTS_FLOOR_FOG_COLOR.toRGBInt())
                .waterColor(Globals.STRUTS_FLOOR_WATER_COLOR.toRGBInt())
                .waterFogColor(Globals.STRUTS_FLOOR_WATER_FOG_COLOR.toRGBInt())
                .ambientLoopSound(SOTWSoundEvents.AMBIENT_STRUTS_FLOOR_LOOP.getHolder().orElseThrow())
                .backgroundMusic(Musics.createGameMusic(SOTWSoundEvents.MUSIC_BIOME_STRUTS_FLOOR.getHolder().orElseThrow()))
                .build()
            ).build()
        );
    }

    public static ResourceKey<Biome> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
