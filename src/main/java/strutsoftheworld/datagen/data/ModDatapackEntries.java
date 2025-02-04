package strutsoftheworld.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.dimension.ModDimensionTypes;
import strutsoftheworld.dimension.ModDimensions;
import strutsoftheworld.worldgen.biome.ModBiomes;
import strutsoftheworld.worldgen.feature.ModBiomeModifiers;
import strutsoftheworld.worldgen.feature.ModConfiguredFeatures;
import strutsoftheworld.worldgen.feature.ModPlacedFeatures;
import strutsoftheworld.worldgen.noisesettings.ModNoiseSettings;
import strutsoftheworld.worldgen.structure.ModStructureSets;
import strutsoftheworld.worldgen.structure.ModStructureTemplatePools;
import strutsoftheworld.worldgen.structure.ModStructures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.BIOME, ModBiomes::bootstrap)
        .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, ModPlacedFeatures::boostrap)
        .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
        .add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap)
        .add(Registries.LEVEL_STEM, ModDimensions::bootstrap)
        .add(Registries.DIMENSION_TYPE, ModDimensionTypes::bootstrap)
        .add(Registries.TEMPLATE_POOL, ModStructureTemplatePools::bootstrap)
        .add(Registries.STRUCTURE, ModStructures::bootstrap)
        .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrap);

    public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(StrutsOfTheWorldMod.MOD_ID));
    }
}
