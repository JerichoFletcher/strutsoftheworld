package strutsoftheworld.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.stats.StatsCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import strutsoftheworld.sound.StrutsAmbientSoundHandler;

import java.util.List;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends AbstractClientPlayer {
    public MixinLocalPlayer(ClientLevel level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Final
    @Shadow
    private List<AmbientSoundHandler> ambientSoundHandlers;

    @Inject(method = "<init>", at = @At(value = "RETURN", unsafe = true))
    private void onInit(
        Minecraft mc,
        ClientLevel level,
        ClientPacketListener connection,
        StatsCounter stats,
        ClientRecipeBook recipeBook,
        boolean wasShiftKeyDown,
        boolean wasSprinting,
        CallbackInfo ci
    ) {
        ambientSoundHandlers.add(new StrutsAmbientSoundHandler((LocalPlayer) (Object) this, mc.getSoundManager()));
    }
}
