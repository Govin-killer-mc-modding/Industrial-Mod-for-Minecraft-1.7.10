package com.govinkiller.industrialmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.creativetab.CreativeTabs;
public class BlockIndustrialOre extends Block {

    public BlockIndustrialOre() {
        super(Material.rock);                              // материал «камень»
        this.setBlockName("industrialOre");                // внутренний ID
        this.setBlockTextureName("industrialmod:industrial_ore"); // путь к текстуре
        this.setCreativeTab(IndustrialMod.tabIndustrial);        // вкладка в креативе
        this.setHardness(3.0F);                            // скорость добычи
        this.setResistance(5.0F);                          // взрывоустойчивость
        this.setHarvestLevel("pickaxe", 2);                // нужна железная кирка
    }
}