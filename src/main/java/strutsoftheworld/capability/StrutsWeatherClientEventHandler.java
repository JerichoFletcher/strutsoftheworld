package strutsoftheworld.capability;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import strutsoftheworld.Globals;
import strutsoftheworld.StrutsOfTheWorldMod;
import strutsoftheworld.dimension.SOTWDimensions;
import strutsoftheworld.particle.SOTWParticles;

@Mod.EventBusSubscriber(modid = StrutsOfTheWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StrutsWeatherClientEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        LocalPlayer player = mc.player;

        if (level != null && player != null && !mc.isPaused()
            && level.dimension().location().equals(SOTWDimensions.STRUTS_OF_THE_WORLD.location())
        ) {
            // Update client-side weather data
            StrutsWeatherCapability.ClientData.update();

            // Simulate the ambient particle spawning section of ClientLevel.doAnimateTick
            int x = player.getBlockX(), y = player.getBlockY(), z = player.getBlockZ();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            RandomSource rand = RandomSource.create();

            for (int i = 0; i < 667; i++) {
                spawnAddAmbientParticle(level, x, y, z, 16, rand, pos);
                spawnAddAmbientParticle(level, x, y, z, 32, rand, pos);
            }
        }
    }

    private static void spawnAddAmbientParticle(ClientLevel level, int x, int y, int z, int range, RandomSource rand, BlockPos.MutableBlockPos pos) {
        if (rand.nextFloat() > StrutsWeatherCapability.ClientData.getRainParticleProbability()) return;

        int tX = x + rand.nextInt(range) - rand.nextInt(range);
        int tZ = z + rand.nextInt(range) - rand.nextInt(range);
        int tY = Math.max(y, level.getHeight(Heightmap.Types.WORLD_SURFACE, tX, tZ)) + Globals.STRUTS_RAIN_PARTICLE_DIST_FROM_GROUND;
        pos.set(tX, tY, tZ);

        BlockState state = level.getBlockState(pos);
        if (!level.isInsideBuildHeight(tY) || !state.isCollisionShapeFullBlock(level, pos)) {
            pos.setY(tY + rand.nextInt(range));
            level.addParticle(SOTWParticles.WASTE_RAINDROP.get(),
                (double) pos.getX() + rand.nextDouble(),
                (double) pos.getY() + rand.nextDouble(),
                (double) pos.getZ() + rand.nextDouble(),
                0f, 0f, 0f
            );
        }
    }
}
