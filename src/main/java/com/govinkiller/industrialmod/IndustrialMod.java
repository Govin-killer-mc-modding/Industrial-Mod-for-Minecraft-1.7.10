package com.govinkiller.industrialmod;

import com.govinkiller.industrialmod.blocks.BlockIndustrialBlock;
import com.govinkiller.industrialmod.blocks.BlockIndustrialOre;
import com.govinkiller.industrialmod.blocks.ModBlocks;
import com.govinkiller.industrialmod.items.*;
import com.govinkiller.industrialmod.world.IndustrialWorldGen;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "industrialmod", name = "Industrial Mod", version = "2.0")
public class IndustrialMod {

    // Объявление всех объектов
    public static BlockIndustrialOre industrialOre;
    public static BlockIndustrialBlock industrialBlock;
    public static Item industrialIngot;
    public static Item industrialHammer;
    public static Item industrialPlate;
    public static Item ingotCopper;
    public static Item ingotTin;
    public static Item plateCopper;
    public static Item plateTin;
    public static Item treeTap;

    // Твоя творческая вкладка
    public static CreativeTabs tabIndustrial = new CreativeTabs("industrialTab") {
        @Override
        public Item getTabIconItem() {
            return industrialIngot;
        }
        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // 1. Сначала регистрируем блоки из ModBlocks
        ModBlocks.init();
        ModBlocks.register();

        // 2. Создаем и регистрируем все предметы и блоки этого класса
        industrialOre = new BlockIndustrialOre();
        GameRegistry.registerBlock(industrialOre, "industrialOre");

        industrialBlock = new BlockIndustrialBlock();
        GameRegistry.registerBlock(industrialBlock, "industrialBlock");

        industrialIngot = new ItemIndustrialIngot();
        GameRegistry.registerItem(industrialIngot, "industrialIngot");

        industrialHammer = new ItemIndustrialHammer();
        GameRegistry.registerItem(industrialHammer, "industrialHammer");

        industrialPlate = new ItemIndustrialPlate();
        GameRegistry.registerItem(industrialPlate, "industrialPlate");

        ingotCopper = new ItemCopperIngot();
        GameRegistry.registerItem(ingotCopper, "ingotCopper");

        ingotTin = new ItemTinIngot();
        GameRegistry.registerItem(ingotTin, "ingotTin");

        plateCopper = new ItemCopperPlate();
        GameRegistry.registerItem(plateCopper, "plateCopper");

        plateTin = new ItemTinPlate();
        GameRegistry.registerItem(plateTin, "plateTin");

        // Регистрация краника через твой новый класс
        treeTap = new ItemTreeTap();
        GameRegistry.registerItem(treeTap, "treeTap");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // 3. Регистрация в словаре руд (OreDictionary)
        OreDictionary.registerOre("oreCopper", ModBlocks.oreCopper);
        OreDictionary.registerOre("oreTin", ModBlocks.oreTin);
        OreDictionary.registerOre("logWood", ModBlocks.rubberLog);
        OreDictionary.registerOre("treeLeaves", ModBlocks.rubberLeaves);
        OreDictionary.registerOre("treeSapling", ModBlocks.rubberSapling);
        OreDictionary.registerOre("ingotCopper", ingotCopper);
        OreDictionary.registerOre("ingotTin", ingotTin);
        OreDictionary.registerOre("plateCopper", plateCopper);
        OreDictionary.registerOre("plateTin", plateTin);

        // 4. Рецепты выплавки
        GameRegistry.addSmelting(industrialOre, new ItemStack(industrialIngot), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(ingotCopper), 0.5F);
        GameRegistry.addSmelting(ModBlocks.oreTin, new ItemStack(ingotTin), 0.5F);

        // 5. Верстачные рецепты
        GameRegistry.addRecipe(new ItemStack(industrialBlock), "XXX", "XXX", "XXX", 'X', industrialIngot);
        GameRegistry.addShapelessRecipe(new ItemStack(industrialIngot, 9), industrialBlock);

        // Молот
        GameRegistry.addRecipe(new ItemStack(industrialHammer), "XXX", "XXX", " S ", 'X', industrialIngot, 'S', Items.stick);

        // КРАНИК (проверь форму на верстаке: "носик" влево)
        // Бесформенный рецепт: просто 5 палок в сетке крафта
        GameRegistry.addShapelessRecipe(new ItemStack(treeTap),
                Items.stick, Items.stick, Items.stick, Items.stick, Items.stick
        );

        // Пластины
        GameRegistry.addShapelessRecipe(new ItemStack(industrialPlate), industrialIngot, new ItemStack(industrialHammer, 1, 32767));
        GameRegistry.addShapelessRecipe(new ItemStack(plateCopper), ingotCopper, new ItemStack(industrialHammer, 1, 32767));
        GameRegistry.addShapelessRecipe(new ItemStack(plateTin), ingotTin, new ItemStack(industrialHammer, 1, 32767));

        // 6. Генерация мира
        GameRegistry.registerWorldGenerator(new IndustrialWorldGen(), 1);
    }
}