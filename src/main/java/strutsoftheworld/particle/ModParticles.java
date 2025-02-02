package strutsoftheworld.particle;

import strutsoftheworld.StrutsOfTheWorldMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(
            ForgeRegistries.PARTICLE_TYPES,
            StrutsOfTheWorldMod.MOD_ID
    );

    public static final RegistryObject<SimpleParticleType> WASTE_RAINDROP = register("waste_raindrop", false);
    public static final RegistryObject<SimpleParticleType> WASTE_RAINDROP_SPLASH = register("waste_raindrop_splash", false);

    private static RegistryObject<SimpleParticleType> register(String name, boolean overrideLimit) {
        return PARTICLES.register(name, () -> new SimpleParticleType(overrideLimit));
    }

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(WASTE_RAINDROP.get(), WasteRaindropParticle.Provider::new);
        event.registerSpriteSet(WASTE_RAINDROP_SPLASH.get(), WasteRaindropSplashParticle.Provider::new);
    }
}
