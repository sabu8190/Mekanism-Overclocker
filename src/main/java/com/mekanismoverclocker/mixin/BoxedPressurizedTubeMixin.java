package com.mekanismoverclocker.mixin;

import com.mekanismoverclocker.core.MekanismOverclockerConfig;
import mekanism.common.content.network.transmitter.BoxedPressurizedTube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = BoxedPressurizedTube.class, remap = false)
public abstract class BoxedPressurizedTubeMixin {

    @Shadow
    public abstract void pullFromAcceptors();

    @Unique
    private boolean mekanismOverclocker$isPulling = false;

    @Inject(method = "pullFromAcceptors", at = @At("HEAD"))
    private void onPullFromAcceptorsHead(CallbackInfo ci) {
        if (mekanismOverclocker$isPulling || !MekanismOverclockerConfig.ENABLE_CHEMICAL_OVERCLOCK.get()) {
            return;
        }

        int burst = MekanismOverclockerConfig.CHEMICAL_BURST_PER_TICK.get();
        if (burst > 1) {
            mekanismOverclocker$isPulling = true;
            try {
                for (int i = 1; i < burst; i++) {
                    pullFromAcceptors();
                }
            } finally {
                mekanismOverclocker$isPulling = false;
            }
        }
    }
}
