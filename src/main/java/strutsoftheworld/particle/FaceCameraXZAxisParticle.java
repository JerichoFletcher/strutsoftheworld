package strutsoftheworld.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public abstract class FaceCameraXZAxisParticle extends TextureSheetParticle {
    protected FaceCameraXZAxisParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy, dz);
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        var rotation = new Quaternionf();
        var camPos = camera.getPosition();

        rotation.setAngleAxis(Mth.atan2(camPos.x() - x, camPos.z() - z), 0f, 1f, 0f);
        if (roll != 0f) {
            rotation.rotateZ(Mth.lerp(partialTicks, oRoll, roll));
        }
        renderRotatedQuad(buffer, camera, rotation, partialTicks);
    }
}
