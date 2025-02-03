package strutsoftheworld.dimension;

import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.worldgen.biome.ModBiomes;
import strutsoftheworld.worldgen.noisesettings.ModNoiseSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

public class ModDimensions {
    public static final ResourceKey<LevelStem> STRUTS_OF_THE_WORLD = registerKey("struts_of_the_world");

    public static void bootstrap(BootstrapContext<LevelStem> context) {
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        var biomes = context.lookup(Registries.BIOME);
        var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        context.register(STRUTS_OF_THE_WORLD, new LevelStem(
                dimensionTypes.getOrThrow(ModDimensionTypes.STRUTS),
                new NoiseBasedChunkGenerator(
                        new FixedBiomeSource(biomes.getOrThrow(ModBiomes.STRUTS_FLOOR)),
                        noiseSettings.getOrThrow(ModNoiseSettings.STRUTS_NOISE)
                )
        ));
    }

    public static ResourceKey<LevelStem> registerKey(String name) {
        return ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
