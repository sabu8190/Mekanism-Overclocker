package com.mekanismoverclocker;

import com.mekanismoverclocker.core.MekanismOverclockerConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MekanismOverclocker.MODID)
public class MekanismOverclocker {
    public static final String MODID = "mekanism_overclocker";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public MekanismOverclocker() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MekanismOverclockerConfig.SPEC, "mekanism_overclocker.toml");
        LOGGER.info("Mekanism Overclocker initialized successfully.");
    }
}
