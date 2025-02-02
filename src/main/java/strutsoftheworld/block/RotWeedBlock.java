package strutsoftheworld.block;

import strutsoftheworld.tag.ModTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RotWeedBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<RotWeedBlock> CODEC = simpleCodec(RotWeedBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    @Override
    public MapCodec<RotWeedBlock> codec() {
        return CODEC;
    }

    public RotWeedBlock(Properties blockProperties) {
        super(blockProperties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModTags.Blocks.ROT_WEED_PLACEABLE_ON);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        if (state.getBlock() == this) {
            return level.getBlockState(belowPos).canSustainPlant(level, pos, Direction.UP, this);
        }
        return mayPlaceOn(state, level, belowPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.is(ModBlocks.ROT_WEED.get());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlock(pos, ModBlocks.GLOWING_ROT_WEED.get().defaultBlockState(), Block.UPDATE_CLIENTS);
    }
}
