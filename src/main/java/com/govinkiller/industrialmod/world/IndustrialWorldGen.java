package com.govinkiller.industrialmod.world;

import java.util.Random;

import com.govinkiller.industrialmod.IndustrialMod;
import com.govinkiller.industrialmod.blocks.BlockIndustrialOre;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class IndustrialWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkX, int chunkZ,
                         World world, IChunkProvider chunkProvider,
                         IChunkProvider chunkProvider2) {

        if (world.provider.dimensionId == 0) {              // только Верхний мир
            this.runGenerator(
                    IndustrialMod.industrialOre,                // что генерируем
                    8,                                          // размер жилы
                    20,                                         // жил на чанк
                    4, 32,                                      // высота Y
                    world, rand, chunkX*16, chunkZ*16);
        }
    }

    private void runGenerator(BlockIndustrialOre block, int veinSize, int chance, int minY,
                              int maxY, World world, Random rand,
                              int chunkX, int chunkZ) {

        int deltaY = maxY - minY;
        for (int i = 0; i < chance; i++) {
            int x = chunkX + rand.nextInt(16);
            int y = minY + rand.nextInt(deltaY);
            int z = chunkZ + rand.nextInt(16);

            new WorldGenMinable(block, veinSize).generate(world, rand, x, y, z);
        }
    }
}