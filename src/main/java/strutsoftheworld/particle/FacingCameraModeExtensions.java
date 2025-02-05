package strutsoftheworld.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class FacingCameraModeExtensions {
    private FacingCameraModeExtensions() {}

    public static void lookAtXZ(SingleQuadParticle particle, Quaternionf rot, Camera cam) {
        var parPos = particle.getPos();
        var camPos = cam.getPosition();
        rot.setAngleAxis(Mth.atan2(camPos.x() - parPos.x(), camPos.z() - parPos.z()), 0f, 1f, 0f);
    }
}
