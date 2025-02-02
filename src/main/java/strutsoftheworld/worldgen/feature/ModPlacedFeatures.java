package strutsoftheworld.worldgen.feature;

import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SCATTERED_TRASH_PILE = registerKey("scattered_trash_pile");
    public static final ResourceKey<PlacedFeature> ROT_WEED_PATCH = registerKey("rot_weed_patch");

    public static void boostrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, SCATTERED_TRASH_PILE,
                configuredFeature.getOrThrow(ModConfiguredFeatures.SCATTERED_TRASH_PILE),
                List.of(
                        CountPlacement.of(32),
                        InSquarePlacement.spread(),
                        PlacementUtils.FULL_RANGE,
                        EnvironmentScanPlacement.scanningFor(
                                Direction.DOWN,
                                BlockPredicate.wouldSurvive(ModBlocks.TRASH_PILE.get().defaultBlockState(), new Vec3i(0, 1, 0)),
                                32
                        ),
                        BiomeFilter.biome()
                )
        );

        register(context, ROT_WEED_PATCH,
                configuredFeature.getOrThrow(ModConfiguredFeatures.ROT_WEED_PATCH),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                ));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }

    private static void register(
            BootstrapContext<PlacedFeature> context,
            ResourceKey<PlacedFeature> key,
            Holder<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> placement
    ) {
        context.register(key, new PlacedFeature(feature, List.copyOf(placement)));
    }
}
