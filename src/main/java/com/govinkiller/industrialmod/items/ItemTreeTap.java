package com.govinkiller.industrialmod.items;

import com.govinkiller.industrialmod.IndustrialMod;
import com.govinkiller.industrialmod.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTreeTap extends Item {
    public ItemTreeTap() {
        super();
        this.setUnlocalizedName("treeTap");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setMaxStackSize(1);
        this.setMaxDamage(32);
        this.setTextureName("industrialmod:treeTap");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (block == ModBlocks.rubberLog && meta == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);

            if (!world.isRemote) {
                int count = world.rand.nextInt(3) + 1;
                player.dropItem(IndustrialMod.latex, count);

                // Расходуем прочность: 1 единица урона игроку
                stack.damageItem(1, player);
            }
            world.playSoundAtEntity(player, "random.pop", 1.0F, 1.2F);
            return true;
        }
        return false;
    }
}