package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.item.Item;

public class ItemIndustrialPlate extends Item {
    public ItemIndustrialPlate() {
        this.setUnlocalizedName("industrialPlate");
        this.setTextureName("industrialmod:industrial_plate");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
    }
}
