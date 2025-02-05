package strutsoftheworld.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import strutsoftheworld.Globals;
import strutsoftheworld.item.SOTWItems;
import strutsoftheworld.tag.SOTWTags;

public class RotWeedBlock extends CropBlock implements BonemealableBlock {
    public static final MapCodec<RotWeedBlock> CODEC = simpleCodec(RotWeedBlock::new);
    public static final int MAX_AGE = BlockStateProperties.MAX_AGE_3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    @Override
    public MapCodec<RotWeedBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public RotWeedBlock(Properties blockProperties) {
        super(blockProperties);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) < Globals.ROT_WEED_GROWTH_MAX_SKY_LIGHT) {
            int age = getAge(state);
            if (age < getMaxAge()) {
                float f = getRotWeedGrowthSpeed(level, pos);
                if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25f / f) + 1) == 0)) {
                    level.setBlock(pos, getStateForAge(age + 1), Block.UPDATE_CLIENTS);
                    ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level pLevel) {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return SOTWItems.ROT_WEED_BUDS.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(SOTWTags.Blocks.ROT_WEED_PLACEABLE_ON);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        if (state.getBlock() == this) {
            return level.getBlockState(belowPos).canSustainPlant(level, pos, Direction.UP, this);
        }
        return mayPlaceOn(state, level, belowPos);
    }

    protected static float getRotWeedGrowthSpeed(BlockAndTintGetter level, BlockPos pos) {
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return Mth.clamp(
            1f - Mth.inverseLerp(skyLight, 0f, Globals.ROT_WEED_GROWTH_MAX_SKY_LIGHT),
            0f, 1f
        );
    }
}
