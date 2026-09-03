package com.mekanismoverclocker.core;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import java.util.concurrent.ConcurrentHashMap;

public final class OverclockRecipeCache {
    private static final ConcurrentHashMap<Item, Object> CACHE = new ConcurrentHashMap<>(512);

    public static Object get(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        return CACHE.get(stack.getItem());
    }

    public static void put(ItemStack stack, Object recipe) {
        if (stack == null || stack.isEmpty() || recipe == null) return;
        CACHE.put(stack.getItem(), recipe);
    }

    public static void clear() {
        CACHE.clear();
    }
}
