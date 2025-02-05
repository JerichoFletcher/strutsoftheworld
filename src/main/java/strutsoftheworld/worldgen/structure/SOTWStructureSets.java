package strutsoftheworld.worldgen.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import strutsoftheworld.StrutsOfTheWorldMod;

public class SOTWStructureSets {
    /// TODO: Add structure set keys

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        /// TODO: Register structure sets using defined structures
    }

    public static ResourceKey<StructureSet> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
