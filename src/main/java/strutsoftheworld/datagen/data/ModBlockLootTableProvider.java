package strutsoftheworld.datagen.data;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import strutsoftheworld.block.ModBlocks;
import strutsoftheworld.block.RotWeedBlock;
import strutsoftheworld.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider prov) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), prov);
    }

    private void addRotWeedDrops() {
        var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        add(ModBlocks.ROT_WEED.get(), b -> LootTable.lootTable()
            .withPool(
                applyExplosionDecay(
                    ModBlocks.ROT_WEED.get(),
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(
                            LootItem.lootTableItem(ModItems.ROT_WEED_BUDS.get())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b)
                                    .setProperties(
                                        StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(RotWeedBlock.AGE, RotWeedBlock.MAX_AGE)
                                    )
                                )
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                    enchantments.getOrThrow(Enchantments.FORTUNE),
                                    0.5f, 3))
                        )
                )
            )
        );
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.TRASH_BLOCK.get());
        dropWhenSilkTouch(ModBlocks.TRASH_PILE.get());
        addRotWeedDrops();
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void dropWhenSheared(Block block) {
        add(block, createShearsOnlyDrop(block));
    }
}
