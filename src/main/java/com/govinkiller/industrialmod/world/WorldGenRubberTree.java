package com.govinkiller.industrialmod.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenRubberTree extends WorldGenTrees {

    // Создаем собственные переменные для хранения наших блоков
    private final Block log;
    private final Block leaf;

    public WorldGenRubberTree(boolean notify, int minTreeHeight, Block logBlock, Block leavesBlock, boolean vinesGrow) {
        // Вызываем конструктор родителя. 0, 0 — это стандартные метаданные дуба.
        super(notify, minTreeHeight, 0, 0, vinesGrow);

        // Запоминаем наши блоки в наши переменные
        this.log = logBlock;
        this.leaf = leavesBlock;
    }

    /**
     * Это "умный" перехватчик.
     * Когда стандартный алгоритм WorldGenTrees захочет поставить блок,
     * он вызовет этот метод. Мы проверяем материал и подсовываем свой блок.
     */
    // Попробуй заменить func_150515_a на это имя:
    @Override
    protected void setBlockAndNotifyAdequately(World world, int x, int y, int z, Block block, int meta) {
        // Логика остается той же самой:
        if (block.getMaterial() == net.minecraft.block.material.Material.wood) {
            super.setBlockAndNotifyAdequately(world, x, y, z, this.log, 0);
        }
        else if (block.getMaterial() == net.minecraft.block.material.Material.leaves) {
            super.setBlockAndNotifyAdequately(world, x, y, z, this.leaf, 0);
        }
        else {
            super.setBlockAndNotifyAdequately(world, x, y, z, block, meta);
        }
    }
}