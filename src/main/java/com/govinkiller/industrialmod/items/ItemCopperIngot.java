package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.item.Item;

public class ItemCopperIngot extends Item {
    public ItemCopperIngot() {
        this.setUnlocalizedName("ingotCopper"); // Внутреннее имя
        this.setTextureName("industrialmod:ingotCopper"); // Текстура: assets/industrialmod/textures/items/ingotCopper.png
        this.setCreativeTab(IndustrialMod.tabIndustrial); // Твоя вкладка
    }
}
