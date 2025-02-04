package strutsoftheworld.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import strutsoftheworld.StrutsOfTheWorldMod;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(
        PackOutput out,
        CompletableFuture<HolderLookup.Provider> lookupProv,
        CompletableFuture<TagLookup<Block>> blockTags,
        @Nullable ExistingFileHelper efh
    ) {
        super(out, lookupProv, blockTags, StrutsOfTheWorldMod.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider prov) {

    }
}
