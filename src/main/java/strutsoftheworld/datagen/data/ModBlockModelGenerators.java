package strutsoftheworld.datagen.data;

import strutsoftheworld.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockStateGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModBlockModelGenerators extends BlockModelGenerators {
    public ModBlockModelGenerators(
            Consumer<BlockStateGenerator> blockStateOut,
            ItemModelOutput itemModelOut,
            BiConsumer<ResourceLocation, ModelInstance> modelOut
    ) {
        super(blockStateOut, itemModelOut, modelOut);
    }

    private void createTrivialCubeWithItem(Block block) {
        createTrivialCube(block);
        registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    private void createPlantWithItem(Block block, PlantType type) {
        createCrossBlock(block, type);
        registerSimpleItemModel(block, createFlatItemModelWithBlockTexture(block.asItem(), block));
    }

    private void createTrashPile() {
        Block block = ModBlocks.TRASH_PILE.get();

        ResourceLocation model01Loc = ModelLocationUtils.getModelLocation(block);
        ResourceLocation model02Loc = ModelLocationUtils.getModelLocation(block, "_02");
        ResourceLocation model03Loc = ModelLocationUtils.getModelLocation(block, "_03");

        blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(block,
                        Variant.variant().with(VariantProperties.MODEL, model01Loc),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model01Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model01Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model01Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                        Variant.variant().with(VariantProperties.MODEL, model02Loc),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model02Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model02Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model02Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270),
                        Variant.variant().with(VariantProperties.MODEL, model03Loc),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model03Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model03Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model03Loc)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                )
        );
        registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    @Override
    public void run() {
        createTrivialCubeWithItem(ModBlocks.TRASH_BLOCK.get());
        createTrashPile();
        createPlantWithItem(ModBlocks.ROT_WEED.get(), PlantType.NOT_TINTED);
        createPlantWithItem(ModBlocks.GLOWING_ROT_WEED.get(), PlantType.NOT_TINTED);
    }
}
