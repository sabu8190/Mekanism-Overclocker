package com.mekanismoverclocker.mixin.opt;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.common.inventory.slot.BasicInventorySlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Pseudo
@Mixin(value = BasicInventorySlot.class, remap = false)
public abstract class OverclockBasicInventorySlotMixin {

    @Shadow
    protected ItemStack current;

    @Shadow
    @Final
    protected Predicate<@org.jetbrains.annotations.NotNull ItemStack> validator;

    @Shadow
    @Final
    protected BiPredicate<@org.jetbrains.annotations.NotNull ItemStack, @org.jetbrains.annotations.NotNull AutomationType> canInsert;

    @Shadow
    public abstract int getLimit(ItemStack stack);

    @Shadow
    public abstract int getCount();

    @Shadow
    public abstract boolean isEmpty();

    @Inject(method = "insertItem", at = @At("HEAD"), cancellable = true)
    private void onInsertItemFast(ItemStack stack, Action action, AutomationType automationType, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.isEmpty() || !validator.test(stack) || !canInsert.test(stack, automationType)) {
            cir.setReturnValue(stack);
            return;
        }

        int needed = getLimit(stack) - getCount();
        if (needed <= 0) {
            cir.setReturnValue(stack);
            return;
        }

        boolean sameType = isEmpty() || ItemHandlerHelper.canItemStacksStack(current, stack);
        if (sameType) {
            int toAdd = Math.min(stack.getCount(), needed);
            if (action.simulate()) {
                if (stack.getCount() == toAdd) {
                    cir.setReturnValue(ItemStack.EMPTY);
                    return;
                }
                if (toAdd == 0) {
                    cir.setReturnValue(stack);
                    return;
                }
            }
        }
    }
}
