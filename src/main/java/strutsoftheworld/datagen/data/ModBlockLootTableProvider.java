package strutsoftheworld.datagen.data;

import strutsoftheworld.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider prov) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), prov);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.TRASH_BLOCK.get());
        dropWhenSilkTouch(ModBlocks.TRASH_PILE.get());
        dropWhenSheared(ModBlocks.ROT_WEED.get());
        dropWhenSheared(ModBlocks.GLOWING_ROT_WEED.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void dropWhenSheared(Block block) {
        add(block, createShearsOnlyDrop(block));
    }
}
