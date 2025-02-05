package strutsoftheworld.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.RotWeedBlock;
import strutsoftheworld.block.SOTWBlocks;
import strutsoftheworld.tag.SOTWTags;

public class SOTWConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCATTERED_TRASH_PILE = registerKey("scattered_trash_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROT_WEED_PATCH = registerKey("rot_weed_patch");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, SCATTERED_TRASH_PILE, Feature.RANDOM_PATCH,
            FeatureUtils.simpleRandomPatchConfiguration(
                96, PlacementUtils.filtered(
                    SOTWFeatures.TRASH_PILE.get(),
                    NoneFeatureConfiguration.NONE,
                    BlockPredicate.matchesTag(SOTWTags.Blocks.TRASH_PILE_REPLACEABLE)
                )
            )
        );

        register(context, ROT_WEED_PATCH, Feature.RANDOM_PATCH,
            FeatureUtils.simpleRandomPatchConfiguration(
                96, PlacementUtils.filtered(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(new RandomizedIntStateProvider(
                        BlockStateProvider.simple(SOTWBlocks.ROT_WEED.get().defaultBlockState()),
                        RotWeedBlock.AGE,
                        UniformInt.of(0, RotWeedBlock.MAX_AGE)
                    )),
                    BlockPredicate.matchesTag(SOTWTags.Blocks.ROT_WEED_REPLACEABLE)
                )
            ));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
        BootstrapContext<ConfiguredFeature<?, ?>> context,
        ResourceKey<ConfiguredFeature<?, ?>> key,
        F feature,
        FC configuration
    ) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
