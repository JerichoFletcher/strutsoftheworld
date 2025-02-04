package strutsoftheworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluids;
import strutsoftheworld.Globals;

public class WasteRaindropParticle extends FaceCameraXZAxisParticle {
    private static final float QUAD_SCALE = 0.1f;
    private static final int LIFETIME = 120;
    private static final int FADE_IN_AGE = 10;
    private static final int FADE_OUT_AGE = 110;

    public WasteRaindropParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy - 1.5f, dz);
        lifetime = LIFETIME;
        quadSize = 0f;
        gravity = 0.06f;

        var color = Globals.STRUTS_FLOOR_WATER_COLOR;
        setColor(color.r(), color.g(), color.b());
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (age++ >= lifetime) {
            remove();
            return;
        }

        if (age <= FADE_IN_AGE) {
            quadSize = QUAD_SCALE * age / FADE_IN_AGE;
        }
        if (age > FADE_OUT_AGE) {
            quadSize = QUAD_SCALE * (LIFETIME - age) / (LIFETIME - FADE_OUT_AGE);
        }

        yd -= gravity;
        move(xd, yd, zd);

        if (onGround) {
            remove();
            level.addParticle(ModParticles.WASTE_RAINDROP_SPLASH.get(), x, y, z, 0, 0, 0);
            return;
        }

        xd *= friction;
        yd *= friction;
        zd *= friction;
        var pos = BlockPos.containing(x, y, z);
        var fluid = level.getFluidState(pos);

        if (fluid.is(Fluids.WATER) && y < (double) ((float) pos.getY() + fluid.getHeight(this.level, pos))) {
            remove();
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            var particle = new WasteRaindropParticle(level, x, y, z, dx, dy, dz);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
