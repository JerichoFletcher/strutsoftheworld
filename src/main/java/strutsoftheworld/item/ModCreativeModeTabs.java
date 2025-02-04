package strutsoftheworld.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<CreativeModeTab> BLOCKS_CREATIVE_TAB = CREATIVE_MODE_TABS.register("blocks_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.strutsoftheworld.blocks"))
            .icon(() -> new ItemStack(ModBlocks.TRASH_BLOCK.get().asItem()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModBlocks.TRASH_BLOCK.get());
                output.accept(ModBlocks.TRASH_PILE.get());
            })
            .build()
    );

    public static final RegistryObject<CreativeModeTab> ITEMS_CREATIVE_TAB = CREATIVE_MODE_TABS.register("items_tab",
        () -> CreativeModeTab.builder()
            .withTabsBefore(BLOCKS_CREATIVE_TAB.getId())
            .title(Component.translatable("itemGroup.strutsoftheworld.items"))
            .icon(() -> new ItemStack(ModBlocks.TRASH_BLOCK.get().asItem()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.ROT_WEED_BUDS.get());
            })
            .build()
    );

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
