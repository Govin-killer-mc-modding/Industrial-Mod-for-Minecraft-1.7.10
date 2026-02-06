package com.govinkiller.industrialmod.world;

import java.util.Random;
import com.govinkiller.industrialmod.blocks.ModBlocks;
import com.govinkiller.industrialmod.IndustrialMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class IndustrialWorldGen implements IWorldGenerator {

    private WorldGenMinable copperOreGenerator;
    private WorldGenMinable tinOreGenerator;

    public IndustrialWorldGen() {
        this.copperOreGenerator = new WorldGenMinable(ModBlocks.oreCopper, 8);
        this.tinOreGenerator = new WorldGenMinable(ModBlocks.oreTin, 7);
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkProvider, IChunkProvider chunkProvider2) {
        if (world.provider.dimensionId == 0) { // только Верхний мир

            // --- 1. ГЕНЕРАЦИЯ РУД (ВСЕ ТВОИ РУДЫ НА МЕСТЕ) ---
            this.runGeneratorGeneric(IndustrialMod.industrialOre, 6, 8, 4, 48, world, rand, chunkX*16, chunkZ*16);
            this.runGeneratorGeneric(ModBlocks.oreCopper, 6, 10, 40, 75, world, rand, chunkX*16, chunkZ*16);
            this.runGeneratorGeneric(ModBlocks.oreTin, 5, 8, 20, 55, world, rand, chunkX*16, chunkZ*16);

            // --- 2. ГЕНЕРАЦИЯ ДЕРЕВЬЕВ ---
            this.generateTreesInBiome(world, rand, chunkX * 16, chunkZ * 16);
        }
    }

    private void runGeneratorGeneric(Block block, int veinSize, int chance, int minY, int maxY, World world, Random rand, int chunkX, int chunkZ) {
        int deltaY = maxY - minY;
        for (int i = 0; i < chance; i++) {
            int x = chunkX + rand.nextInt(16);
            int y = minY + rand.nextInt(deltaY);
            int z = chunkZ + rand.nextInt(16);
            new WorldGenMinable(block, veinSize).generate(world, rand, x, y, z);
        }
    }

    private void generateTreesInBiome(World world, Random random, int x, int z) {
        WorldGenRubberTree rubberTreeGenerator = new WorldGenRubberTree(true, 5, ModBlocks.rubberLog, ModBlocks.rubberLeaves, false);
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

        int attempts = 0;

        // ПРОВЕРКА БИОМОВ:
        if (biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleEdge || biome == BiomeGenBase.swampland) {
            // В джунглях/болотах: шанс высокий (каждый 2-й чанк), 0-1 дерево
            if (random.nextInt(2) == 0) {
                attempts = random.nextInt(1);
            }
        }
        else {
            // В остальных биомах: очень редко (шанс 1%, т.е. 1 из 100 чанков)
            if (random.nextInt(100) == 0) {
                attempts = 1;
            }
        }

        // ... (остальной код по поиску почвы и установке дерева остается прежним) ...

        // Цикл попыток установки дерева
        for (int i = 0; i < attempts; i++) {
            // Смещение +8 для стабильности на границах чанков
            int Xcoord = x + random.nextInt(16) + 8;
            int Zcoord = z + random.nextInt(16) + 8;
            int Ycoord = world.getHeightValue(Xcoord, Zcoord);

            // Проверка почвы: только на траве или земле
            Block blockUnder = world.getBlock(Xcoord, Ycoord - 1, Zcoord);
            if (blockUnder == Blocks.grass || blockUnder == Blocks.dirt) {
                rubberTreeGenerator.generate(world, random, Xcoord, Ycoord, Zcoord);
            }
        }
    }
}