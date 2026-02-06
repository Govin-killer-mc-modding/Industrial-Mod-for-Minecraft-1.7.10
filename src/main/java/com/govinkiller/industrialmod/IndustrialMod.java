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
    public static Item latex;
    public static Item rubber; // ДОБАВЬ ЭТУ СТРОКУ

    // Объявляем вкладку здесь, но инициализируем в preInit
    public static CreativeTabs tabIndustrial;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // 1. ИНИЦИАЛИЗАЦИЯ ВКЛАДКИ (Первым делом!)
        tabIndustrial = new CreativeTabs("industrialTab") {
            @Override
            public Item getTabIconItem() {
                // Безопасная проверка: если слитка еще нет, покажет палку
                return industrialIngot != null ? industrialIngot : Items.stick;
            }
            @Override
            public boolean hasSearchBar() {
                return true;
            }
        }.setBackgroundImageName("item_search.png");

        // 2. РЕГИСТРАЦИЯ БЛОКОВ
        ModBlocks.init();
        ModBlocks.register();

        // 3. РЕГИСТРАЦИЯ ОБЫЧНЫХ ОБЪЕКТОВ
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

        latex = new ItemLatex();
        GameRegistry.registerItem(latex, "latex");

        // 4. РЕГИСТРАЦИЯ КРАНИКА (В самом конце, так как он зависит от всего выше)
        treeTap = new ItemTreeTap();
        GameRegistry.registerItem(treeTap, "treeTap");

        rubber = new ItemRubber();
        GameRegistry.registerItem(rubber, "rubber");

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // РЕГИСТРАЦИЯ В СЛОВАРЕ РУД
        OreDictionary.registerOre("oreCopper", ModBlocks.oreCopper);
        OreDictionary.registerOre("oreTin", ModBlocks.oreTin);
        OreDictionary.registerOre("logWood", ModBlocks.rubberLog);
        OreDictionary.registerOre("treeLeaves", ModBlocks.rubberLeaves);
        OreDictionary.registerOre("treeSapling", ModBlocks.rubberSapling);
        OreDictionary.registerOre("ingotCopper", ingotCopper);
        OreDictionary.registerOre("ingotTin", ingotTin);
        OreDictionary.registerOre("plateCopper", plateCopper);
        OreDictionary.registerOre("plateTin", plateTin);
        OreDictionary.registerOre("itemRubber", rubber);


        // РЕЦЕПТЫ ВЫПЛАВКИ
        GameRegistry.addSmelting(industrialOre, new ItemStack(industrialIngot), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(ingotCopper), 0.5F);
        GameRegistry.addSmelting(ModBlocks.oreTin, new ItemStack(ingotTin), 0.5F);
        // Обжиг латекса дает резину. 0.1F — это опыт за переплавку.
        GameRegistry.addSmelting(latex, new ItemStack(rubber), 0.1F);

        // РЕЦЕПТЫ ВЕРСТАКА
        GameRegistry.addRecipe(new ItemStack(industrialBlock), "XXX", "XXX", "XXX", 'X', industrialIngot);
        GameRegistry.addShapelessRecipe(new ItemStack(industrialIngot, 9), industrialBlock);
        GameRegistry.addRecipe(new ItemStack(industrialHammer), "XXX", "XXX", " S ", 'X', industrialIngot, 'S', Items.stick);

        // БЕСФОРМЕННЫЙ КРАФТ КРАНИКА (5 палок)
        GameRegistry.addShapelessRecipe(new ItemStack(treeTap), Items.stick, Items.stick, Items.stick, Items.stick, Items.stick);

        GameRegistry.addShapelessRecipe(new ItemStack(industrialPlate), industrialIngot, new ItemStack(industrialHammer, 1, 32767));
        GameRegistry.addShapelessRecipe(new ItemStack(plateCopper), ingotCopper, new ItemStack(industrialHammer, 1, 32767));
        GameRegistry.addShapelessRecipe(new ItemStack(plateTin), ingotTin, new ItemStack(industrialHammer, 1, 32767));

        GameRegistry.registerWorldGenerator(new IndustrialWorldGen(), 1);
    }
}
