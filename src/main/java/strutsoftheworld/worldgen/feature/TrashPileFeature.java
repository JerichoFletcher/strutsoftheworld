package strutsoftheworld.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import strutsoftheworld.block.ModBlocks;
import strutsoftheworld.block.TrashPileBlock;

public class TrashPileFeature extends Feature<NoneFeatureConfiguration> {
    public TrashPileFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean flag = false;

        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();

        BlockState state = ModBlocks.TRASH_PILE.get().defaultBlockState()
            .setValue(TrashPileBlock.WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
        if (state.canSurvive(level, pos)) {
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
            flag = true;
        }

        return flag;
    }
}
