package strutsoftheworld.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.dimension.SOTWDimensionTypes;
import strutsoftheworld.dimension.SOTWDimensions;
import strutsoftheworld.worldgen.biome.SOTWBiomes;
import strutsoftheworld.worldgen.feature.SOTWBiomeModifiers;
import strutsoftheworld.worldgen.feature.SOTWConfiguredFeatures;
import strutsoftheworld.worldgen.feature.SOTWPlacedFeatures;
import strutsoftheworld.worldgen.noisesettings.SOTWNoiseSettings;
import strutsoftheworld.worldgen.structure.SOTWStructureSets;
import strutsoftheworld.worldgen.structure.SOTWStructureTemplatePools;
import strutsoftheworld.worldgen.structure.SOTWStructures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SOTWDatapackEntriesProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.BIOME, SOTWBiomes::bootstrap)
        .add(Registries.CONFIGURED_FEATURE, SOTWConfiguredFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, SOTWPlacedFeatures::boostrap)
        .add(ForgeRegistries.Keys.BIOME_MODIFIERS, SOTWBiomeModifiers::bootstrap)
        .add(Registries.NOISE_SETTINGS, SOTWNoiseSettings::bootstrap)
        .add(Registries.LEVEL_STEM, SOTWDimensions::bootstrap)
        .add(Registries.DIMENSION_TYPE, SOTWDimensionTypes::bootstrap)
        .add(Registries.TEMPLATE_POOL, SOTWStructureTemplatePools::bootstrap)
        .add(Registries.STRUCTURE, SOTWStructures::bootstrap)
        .add(Registries.STRUCTURE_SET, SOTWStructureSets::bootstrap);

    public SOTWDatapackEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(StrutsOfTheWorldMod.MOD_ID));
    }
}
