package com.govinkiller.industrialmod.blocks;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import com.govinkiller.industrialmod.IndustrialMod;

public class BlockRubberLeaves extends BlockLeaves {

    public BlockRubberLeaves() {
        super();
        this.setBlockName("rubberLeaves");
        // Важно: здесь название должно СТРОГО совпадать с файлом в assets
        this.setBlockTextureName("industrialmod:rubberLeaves");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
    }

    // 1. Исправляем прозрачность (убираем дыры в мир)
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    // 2. Метод для отрисовки текстуры (исправляет черно-розовые кубы)
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("industrialmod:rubberLeaves");
    }

    // 3. Исправляем вылет при рендере
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    // 4. Выпадение саженца (шанс 5%)
    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemFromBlock(ModBlocks.rubberSapling);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    // --- ОБЯЗАТЕЛЬНЫЕ МЕТОДЫ-ЗАГЛУШКИ ДЛЯ 1.7.10 ---

    @Override
    public String[] func_150125_e() {
        return new String[] {"rubberLeaves"};
    }

    // Без Override, чтобы IDE не ругалась, если метод не основной
    public String[] func_150129_e() {
        return new String[] {"rubberLeaves"};
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }
}