package strutsoftheworld.item;

import strutsoftheworld.StrutsOfTheWorldMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS,
            StrutsOfTheWorldMod.MOD_ID
    );

    /// TODO: Add items

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
