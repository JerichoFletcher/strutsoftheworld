package strutsoftheworld.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

public class SustainsRotWeedBlock extends Block {
    public SustainsRotWeedBlock(Properties blockProperties) {
        super(blockProperties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlant(world, pos.relative(facing)).getBlock() instanceof RotWeedBlock;
    }
}
