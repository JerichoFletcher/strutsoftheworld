package strutsoftheworld.worldgen.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import strutsoftheworld.StrutsOfTheWorldMod;

public class SOTWStructureTemplatePools {
    /// TODO: Add template pool keys

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        /// TODO: Register template pools
    }

    public static ResourceKey<StructureTemplatePool> registerKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
