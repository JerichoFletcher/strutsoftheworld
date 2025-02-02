package strutsoftheworld.util.model;

import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.block.ModBlocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelEventHandler {
    @SubscribeEvent
    public static void modifyBakedModels(ModelEvent.ModifyBakingResult event) {
        var models = event.getResults().blockStateModels();

        for (var modelLoc : models.keySet()) {
            if (ModBlocks.OVERRIDE_RENDER_TYPE_SET.containsKey(modelLoc.id())) {
                // Replace the entry with a wrapper, so we can inject custom render type sets to the baked model
                var baseModel = models.get(modelLoc);
                var overrideTypeSet = ModBlocks.OVERRIDE_RENDER_TYPE_SET.get(modelLoc.id());
                var overrideModel = new OverrideRenderTypeSetBakedModel(baseModel, overrideTypeSet);

                models.put(modelLoc, overrideModel);
            }
        }
    }
}
