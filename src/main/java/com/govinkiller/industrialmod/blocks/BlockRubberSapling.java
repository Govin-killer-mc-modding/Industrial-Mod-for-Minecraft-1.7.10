package com.govinkiller.industrialmod.blocks;

import com.govinkiller.industrialmod.IndustrialMod;
import com.govinkiller.industrialmod.world.WorldGenRubberTree; // Импортируем генератор дерева
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

public class BlockRubberSapling extends BlockSapling implements IGrowable {

    public BlockRubberSapling() {
        super();
        this.setBlockName("rubberSapling");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setStepSound(soundTypeGrass);
    }

    // --- ЛОГИКА РОСТА ОТ КОСТНОЙ МУКИ ---

    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean isClient) {
        return true; // Можно юзать муку
    }

    @Override
    public boolean func_149852_a(World world, Random random, int x, int y, int z) {
        return (double)world.rand.nextFloat() < 0.45D; // Шанс успеха
    }

    @Override
    public void func_149853_b(World world, Random random, int x, int y, int z) {
        this.func_149878_d(world, x, y, z, random);
    }

    // Метод, который вызывается при успешном росте
    @Override
    public void func_149878_d(World world, int x, int y, int z, Random random) {
        // Создаем генератор:
        // notify: true, minHeight: 5, log: наш блок, leaves: наш блок, vines: false
        WorldGenRubberTree treeGenerator = new WorldGenRubberTree(true, 5, ModBlocks.rubberLog, ModBlocks.rubberLeaves, false);

        // Убираем саженец
        world.setBlockToAir(x, y, z);

        // Пытаемся вырастить дерево
        if (!treeGenerator.generate(world, random, x, y, z)) {
            // Если места не хватило (например, потолок низко), ставим саженец обратно
            world.setBlock(x, y, z, this);
        }
    }

    // --- ТЕКСТУРЫ И ОТОБРАЖЕНИЕ ---

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("industrialmod:rubberSapling");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        // Чистый белый цвет для инвентаря
        return 16777215;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        // Чистый белый цвет для мира
        return 16777215;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        // Принудительно возвращаем нашу иконку вместо дуба
        return this.blockIcon;
    }

}
