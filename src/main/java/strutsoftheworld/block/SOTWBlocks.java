package strutsoftheworld.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.item.SOTWItems;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class SOTWBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        ForgeRegistries.BLOCKS,
        StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<Block> TRASH_BLOCK = registerBlockAndItem("trash_block",
        () -> new SustainsRotWeedBlock(
            ofId("trash_block")
                .mapColor(MapColor.COLOR_BROWN)
                .strength(1.5f)
                .sound(SoundType.METAL)
        )
    );

    public static final RegistryObject<TrashPileBlock> TRASH_PILE = registerBlockAndItem("trash_pile",
        () -> new TrashPileBlock(
            ofId("trash_pile")
                .mapColor(waterloggedMapColor(MapColor.COLOR_BROWN))
                .replaceable()
                .noCollission()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.METAL)
                .offsetType(BlockBehaviour.OffsetType.XZ)
        )
    );

    public static final RegistryObject<RotWeedBlock> ROT_WEED = registerBlock("rot_weed",
        () -> new RotWeedBlock(
            ofId("rot_weed")
                .mapColor(MapColor.PLANT)
                .replaceable()
                .noCollission()
                .instabreak()
                .noOcclusion()
                .ignitedByLava()
                .lightLevel(state -> state.getValue(RotWeedBlock.AGE) == RotWeedBlock.MAX_AGE ? 7 : 0)
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.GRASS)
                .offsetType(BlockBehaviour.OffsetType.XYZ)
        ));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block) {
        var blockRegObj = registerBlock(name, block);
        SOTWItems.registerBlockItem(name, blockRegObj);
        return blockRegObj;
    }

    private static Function<BlockState, MapColor> waterloggedMapColor(MapColor color) {
        return state -> state.getValue(BlockStateProperties.WATERLOGGED) ? MapColor.WATER : color;
    }

    private static BlockBehaviour.Properties ofId(String name) {
        return BlockBehaviour.Properties.of().setId(BLOCKS.key(name));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
