package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.item.Item;

public class ItemTreeTap extends Item {
    public ItemTreeTap() {
        super();
        this.setUnlocalizedName("treeTap");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setMaxStackSize(1);
        this.setMaxDamage(32); // Краник будет тратить прочность (32 использования)
        // Указываем название текстуры, которое ты нарисовал
        this.setTextureName("industrialmod:treeTap");
    }
}