package com.sleepingbag.modid.mixin;

import com.sleepingbag.modid.ModBlocks;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTrigger.class)
public class SweetDreamsMixin {
    @Inject(method = "trigger", at = @At("HEAD"), cancellable = true)
    private void sleepingBag$skipSweetDreams(ServerPlayer player, CallbackInfo ci) {
        if ((Object) this != CriteriaTriggers.SLEPT_IN_BED) {
            return;
        }

        boolean sleepingOnSleepingBag = player.getSleepingPos()
                .map(pos -> ModBlocks.isSleepingBag(player.level().getBlockState(pos)))
                .orElse(false);

        if (sleepingOnSleepingBag) {
            ci.cancel();
        }
    }
}
