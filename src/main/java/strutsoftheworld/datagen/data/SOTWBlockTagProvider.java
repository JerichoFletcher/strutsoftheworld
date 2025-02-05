package strutsoftheworld.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.SOTWBlocks;
import strutsoftheworld.tag.SOTWTags;

import java.util.concurrent.CompletableFuture;

public class SOTWBlockTagProvider extends BlockTagsProvider {
    public SOTWBlockTagProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> lookupProv, @Nullable ExistingFileHelper efh) {
        super(out, lookupProv, StrutsOfTheWorldMod.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.Provider prov) {
        tag(SOTWTags.Blocks.TRASH_PILE_REPLACEABLE)
            .add(Blocks.AIR, Blocks.WATER, SOTWBlocks.ROT_WEED.get());
        tag(SOTWTags.Blocks.ROT_WEED_REPLACEABLE)
            .add(Blocks.AIR);
        tag(SOTWTags.Blocks.ROT_WEED_PLACEABLE_ON)
            .add(SOTWBlocks.TRASH_BLOCK.get());
    }
}
