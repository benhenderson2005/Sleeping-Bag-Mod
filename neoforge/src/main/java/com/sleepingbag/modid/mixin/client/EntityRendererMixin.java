package com.sleepingbag.modid.mixin.client;

import com.sleepingbag.modid.client.SleepingBagRenderStateAccess;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    @Inject(method = "getRenderOffset", at = @At("RETURN"), cancellable = true)
    private void sleepingBag$lowerSleepingPlayers(EntityRenderState state, CallbackInfoReturnable<Vec3> cir) {
        if (!(state instanceof SleepingBagRenderStateAccess access)) {
            return;
        }

        if (!(state instanceof LivingEntityRenderState livingState)) {
            return;
        }

        if (!access.sleepingBag$isOnSleepingBag() || livingState.pose != Pose.SLEEPING) {
            return;
        }

        Vec3 original = cir.getReturnValue();
        cir.setReturnValue(original.add(0.0D, 0.0D / 16.0D, 0.0D));
    }
}
