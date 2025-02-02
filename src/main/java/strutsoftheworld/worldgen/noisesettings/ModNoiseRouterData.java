package strutsoftheworld.worldgen.noisesettings;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoiseRouterData extends NoiseRouterData {
    public static NoiseRouter struts(
            HolderGetter<DensityFunction> densityFunctionReg,
            HolderGetter<NormalNoise.NoiseParameters> noiseParamReg
    ) {
        return overworld(densityFunctionReg, noiseParamReg, false, false);
    }
}
