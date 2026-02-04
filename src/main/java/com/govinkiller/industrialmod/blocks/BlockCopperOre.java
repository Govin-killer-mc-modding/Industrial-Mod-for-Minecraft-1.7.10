package com.govinkiller.industrialmod.blocks;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCopperOre extends Block {
    public BlockCopperOre() {
        super(Material.rock);
        this.setBlockName("oreCopper");
        this.setBlockTextureName("industrialmod:oreCopper"); // Текстура: assets/industrialmod/textures/blocks/oreCopper.png
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setHardness(3.0F); // Крепость
        this.setResistance(5.0F); // Сопротивление взрыву
        this.setHarvestLevel("pickaxe", 2);                // нужна железная кирка
        this.setStepSound(soundTypeStone);
        }
}