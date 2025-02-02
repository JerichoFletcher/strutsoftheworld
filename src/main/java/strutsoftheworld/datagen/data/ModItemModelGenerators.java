package strutsoftheworld.datagen.data;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.resources.ResourceLocation;

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
        /// TODO: Add item models
    }
}
