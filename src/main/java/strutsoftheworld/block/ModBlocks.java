package strutsoftheworld.block;

import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<Block> TRASH_BLOCK = registerBlock("trash_block",
            () -> new SustainsRotWeedBlock(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("trash_block"))
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(1.5f)
                    .sound(SoundType.METAL)
            )
    );

    public static final RegistryObject<TrashPileBlock> TRASH_PILE = registerBlock("trash_pile",
            () -> new TrashPileBlock(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("trash_pile"))
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
            () -> new RotWeedBlock(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("rot_weed"))
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
            ));

    public static final RegistryObject<RotWeedBlock> GLOWING_ROT_WEED = registerBlock("glowing_rot_weed",
            () -> new RotWeedBlock(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("glowing_rot_weed"))
                    .mapColor(MapColor.PLANT)
                    .replaceable()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .ignitedByLava()
                    .lightLevel(s -> 7)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)
            ));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blockRegObj = BLOCKS.register(name, block);
        registerBlockItem(name, blockRegObj);
        return blockRegObj;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> blockRegObj) {
        ModItems.ITEMS.register(name, () -> new BlockItem(blockRegObj.get(), new Item.Properties()
                .setId(ModItems.ITEMS.key(name))
        ));
    }

    private static Function<BlockState, MapColor> waterloggedMapColor(MapColor color) {
        return state -> state.getValue(BlockStateProperties.WATERLOGGED) ? MapColor.WATER : color;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int emissionValue) {
        return state -> state.getValue(BlockStateProperties.LIT) ? emissionValue : 0;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
