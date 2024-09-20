package dev.cxntered.rotation.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Shadow
    protected Minecraft mc;

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onChatMessage(String message, CallbackInfo ci) {
        if (message.startsWith("/rotation ")) {
            String[] args = message.substring(10).split(" ");
            float yaw, pitch;
            try {
                yaw = Float.parseFloat(args[0]);
                pitch = Float.parseFloat(args[1]);
            } catch (Exception e) {
                this.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Invalid arguments."));
                ci.cancel();
                return;
            }
            mc.thePlayer.rotationYaw = yaw;
            mc.thePlayer.rotationPitch = pitch;
            ci.cancel();
        }
    }
}