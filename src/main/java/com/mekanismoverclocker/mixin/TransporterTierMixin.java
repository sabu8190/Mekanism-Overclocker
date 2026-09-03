package com.mekanismoverclocker.mixin;

import com.mekanismoverclocker.core.MekanismOverclockerConfig;
import mekanism.common.tier.TransporterTier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = TransporterTier.class, remap = false)
public abstract class TransporterTierMixin {

    @Inject(method = "getSpeed", at = @At("HEAD"), cancellable = true)
    private void onGetSpeed(CallbackInfoReturnable<Integer> cir) {
        if (MekanismOverclockerConfig.ENABLE_ITEM_OVERCLOCK.get()) {
            cir.setReturnValue(100); // Instant 100% progress per tick (light-speed transport)
        }
    }

    @Inject(method = "getPullAmount", at = @At("HEAD"), cancellable = true)
    private void onGetPullAmount(CallbackInfoReturnable<Integer> cir) {
        if (MekanismOverclockerConfig.ENABLE_ITEM_OVERCLOCK.get()) {
            cir.setReturnValue(64); // Full 64 items (1 stack) per pull regardless of tier
        }
    }
}
