package strutsoftheworld.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput out) {
        super(registries, out);
    }

    @Override
    protected void buildRecipes() {
        /// TODO: Add recipes
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput out, CompletableFuture<HolderLookup.Provider> registries) {
            super(out, registries);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries, @NotNull RecipeOutput out) {
            return new ModRecipeProvider(registries, out);
        }

        @Override
        public @NotNull String getName() {
            return "Recipes";
        }
    }
}
