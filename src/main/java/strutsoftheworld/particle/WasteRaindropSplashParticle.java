package strutsoftheworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ARGB;
import strutsoftheworld.Globals;

public class WasteRaindropSplashParticle extends WaterDropParticle {
    public WasteRaindropSplashParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z);
        gravity = 0.04f;

        if (dy == 0f && (dx != 0f || dz != 0f)) {
            xd = dx;
            yd = 0.1f;
            zd = dz;
        }

        var color = ARGB.vector3fFromRGB24(Globals.STRUTS_FLOOR_WATER_COLOR);
        setColor(color.x, color.y, color.z);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            var particle = new WasteRaindropSplashParticle(level, x, y, z, dx, dy, dz);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
