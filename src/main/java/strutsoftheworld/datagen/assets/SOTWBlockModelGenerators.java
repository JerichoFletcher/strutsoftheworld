package strutsoftheworld.datagen.assets;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import strutsoftheworld.block.RotWeedBlock;
import strutsoftheworld.block.SOTWBlocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SOTWBlockModelGenerators extends BlockModelGenerators {
    public SOTWBlockModelGenerators(
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
        Block block = SOTWBlocks.TRASH_PILE.get();

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

    private void createRotWeed() {
        RotWeedBlock block = SOTWBlocks.ROT_WEED.get();
        var texMapping = PlantType.NOT_TINTED.getTextureMapping(block);

        ResourceLocation modelLoc = PlantType.NOT_TINTED.getCross().create(block, texMapping, modelOutput);
        var glowingTexLoc = modelLoc.withPath(modelLoc.getPath() + "_glowing");

        ResourceLocation modelGlowingLoc = PlantType.NOT_TINTED.getCross().createWithSuffix(
            block, "_glowing",
            TextureMapping.cross(glowingTexLoc), modelOutput
        );

        blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(RotWeedBlock.AGE)
                    .generate(age -> age < RotWeedBlock.MAX_AGE
                        ? Variant.variant().with(VariantProperties.MODEL, modelLoc)
                        : Variant.variant().with(VariantProperties.MODEL, modelGlowingLoc)
                    )
                )
        );
    }

    @Override
    public void run() {
        createTrivialCubeWithItem(SOTWBlocks.TRASH_BLOCK.get());
        createTrashPile();
        createRotWeed();
    }
}
