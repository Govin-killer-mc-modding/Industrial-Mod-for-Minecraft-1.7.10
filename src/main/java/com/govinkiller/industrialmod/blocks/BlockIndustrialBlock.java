package com.govinkiller.industrialmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.creativetab.CreativeTabs;
public class BlockIndustrialBlock extends Block {

    public BlockIndustrialBlock() {
        super(Material.iron);
        this.setBlockName("industrialBlock");
        this.setBlockTextureName("industrialmod:industrial_block");
        this.setCreativeTab(IndustrialMod.tabIndustrial);

        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeMetal); // Чтобы он звенел как металл
        this.setHardness(5.0F);
        this.setResistance(10.0F);
    }
}
