package com.govinkiller.industrialmod.blocks;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTinOre extends Block {
    public BlockTinOre() {
        super(Material.rock);
        this.setBlockName("oreTin");
        this.setBlockTextureName("industrialmod:oreTin"); // Текстура: assets/industrialmod/textures/blocks/oreTin.png
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);                // нужна железная кирка
    }
}
