package strutsoftheworld.util.model;

import com.jcraft.jorbis.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.registries.RegistryObject;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.ModBlocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelEventHandler {
    @SubscribeEvent
    public static void modifyBakedModels(ModelEvent.ModifyBakingResult event) {
        var models = event.getResults().blockStateModels();

        overrideWithRenderType(ModBlocks.ROT_WEED.getId(), RenderType.cutout(), models);
        overrideWithRenderType(ModBlocks.GLOWING_ROT_WEED.getId(), RenderType.cutout(), models);
    }

    private static void overrideWithRenderType(
            ResourceLocation blockId,
            RenderType overrideType,
            Map<ModelResourceLocation, BakedModel> modelMap
    ) {
        overrideWithRenderType(blockId, ChunkRenderTypeSet.of(overrideType), modelMap);
    }

    private static void overrideWithRenderType(
            ResourceLocation blockId,
            ChunkRenderTypeSet overrideTypeSet,
            Map<ModelResourceLocation, BakedModel> modelMap
    ) {
        var modelLoc = modelMap.keySet().stream()
                .filter(ml -> ml.id().equals(blockId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No model associated with " + blockId));

        modelMap.compute(modelLoc, (k, baseModel) -> new OverrideRenderTypeSetBakedModel(baseModel, overrideTypeSet));
    }
}
