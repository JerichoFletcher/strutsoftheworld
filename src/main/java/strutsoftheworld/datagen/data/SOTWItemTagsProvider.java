package strutsoftheworld.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.item.SOTWItems;
import strutsoftheworld.tag.SOTWTags;

import java.util.concurrent.CompletableFuture;

public class SOTWItemTagsProvider extends ItemTagsProvider {
    public SOTWItemTagsProvider(
        PackOutput out,
        CompletableFuture<HolderLookup.Provider> lookupProv,
        CompletableFuture<TagLookup<Block>> blockTags,
        @Nullable ExistingFileHelper efh
    ) {
        super(out, lookupProv, blockTags, StrutsOfTheWorldMod.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider prov) {
        tag(Tags.Items.CROPS).add(SOTWItems.ROT_WEED_BUDS.get());
        tag(SOTWTags.Items.CROPS_ROT_WEED).add(SOTWItems.ROT_WEED_BUDS.get());
    }
}
