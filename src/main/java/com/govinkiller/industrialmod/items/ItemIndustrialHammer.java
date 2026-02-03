package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class ItemIndustrialHammer extends Item {
    public ItemIndustrialHammer() {
        this.setUnlocalizedName("industrialHammer");
        this.setTextureName("industrialmod:industrial_hammer");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setMaxStackSize(1);
        this.setMaxDamage(200); // Хватит на 200 пластин
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true; // Предмет возвращается в сетку крафта
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = itemStack.copy();
        stack.setItemDamage(stack.getItemDamage() + 1); // Тратим прочность
        return stack;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
        return false; // Не дает молоту вылетать из сетки
    }
    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        // Проигрываем звук наковальни в момент крафта
        world.playSoundAtEntity(player, "random.anvil_use", 0.5F, 1.0F);
    }

}

