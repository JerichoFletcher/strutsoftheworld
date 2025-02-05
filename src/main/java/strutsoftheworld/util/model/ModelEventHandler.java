package strutsoftheworld.util.model;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.SOTWBlocks;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void modifyBakedModels(ModelEvent.ModifyBakingResult event) {
        var models = event.getResults().blockStateModels();

        overrideWithRenderType(SOTWBlocks.ROT_WEED.getId(), models, RenderType.cutout());
    }

    private static void overrideWithRenderType(
        ResourceLocation targetBaseId,
        Map<ModelResourceLocation, BakedModel> modelMap,
        RenderType... overrideTypes
    ) {
        overrideWithRenderType(targetBaseId, modelMap, ChunkRenderTypeSet.of(overrideTypes));
    }

    private static void overrideWithRenderType(
        ResourceLocation targetBaseId,
        Map<ModelResourceLocation, BakedModel> modelMap,
        ChunkRenderTypeSet overrideTypeSet
    ) {
        final AtomicInteger count = new AtomicInteger();
        modelMap.keySet().stream()
            .filter(ml -> pathBeginsWith(ml.id(), targetBaseId))
            .forEach(ml -> {
                modelMap.compute(ml, (k, baseModel) -> new OverrideRenderTypeSetBakedModel(baseModel, overrideTypeSet));
                LOGGER.debug("Applied override render type for model {}", ml);
                count.getAndIncrement();
            });
        if (count.get() == 0) {
            throw new IllegalArgumentException("No models associated with " + targetBaseId);
        }
        LOGGER.debug("Finished applying overrides for {} models associated with {}", count, targetBaseId);
    }

    private static boolean pathBeginsWith(ResourceLocation loc, ResourceLocation pathBase) {
        return loc.getNamespace().equals(pathBase.getNamespace())
            && loc.getPath().startsWith(pathBase.getPath());
    }
}
