package com.govinkiller.industrialmod;

import com.govinkiller.industrialmod.blocks.BlockIndustrialBlock;
import com.govinkiller.industrialmod.blocks.BlockIndustrialOre;
import com.govinkiller.industrialmod.items.ItemIndustrialIngot;
import com.govinkiller.industrialmod.items.ItemIndustrialHammer; // НОВОЕ
import com.govinkiller.industrialmod.items.ItemIndustrialPlate;  // НОВОЕ
import com.govinkiller.industrialmod.world.IndustrialWorldGen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs; // ВАЖНО: Импорт для вкладок
import net.minecraft.item.Item; // ВАЖНО: Импорт для предметов
import net.minecraft.item.ItemStack;

@Mod(modid = "industrialmod", name = "Industrial Mod", version = "1.0")
public class IndustrialMod {

    public static BlockIndustrialOre industrialOre;
    public static BlockIndustrialBlock industrialBlock;
    public static ItemIndustrialIngot industrialIngot;
    public static Item industrialHammer; // НОВОЕ
    public static Item industrialPlate;  // НОВОЕ
    // СОЗДАНИЕ ВКЛАДКИ
    public static CreativeTabs tabIndustrial = new CreativeTabs("industrialTab") {
        @Override
        public Item getTabIconItem() {
            return industrialIngot; // Иконка вкладки - ваш слиток
        }
    };

    @EventHandler
    public void init(FMLInitializationEvent event) {

        // 1. Руда
        industrialOre = new BlockIndustrialOre();
        GameRegistry.registerBlock(industrialOre, "industrialOre");

        // 2. Блок из слитков
        industrialBlock = new BlockIndustrialBlock();
        GameRegistry.registerBlock(industrialBlock, "industrialBlock");

        // 3. Слиток
        industrialIngot = new ItemIndustrialIngot();
        GameRegistry.registerItem(industrialIngot, "industrialIngot");

        // 4. Молот
        industrialHammer = new ItemIndustrialHammer(); // НОВОЕ
        GameRegistry.registerItem(industrialHammer, "industrialHammer");

        // 3. Пластина
        industrialPlate = new ItemIndustrialPlate();   // НОВОЕ
        GameRegistry.registerItem(industrialPlate, "industrialPlate");

        /* ========== РЕЦЕПТЫ ========== */
        GameRegistry.addSmelting(industrialOre, new ItemStack(industrialIngot), 0.7F);

        GameRegistry.addRecipe(new ItemStack(industrialBlock),
                "XXX", "XXX", "XXX",
                'X', industrialIngot);

        GameRegistry.addShapelessRecipe(new ItemStack(industrialIngot, 9),
                industrialBlock);

        // НОВЫЙ РЕЦЕПТ: Молот (6 слитков и 1 палка)
        GameRegistry.addRecipe(new ItemStack(industrialHammer),
                "XXX",
                "XXX",
                " S ",
                'X', industrialIngot,
                'S', net.minecraft.init.Items.stick);

        // НОВЫЙ РЕЦЕПТ: Пластина (Слиток + Молот)
        // 32767 — это флаг, чтобы принимался молот с любой прочностью
        GameRegistry.addShapelessRecipe(new ItemStack(industrialPlate),
                industrialIngot, new ItemStack(industrialHammer, 1, 32767));

        /* ========== ГЕНЕРАЦИЯ В МИРЕ ========== */
        GameRegistry.registerWorldGenerator(new IndustrialWorldGen(), 1);
    }
}
