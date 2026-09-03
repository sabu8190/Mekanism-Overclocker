package com.mekanismoverclocker.core;

import net.minecraftforge.common.ForgeConfigSpec;

public final class MekanismOverclockerConfig {
    public static final ForgeConfigSpec SPEC;

    // --- ITEM OVERCLOCK ---
    public static final ForgeConfigSpec.BooleanValue ENABLE_ITEM_OVERCLOCK;
    public static final ForgeConfigSpec.IntValue ITEM_BURST_PER_TICK;
    public static final ForgeConfigSpec.IntValue ITEM_EJECT_TICK_DELAY;

    // --- FLUID OVERCLOCK ---
    public static final ForgeConfigSpec.BooleanValue ENABLE_FLUID_OVERCLOCK;
    public static final ForgeConfigSpec.IntValue FLUID_BURST_PER_TICK;

    // --- CHEMICAL (GAS) OVERCLOCK ---
    public static final ForgeConfigSpec.BooleanValue ENABLE_CHEMICAL_OVERCLOCK;
    public static final ForgeConfigSpec.IntValue CHEMICAL_BURST_PER_TICK;

    // --- ENERGY OVERCLOCK ---
    public static final ForgeConfigSpec.BooleanValue ENABLE_ENERGY_OVERCLOCK;
    public static final ForgeConfigSpec.IntValue ENERGY_BURST_PER_TICK;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Mekanism Overclocker Settings").push("Overclocking Tuning");

        ENABLE_ITEM_OVERCLOCK = builder
                .comment("Enable overclocked burst transfer and extraction for ITEMS.")
                .translation("mekanism_overclocker.config.enableItemOverclock")
                .define("enableItemOverclock", true);

        ITEM_BURST_PER_TICK = builder
                .comment("Maximum item stacks to insert/extract/eject in a single tick (1 to 512).")
                .translation("mekanism_overclocker.config.itemBurstPerTick")
                .defineInRange("itemBurstPerTick", 64, 1, 512);

        ITEM_EJECT_TICK_DELAY = builder
                .comment("Tick delay between item auto-ejection cycles (0 = eject every tick).")
                .translation("mekanism_overclocker.config.itemEjectTickDelay")
                .defineInRange("itemEjectTickDelay", 0, 0, 20);

        ENABLE_FLUID_OVERCLOCK = builder
                .comment("Enable overclocked burst transfer and extraction for FLUIDS.")
                .translation("mekanism_overclocker.config.enableFluidOverclock")
                .define("enableFluidOverclock", true);

        FLUID_BURST_PER_TICK = builder
                .comment("Maximum fluid transfer iterations per tick (1 to 512).")
                .translation("mekanism_overclocker.config.fluidBurstPerTick")
                .defineInRange("fluidBurstPerTick", 64, 1, 512);

        ENABLE_CHEMICAL_OVERCLOCK = builder
                .comment("Enable overclocked burst transfer and extraction for CHEMICALS/GASES.")
                .translation("mekanism_overclocker.config.enableChemicalOverclock")
                .define("enableChemicalOverclock", true);

        CHEMICAL_BURST_PER_TICK = builder
                .comment("Maximum chemical/gas transfer iterations per tick (1 to 512).")
                .translation("mekanism_overclocker.config.chemicalBurstPerTick")
                .defineInRange("chemicalBurstPerTick", 64, 1, 512);

        ENABLE_ENERGY_OVERCLOCK = builder
                .comment("Enable overclocked burst transfer and extraction for ENERGY.")
                .translation("mekanism_overclocker.config.enableEnergyOverclock")
                .define("enableEnergyOverclock", true);

        ENERGY_BURST_PER_TICK = builder
                .comment("Maximum energy transfer iterations per tick (1 to 512).")
                .translation("mekanism_overclocker.config.energyBurstPerTick")
                .defineInRange("energyBurstPerTick", 64, 1, 512);

        builder.pop();
        SPEC = builder.build();
    }

    private MekanismOverclockerConfig() {
    }
}
