package strutsoftheworld.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import strutsoftheworld.Globals;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.sound.SOTWMusics;
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
            ).specialEffects(BiomeSpecialEffectsBuilder.create(
                    Globals.STRUTS_FLOOR_FOG_COLOR,
                    Globals.STRUTS_FLOOR_WATER_COLOR,
                    Globals.STRUTS_FLOOR_WATER_FOG_COLOR,
                    Globals.STRUTS_FLOOR_SKY_COLOR)
                // .ambientLoopSound(SOTWSoundEvents.AMBIENT_STRUTS_FLOOR_LOOP.getHolder().orElseThrow())
                .backgroundMusic(SOTWMusics.BIOME_STRUTS_FLOOR)
                .build()
            ).build()
        );
    }

    public static ResourceKey<Biome> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
