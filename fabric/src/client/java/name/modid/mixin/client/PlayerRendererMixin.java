package name.modid.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import name.modid.ModBlocks;
import name.modid.client.SleepingBagRenderStateAccess;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;

@Mixin(AvatarRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void sleepingBag$markSleepingBagSleepers(Avatar player, AvatarRenderState state, float tickDelta, CallbackInfo ci) {
        boolean onSleepingBag = player.isSleeping()
                && player.getSleepingPos()
                        .map(pos -> ModBlocks.isSleepingBag(player.level().getBlockState(pos)))
                        .orElse(false);

        ((SleepingBagRenderStateAccess) state).sleepingBag$setOnSleepingBag(onSleepingBag);
    }
}