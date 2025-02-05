package strutsoftheworld.worldgen.noisesettings;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.SOTWBlocks;

public class SOTWNoiseSettings {
    public static final ResourceKey<NoiseGeneratorSettings> STRUTS_NOISE = registerKey("struts_noise");

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        var densityFuncReg = context.lookup(Registries.DENSITY_FUNCTION);
        var noiseParamReg = context.lookup(Registries.NOISE);

        context.register(STRUTS_NOISE, new NoiseGeneratorSettings(
            NoiseSettings.create(0, 256, 1, 2),
            SOTWBlocks.TRASH_BLOCK.get().defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            SOTWNoiseRouterData.struts(densityFuncReg, noiseParamReg),
            SurfaceRules.ifTrue(
                SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
            ),
            new OverworldBiomeBuilder().spawnTarget(),
            48,
            true,
            false,
            false,
            false
        ));
    }

    public static ResourceKey<NoiseGeneratorSettings> registerKey(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(StrutsOfTheWorldMod.MOD_ID, name));
    }
}
