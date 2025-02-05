package strutsoftheworld.datagen.assets;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import strutsoftheworld.item.SOTWItems;

import java.util.function.BiConsumer;

public class SOTWItemModelGenerators extends ItemModelGenerators {
    public SOTWItemModelGenerators(
        ItemModelOutput itemModelOut,
        BiConsumer<ResourceLocation, ModelInstance> modelOut
    ) {
        super(itemModelOut, modelOut);
    }

    @Override
    public void run() {
        generateFlatItem(SOTWItems.ROT_WEED_BUDS.get(), ModelTemplates.FLAT_ITEM);
    }
}
