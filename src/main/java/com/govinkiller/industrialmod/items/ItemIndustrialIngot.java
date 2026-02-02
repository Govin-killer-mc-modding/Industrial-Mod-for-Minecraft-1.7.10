package com.govinkiller.industrialmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import com.govinkiller.industrialmod.IndustrialMod;
public class ItemIndustrialIngot extends Item {

    public ItemIndustrialIngot() {
        this.setUnlocalizedName("industrialIngot");
        this.setTextureName("industrialmod:industrial_ingot");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
    }
}