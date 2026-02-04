package com.govinkiller.industrialmod.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static Block oreCopper;
    public static Block oreTin;
    public static Block rubberLog;

    public static void init() {
        oreCopper = new BlockCopperOre();
        oreTin = new BlockTinOre();
        rubberLog = new BlockRubberLog();
    }

    public static void register() {
        GameRegistry.registerBlock(oreCopper, "oreCopper");
        GameRegistry.registerBlock(oreTin, "oreTin");
        GameRegistry.registerBlock(rubberLog, "rubberLog");
    }
}
