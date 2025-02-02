package strutsoftheworld.datagen.lang;

import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.ModBlocks;
import strutsoftheworld.item.ModCreativeModeTabs;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class ModEnUSLangProvider extends LanguageProvider {
    public ModEnUSLangProvider(PackOutput out, String locale) {
        super(out, StrutsOfTheWorldMod.MOD_ID, locale);
    }

    private void addBlockAndItem(Supplier<? extends Block> block, String name) {
        addBlock(block, name);
        addItem(block.get()::asItem, name);
    }

    @Override
    protected void addTranslations() {
        // Blocks
        addBlockAndItem(ModBlocks.TRASH_BLOCK, "Trash Block");
        addBlockAndItem(ModBlocks.TRASH_PILE, "Trash Pile");
        addBlockAndItem(ModBlocks.ROT_WEED, "Rot Weed");
        addBlockAndItem(ModBlocks.GLOWING_ROT_WEED, "Glowing Rot Weed");

        // Items
        /// TODO: Add item translation entries

        // Creative tabs
        addCreativeTab(ModCreativeModeTabs.BLOCKS_CREATIVE_TAB, "Struts of the World: Blocks");
        addCreativeTab(ModCreativeModeTabs.ITEMS_CREATIVE_TAB, "Struts of the World: Items");
    }

    private void addCreativeTab(Supplier<CreativeModeTab> tab, String name) {
        var content = tab.get().getDisplayName().getContents();
        if (!(content instanceof TranslatableContents translatable)) {
            throw new IllegalArgumentException( "Creative tab " + tab + " does not have a translatable name");
        }

        add(translatable.getKey(), name);
    }
}
