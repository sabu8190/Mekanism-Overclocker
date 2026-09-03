package com.mekanismoverclocker.mixin;

import com.mekanismoverclocker.core.MekanismOverclockerConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Pseudo
@Mixin(value = TileComponentEjector.class, remap = false)
public abstract class TileComponentEjectorMixin {

    @Shadow
    @Final
    private TileEntityMekanism tile;

    @Shadow
    private int tickDelay;

    @Shadow
    @Final
    private Map<TransmissionType, ConfigInfo> configInfo;

    @Shadow
    public abstract boolean isEjecting(ConfigInfo info, TransmissionType type);

    @Shadow
    protected abstract void eject(TransmissionType type, ConfigInfo info);

    @Shadow
    protected abstract void outputItems(ConfigInfo info);

    @Inject(method = "tickServer", at = @At("HEAD"))
    private void onTickServerHead(CallbackInfo ci) {
        if (tile == null) {
            return;
        }

        int configuredDelay = MekanismOverclockerConfig.ITEM_EJECT_TICK_DELAY.get();
        if (tickDelay > configuredDelay) {
            tickDelay = configuredDelay;
        }

        for (Map.Entry<TransmissionType, ConfigInfo> entry : configInfo.entrySet()) {
            TransmissionType type = entry.getKey();
            ConfigInfo info = entry.getValue();

            if (type == TransmissionType.ITEM || type == TransmissionType.HEAT || !isEjecting(info, type)) {
                continue;
            }

            int burst = 1;
            if (type == TransmissionType.FLUID && MekanismOverclockerConfig.ENABLE_FLUID_OVERCLOCK.get()) {
                burst = MekanismOverclockerConfig.FLUID_BURST_PER_TICK.get();
            } else if (type.isChemical() && MekanismOverclockerConfig.ENABLE_CHEMICAL_OVERCLOCK.get()) {
                burst = MekanismOverclockerConfig.CHEMICAL_BURST_PER_TICK.get();
            } else if (type == TransmissionType.ENERGY && MekanismOverclockerConfig.ENABLE_ENERGY_OVERCLOCK.get()) {
                burst = MekanismOverclockerConfig.ENERGY_BURST_PER_TICK.get();
            }

            if (burst > 1) {
                for (int i = 0; i < burst - 1; i++) {
                    eject(type, info);
                }
            }
        }
    }

    @Inject(method = "tickServer", at = @At("TAIL"))
    private void onTickServerTail(CallbackInfo ci) {
        if (tile == null || !MekanismOverclockerConfig.ENABLE_ITEM_OVERCLOCK.get()) {
            return;
        }

        int maxBurst = MekanismOverclockerConfig.ITEM_BURST_PER_TICK.get();
        if (maxBurst <= 1) {
            return;
        }

        ConfigInfo itemConfig = configInfo.get(TransmissionType.ITEM);
        if (itemConfig != null && isEjecting(itemConfig, TransmissionType.ITEM)) {
            for (int i = 1; i < maxBurst; i++) {
                outputItems(itemConfig);
            }
        }
    }

    @Inject(method = "outputItems", at = @At("TAIL"))
    private void onOutputItemsTail(ConfigInfo info, CallbackInfo ci) {
        this.tickDelay = MekanismOverclockerConfig.ITEM_EJECT_TICK_DELAY.get();
    }
}
