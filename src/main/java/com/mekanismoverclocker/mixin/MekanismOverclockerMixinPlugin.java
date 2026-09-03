package com.mekanismoverclocker.mixin;

import net.minecraftforge.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MekanismOverclockerMixinPlugin implements IMixinConfigPlugin {

    private boolean isMekanismOptimizerPresent = false;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            isMekanismOptimizerPresent = FMLLoader.getLoadingModList().getModFileById("mekanism_optimizer") != null;
        } catch (Throwable t) {
            isMekanismOptimizerPresent = false;
        }
        if (isMekanismOptimizerPresent) {
            System.out.println("[MekanismOverclocker] Detected Mekanism Optimizer! Disabling built-in optimizer mixins to prevent duplicates.");
        } else {
            System.out.println("[MekanismOverclocker] Running standalone mode with built-in acceleration cache.");
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // If Mekanism Optimizer is present, skip all fallback optimizer mixins (only apply Overclock mixins)
        if (isMekanismOptimizerPresent && mixinClassName.contains("com.mekanismoverclocker.mixin.opt.")) {
            return false;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
