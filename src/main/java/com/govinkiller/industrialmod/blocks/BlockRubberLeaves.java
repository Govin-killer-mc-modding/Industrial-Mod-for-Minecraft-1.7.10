package com.govinkiller.industrialmod.blocks;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.govinkiller.industrialmod.IndustrialMod;

public class BlockRubberLeaves extends BlockLeaves {

    public BlockRubberLeaves() {
        super();
        this.setBlockName("rubberLeaves");
        this.setBlockTextureName("industrialmod:rubberLeaves");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
    }

    // --- ИСПРАВЛЕНИЕ ОСЫПАНИЯ ---
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);

            // Если листва не поставлена игроком вручную
            if ((meta & 8) != 0 && (meta & 4) == 0) {
                byte range = 4;
                int maxRange = range + 1;
                byte checkArea = 32;
                int offset = checkArea / 2;

                // Проверяем блоки вокруг
                for (int dx = -range; dx <= range; ++dx) {
                    for (int dy = -range; dy <= range; ++dy) {
                        for (int dz = -range; dz <= range; ++dz) {
                            Block block = world.getBlock(x + dx, y + dy, z + dz);

                            // Если рядом есть наше бревно - листва ЖИВЕТ
                            if (block == ModBlocks.rubberLog) {
                                return;
                            }
                        }
                    }
                }
                // Если бревно не нашли - листва УМИРАЕТ (превращается в саженец)
                this.dropBlockAsItem(world, x, y, z, meta, 0);
                world.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("industrialmod:rubberLeaves");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemFromBlock(ModBlocks.rubberSapling);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    @Override
    public String[] func_150125_e() {
        return new String[] {"rubberLeaves"};
    }

    public String[] func_150129_e() {
        return new String[] {"rubberLeaves"};
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }
}
