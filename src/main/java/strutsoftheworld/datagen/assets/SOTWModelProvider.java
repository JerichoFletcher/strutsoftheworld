package strutsoftheworld.datagen.assets;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import strutsoftheworld.block.SOTWBlocks;
import strutsoftheworld.item.SOTWItems;

import java.util.stream.Stream;

public class SOTWModelProvider extends ModelProvider {
    public SOTWModelProvider(PackOutput out) {
        super(out);
    }

    @Override
    protected @NotNull BlockModelGenerators getBlockModelGenerators(
        @NotNull BlockStateGeneratorCollector blocks,
        @NotNull ItemInfoCollector items,
        @NotNull SimpleModelCollector models
    ) {
        return new SOTWBlockModelGenerators(blocks, items, models);
    }

    @Override
    protected @NotNull ItemModelGenerators getItemModelGenerators(
        @NotNull ItemInfoCollector items,
        @NotNull SimpleModelCollector models
    ) {
        return new SOTWItemModelGenerators(items, models);
    }

    @Override
    protected @NotNull Stream<Block> getKnownBlocks() {
        return SOTWBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get);
    }

    @Override
    protected @NotNull Stream<Item> getKnownItems() {
        return SOTWItems.ITEMS.getEntries().stream().map(RegistryObject::get);
    }
}
