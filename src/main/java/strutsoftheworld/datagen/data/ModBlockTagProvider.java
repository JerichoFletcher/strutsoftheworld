package strutsoftheworld.datagen.data;

import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.ModBlocks;
import strutsoftheworld.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> lookupProv, @Nullable ExistingFileHelper efh) {
        super(out, lookupProv, StrutsOfTheWorldMod.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.Provider prov) {
        tag(ModTags.Blocks.TRASH_PILE_REPLACEABLE)
                .add(Blocks.AIR, Blocks.WATER, ModBlocks.ROT_WEED.get(), ModBlocks.GLOWING_ROT_WEED.get());
        tag(ModTags.Blocks.ROT_WEED_REPLACEABLE)
                .add(Blocks.AIR);
        tag(ModTags.Blocks.ROT_WEED_PLACEABLE_ON)
                .add(ModBlocks.TRASH_BLOCK.get());
    }
}
