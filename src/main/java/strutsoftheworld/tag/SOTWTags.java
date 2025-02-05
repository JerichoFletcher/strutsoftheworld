package strutsoftheworld.tag;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import strutsoftheworld.StrutsOfTheWorldMod;

public class SOTWTags {
    public static class Blocks {
        public static final TagKey<Block> TRASH_PILE_REPLACEABLE = createTag("trash_pile_replaceable");
        public static final TagKey<Block> ROT_WEED_REPLACEABLE = createTag("rot_weed_replaceable");
        public static final TagKey<Block> ROT_WEED_PLACEABLE_ON = createTag("rot_weed_placeable_on");

        private static TagKey<Block> createForgeTag(String name) {
            return BlockTags.create("forge", name);
        }

        private static TagKey<Block> createCTag(String name) {
            return BlockTags.create("c", name);
        }

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(StrutsOfTheWorldMod.MOD_ID, name);
        }
    }

    public static class Items {
        public static final TagKey<Item> CROPS_ROT_WEED = createCTag("crops/rot_weed");

        private static TagKey<Item> createForgeTag(String name) {
            return ItemTags.create("forge", name);
        }

        private static TagKey<Item> createCTag(String name) {
            return ItemTags.create("c", name);
        }

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(StrutsOfTheWorldMod.MOD_ID, name);
        }
    }
}
