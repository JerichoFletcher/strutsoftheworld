package strutsoftheworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrashPileBlock extends WaterloggedTransparentBlock {
    private static final VoxelShape SHAPE = Block.box(0f, 0f, 0f, 16f, 4f, 16f);

    public TrashPileBlock(Properties blockProperties) {
        super(blockProperties);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        return belowState.isFaceSturdy(level, belowPos, Direction.UP);
    }

    @Override
    protected boolean canBeReplaced(BlockState pState, Fluid pFluid) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess tickAccess,
            BlockPos pos,
            Direction dir,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource rand
    ) {
        if (!canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, tickAccess, pos, dir, neighborPos, neighborState, rand);
    }
}
