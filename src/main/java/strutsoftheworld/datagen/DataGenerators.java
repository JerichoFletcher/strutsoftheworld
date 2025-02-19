package strutsoftheworld.datagen;

import net.minecraft.DetectedVersion;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.datagen.assets.SOTWModelProvider;
import strutsoftheworld.datagen.assets.SOTWParticleDescriptionProvider;
import strutsoftheworld.datagen.assets.SOTWSoundDefinitionsProvider;
import strutsoftheworld.datagen.data.*;
import strutsoftheworld.datagen.lang.SOTWEnUSLangProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var out = gen.getPackOutput();
        var efh = event.getExistingFileHelper();
        var lookupProv = event.getLookupProvider();

        // Register client-only providers
        gen.addProvider(event.includeClient(), new SOTWModelProvider(out));
        gen.addProvider(event.includeClient(), new SOTWParticleDescriptionProvider(out, efh));

        registerTranslations(event.includeClient(), gen, out);

        // Register server providers
        gen.addProvider(event.includeServer(), new SOTWRecipeProvider.Runner(out, lookupProv));
        gen.addProvider(event.includeServer(), new LootTableProvider(out, Collections.emptySet(),
            List.of(
                new LootTableProvider.SubProviderEntry(SOTWBlockLootTableProvider::new, LootContextParamSets.BLOCK)
            ), lookupProv)
        );

        var blockTagProv = new SOTWBlockTagProvider(out, lookupProv, efh);
        gen.addProvider(event.includeServer(), blockTagProv);
        gen.addProvider(event.includeServer(), new SOTWItemTagsProvider(out, lookupProv, blockTagProv.contentsGetter(), efh));

        gen.addProvider(event.includeServer(), new SOTWDatapackEntriesProvider(out, lookupProv));
        gen.addProvider(event.includeServer(), new SOTWSoundDefinitionsProvider(out, efh));

        // Register pack metadata generator
        gen.addProvider(event.includeServer(), new PackMetadataGenerator(out)
            .add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal(String.format("Resources for %s", StrutsOfTheWorldMod.MOD_NAME)),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                Optional.empty()
            ))
        );
    }

    private static void registerTranslations(boolean run, DataGenerator gen, PackOutput out) {
        gen.addProvider(run, new SOTWEnUSLangProvider(out));
    }
}
