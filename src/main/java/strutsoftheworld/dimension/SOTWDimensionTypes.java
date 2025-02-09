package strutsoftheworld.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.dimension.DimensionType;
import strutsoftheworld.StrutsOfTheWorldMod;

import java.util.OptionalLong;

public class SOTWDimensionTypes {
    public static final ResourceKey<DimensionType> STRUTS = registerKey("struts");

    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(STRUTS, new DimensionType(
            OptionalLong.of(0),
            false,
            false,
            false,
            false,
            1f,
            true,
            false,
            0,
            256,
            256,
            BlockTags.INFINIBURN_OVERWORLD,
            SOTWDimensionSpecialEffects.STRUTS_EFFECTS,
            0f,
            new DimensionType.MonsterSettings(
                false,
                false,
                ConstantInt.of(0),
                0
            )
        ));
    }

    public static ResourceKey<DimensionType> registerKey(String name) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
