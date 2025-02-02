package strutsoftheworld.tag;

import strutsoftheworld.StrutsOfTheWorldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> TRASH_PILE_REPLACEABLE = createTag("trash_pile_replaceable");
        public static final TagKey<Block> ROT_WEED_REPLACEABLE = createTag("rot_weed_replaceable");
        public static final TagKey<Block> ROT_WEED_PLACEABLE_ON = createTag("rot_weed_placeable_on");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
        }
    }

    public static class Items {
        /// TODO: Add item tags

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
        }
    }
}
