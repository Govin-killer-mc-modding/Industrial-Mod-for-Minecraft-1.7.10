package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.item.Item;

public class ItemLatex extends Item {
    public ItemLatex() {
        super();
        this.setUnlocalizedName("latex");
        this.setTextureName("industrialmod:latex"); // Текстура latex.png в папке items
        this.setCreativeTab(IndustrialMod.tabIndustrial);
    }
}