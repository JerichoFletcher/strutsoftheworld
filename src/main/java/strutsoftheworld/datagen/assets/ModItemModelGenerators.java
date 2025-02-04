package strutsoftheworld.datagen.assets;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import strutsoftheworld.item.ModItems;

import java.util.function.BiConsumer;

public class ModItemModelGenerators extends ItemModelGenerators {
    public ModItemModelGenerators(
        ItemModelOutput itemModelOut,
        BiConsumer<ResourceLocation, ModelInstance> modelOut
    ) {
        super(itemModelOut, modelOut);
    }

    @Override
    public void run() {
        generateFlatItem(ModItems.ROT_WEED_BUDS.get(), ModelTemplates.FLAT_ITEM);
    }
}
