package com.mekanismoverclocker.mixin;

import com.mekanismoverclocker.core.MekanismOverclockerConfig;
import mekanism.common.content.network.transmitter.LogisticalTransporterBase;
import mekanism.common.content.transporter.TransporterStack;
import mekanism.common.lib.inventory.TransitRequest;
import mekanism.common.lib.transmitter.ConnectionType;
import mekanism.common.tier.TransporterTier;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Pseudo
@Mixin(value = LogisticalTransporterBase.class, remap = false)
public abstract class LogisticalTransporterBaseMixin {

    @Shadow
    public int delay;

    @Shadow
    public int delayCount;

    @Shadow
    public TransporterTier tier;

    @Inject(method = "onUpdateServer", at = @At("HEAD"))
    private void onUpdateServerHead(CallbackInfo ci) {
        if (!MekanismOverclockerConfig.ENABLE_ITEM_OVERCLOCK.get()) {
            return;
        }

        // Always eliminate delays
        this.delay = 0;
        this.delayCount = 0;

        LogisticalTransporterBase self = (LogisticalTransporterBase) (Object) this;
        Set<Direction> pullSides = self.getConnections(ConnectionType.PULL);
        if (pullSides != null && !pullSides.isEmpty()) {
            int maxBurst = MekanismOverclockerConfig.ITEM_BURST_PER_TICK.get();
            if (maxBurst < 1) maxBurst = 64;

            for (Direction side : pullSides) {
                BlockEntity tile = WorldUtils.getTileEntity(self.getTileWorld(), self.getTilePos().relative(side));
                if (tile != null) {
                    for (int i = 0; i < maxBurst; i++) {
                        // Extract a FULL 64-item stack (1 full st) per iteration regardless of tier
                        TransitRequest request = TransitRequest.anyItem(tile, side.getOpposite(), 64);
                        if (request.isEmpty()) {
                            break;
                        }
                        TransitRequest.TransitResponse response = self.insert(tile, request, self.getColor(), true, 0);
                        if (response.isEmpty()) {
                            break;
                        }
                        response.useAll();
                    }
                }
            }
        }
    }

    @Inject(method = "onUpdateServer", at = @At("TAIL"))
    private void onUpdateServerTail(CallbackInfo ci) {
        if (MekanismOverclockerConfig.ENABLE_ITEM_OVERCLOCK.get()) {
            this.delay = 0;
            this.delayCount = 0;
        }
    }
}
