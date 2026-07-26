package com.thiago.capes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CapesMod.MODID, name = CapesMod.NAME, version = CapesMod.VERSION,
        clientSideOnly = true)
public class CapesMod {

    public static final String MODID = "customcapes";
    public static final String NAME = "Custom Capes";
    public static final String VERSION = "1.0.0";

    @Mod.Instance
    public static CapesMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapeManager.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CapeRenderer());
    }
}