package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.item.Item;

public class ItemRubber extends Item {
    public ItemRubber() {
        super();
        this.setUnlocalizedName("rubber");
        this.setTextureName("industrialmod:rubber");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
    }
}