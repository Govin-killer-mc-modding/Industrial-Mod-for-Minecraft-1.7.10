package com.govinkiller.industrialmod.blocks;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static Block oreCopper;
    public static Block oreTin;
    public static Block rubberLog;
    public static Block rubberLeaves;
    public static Block rubberSapling;
    // 1. Объяви переменную (вверху класса)
    public static Block coalGenerator;

    public static void init() {
        oreCopper = new BlockCopperOre();
        oreTin = new BlockTinOre();
        rubberLog = new BlockRubberLog()
                .setBlockName("rubberLog")
                .setCreativeTab(IndustrialMod.tabIndustrial);// Твоя вкладка
        // Листва (создадим класс следующим шагом)
        rubberLeaves = new BlockRubberLeaves()
                .setBlockName("rubberLeaves")
                .setCreativeTab(IndustrialMod.tabIndustrial);

        // Саженец (создадим класс позже)
        rubberSapling = new BlockRubberSapling()
                .setBlockName("rubberSapling")
                .setCreativeTab(IndustrialMod.tabIndustrial);
        coalGenerator = new BlockCoalGenerator();
    }

    public static void register() {
            GameRegistry.registerBlock(oreCopper, "oreCopper");
            GameRegistry.registerBlock(oreTin, "oreTin");

            // Возвращаем стандартную регистрацию - это уберет вылет
            GameRegistry.registerBlock(rubberLog, "rubberLog");

            GameRegistry.registerBlock(rubberLeaves, "rubberLeaves");
            GameRegistry.registerBlock(rubberSapling, "rubberSapling");
            GameRegistry.registerBlock(coalGenerator, "coalGenerator");
        }
    }
