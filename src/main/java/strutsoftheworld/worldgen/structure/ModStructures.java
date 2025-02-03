package strutsoftheworld.worldgen.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import strutsoftheworld.StrutsOfTheWorldMod;

public class ModStructures {
    /// TODO: Add structure keys

    public static void bootstrap(BootstrapContext<Structure> context) {
        /// TODO: Register structures using defined structure template pools
    }

    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
