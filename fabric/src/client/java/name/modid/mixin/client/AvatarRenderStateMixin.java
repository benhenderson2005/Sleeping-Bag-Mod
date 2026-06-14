package name.modid.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import name.modid.client.SleepingBagRenderStateAccess;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements SleepingBagRenderStateAccess {
    @Unique
    private boolean sleepingBag$onSleepingBag;

    @Override
    public boolean sleepingBag$isOnSleepingBag() {
        return this.sleepingBag$onSleepingBag;
    }

    @Override
    public void sleepingBag$setOnSleepingBag(boolean value) {
        this.sleepingBag$onSleepingBag = value;
    }
}