package com.govinkiller.industrialmod.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockRubberLog extends BlockLog {

    @SideOnly(Side.CLIENT)
    private IIcon iconLogTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconLogSide;

    public BlockRubberLog() {
        super();
        this.setBlockName("rubberLog");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0F); // Прочность как у обычного дерева
        this.setStepSound(soundTypeWood);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        // Регистрируем две разные текстуры
        this.iconLogSide = iconRegister.registerIcon("industrialmod:rubberLog");
        this.iconLogTop = iconRegister.registerIcon("industrialmod:rubberLog_top");
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int side) {
        // Сторона бревна
        return this.iconLogSide;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int side) {
        // Срез бревна
        return this.iconLogTop;
    }
}
