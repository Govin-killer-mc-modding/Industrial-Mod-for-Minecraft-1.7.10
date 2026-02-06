package com.govinkiller.industrialmod.world;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import com.govinkiller.industrialmod.blocks.ModBlocks;

public class WorldGenRubberTree extends WorldGenTrees {

    private final Block log;
    private final Block leaf;

    public WorldGenRubberTree(boolean notify, int minTreeHeight, Block logBlock, Block leavesBlock, boolean vinesGrow) {
        super(notify, minTreeHeight, 0, 0, vinesGrow);
        this.log = logBlock;
        this.leaf = leavesBlock;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        int height = rand.nextInt(3) + 5; // Высота ствола 5-8 блоков

        if (y >= 1 && y + height + 1 <= 256) {
            Block below = world.getBlock(x, y - 1, z);
            if (below != net.minecraft.init.Blocks.grass && below != net.minecraft.init.Blocks.dirt) return false;

            // 1. Растим ствол
            for (int h = 0; h < height; h++) {
                int meta = (rand.nextInt(5) == 0) ? 1 : 0; // 20% шанс латекса
                world.setBlock(x, y + h, z, this.log, meta, 3);
            }

            // 2. Растим шапку пальмы (на вершине и по бокам)
            int topY = y + height;

            // Центральная платформа 3x3 на уровне вершины
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    world.setBlock(x + dx, topY, z + dz, this.leaf, 0, 3);
                }
            }
            // Центральный листик выше всех
            world.setBlock(x, topY + 1, z, this.leaf, 0, 3);

            // Дополнительные, свисающие листья (по углам и сторонам)
            world.setBlock(x + 2, topY - 1, z, this.leaf, 0, 3);
            world.setBlock(x - 2, topY - 1, z, this.leaf, 0, 3);
            world.setBlock(x, topY - 1, z + 2, this.leaf, 0, 3);
            world.setBlock(x, topY - 1, z - 2, this.leaf, 0, 3);

            // Листья по диагонали
            world.setBlock(x + 2, topY - 1, z + 1, this.leaf, 0, 3);
            world.setBlock(x + 2, topY - 1, z - 1, this.leaf, 0, 3);
            world.setBlock(x - 2, topY - 1, z + 1, this.leaf, 0, 3);
            world.setBlock(x - 2, topY - 1, z - 1, this.leaf, 0, 3);

            return true;
        }
        return false;
    }
}