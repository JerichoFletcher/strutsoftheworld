package strutsoftheworld.worldgen.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import strutsoftheworld.StrutsOfTheWorldMod;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(
        ForgeRegistries.FEATURES,
        StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<TrashPileFeature> TRASH_PILE = FEATURES.register("trash_pile", TrashPileFeature::new);

    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
