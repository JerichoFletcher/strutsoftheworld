package strutsoftheworld.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.SOTWBlocks;

import java.util.function.Supplier;

public class SOTWItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS,
        StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<BlockItem> ROT_WEED_BUDS = registerBlockItem("rot_weed_buds", SOTWBlocks.ROT_WEED);

    public static <T extends Block> RegistryObject<BlockItem> registerBlockItem(String name, Supplier<T> blockRegObj) {
        return ITEMS.register(name, () -> new BlockItem(blockRegObj.get(), new Item.Properties()
            .setId(ITEMS.key(name))
        ));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
