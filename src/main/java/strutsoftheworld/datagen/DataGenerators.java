package strutsoftheworld.datagen;

import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.datagen.assets.ModModelProvider;
import strutsoftheworld.datagen.assets.ModParticleDescriptionProvider;
import strutsoftheworld.datagen.assets.ModSoundDefinitionsProvider;
import strutsoftheworld.datagen.data.*;
import strutsoftheworld.datagen.lang.ModEnUSLangProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        ExistingFileHelper efh = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProv = event.getLookupProvider();

        // Register client-only providers
        gen.addProvider(event.includeClient(), new ModModelProvider(out));
        gen.addProvider(event.includeClient(), new ModParticleDescriptionProvider(out, efh));

        // Register server providers
        gen.addProvider(event.includeServer(), new ModRecipeProvider.Runner(out, lookupProv));
        gen.addProvider(event.includeServer(), new LootTableProvider(out, Collections.emptySet(),
            List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
            ), lookupProv)
        );

        var blockTagProv = new ModBlockTagProvider(out, lookupProv, efh);
        gen.addProvider(event.includeServer(), blockTagProv);
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(out, lookupProv, blockTagProv.contentsGetter(), efh));

        gen.addProvider(event.includeServer(), new ModDatapackEntries(out, lookupProv));
        gen.addProvider(event.includeServer(), new ModSoundDefinitionsProvider(out, efh));

        // Register pack metadata generator
        gen.addProvider(event.includeServer(), new PackMetadataGenerator(out)
            .add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal(String.format("Resources for %s", StrutsOfTheWorldMod.MOD_NAME)),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                Optional.empty()
            ))
        );
    }

    @SubscribeEvent
    public static void gatherTranslations(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();

        // Register locale translation provides
        gen.addProvider(event.includeClient(), new ModEnUSLangProvider(out, "en_us"));
    }
}
