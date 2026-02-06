package com.govinkiller.industrialmod.blocks;

import com.govinkiller.industrialmod.IndustrialMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockRubberLog extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon iconLogTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconLogSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconLogResin;

    public BlockRubberLog() {
        super(Material.wood);
        this.setBlockName("rubberLog");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        // Убедись, что названия файлов в assets ТОЧНО такие же
        this.iconLogSide = register.registerIcon("industrialmod:rubberLog");
        this.iconLogTop = register.registerIcon("industrialmod:rubberLog_top");
        this.iconLogResin = register.registerIcon("industrialmod:rubberLog_resin");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) return this.iconLogTop;

        // Пятно будет отображаться только на СЕВЕРНОЙ стороне (side 2)
        // и только если метаданные равны 1
        if (meta == 1 && side == 2) {
            return this.iconLogResin;
        }

        return this.iconLogSide;
    }

    // Это критически важно: когда ты ставишь блок из инвентаря,
    // он должен сохранить свои метаданные (1), а не сбросить их в 0.
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return meta;
    }

    // Чтобы при ломании выпадало обычное дерево без латекса
    @Override
    public int damageDropped(int meta) {
        return 0;
    }
    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        // Помогает игре правильно определять текстуру установленного блока
        return world.getBlockMetadata(x, y, z);
    }
}