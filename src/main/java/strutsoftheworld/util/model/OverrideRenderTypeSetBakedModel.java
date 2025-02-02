package strutsoftheworld.util.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**@summary
 * Used for overriding what {@link ChunkRenderTypeSet}s are used for a base {@link BakedModel}.
 */
public class OverrideRenderTypeSetBakedModel implements BakedModel {
    private final BakedModel base;
    private final ChunkRenderTypeSet overrideRenderTypeSet;

    public OverrideRenderTypeSetBakedModel(BakedModel base, ChunkRenderTypeSet overrideRenderTypeSet) {
        this.base = base;
        this.overrideRenderTypeSet = overrideRenderTypeSet;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, RandomSource pRandom) {
        return base.getQuads(pState, pDirection, pRandom);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return base.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return base.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return base.usesBlockLight();
    }

    @SuppressWarnings("deprecation")
    @Override
    public TextureAtlasSprite getParticleIcon() {
        return base.getParticleIcon();
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return overrideRenderTypeSet;
    }
}
