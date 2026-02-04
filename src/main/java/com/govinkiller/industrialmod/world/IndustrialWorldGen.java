package com.govinkiller.industrialmod.world;

import java.util.Random;
import com.govinkiller.industrialmod.blocks.ModBlocks;
import com.govinkiller.industrialmod.IndustrialMod;
import com.govinkiller.industrialmod.blocks.BlockIndustrialOre;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.block.Block; // ВАЖНО: добавь этот импорт

public class IndustrialWorldGen implements IWorldGenerator {

    @Override // ОСТАВЬ ТОЛЬКО ОДИН @Override
    public void generate(Random rand, int chunkX, int chunkZ,
                         World world, IChunkProvider chunkProvider,
                         IChunkProvider chunkProvider2) {

        if (world.provider.dimensionId == 0) { // только Верхний мир

            // 1. Старая руда (теперь вызываем через Generic, чтобы не было ошибки)
            this.runGeneratorGeneric(IndustrialMod.industrialOre, 6, 8, 4, 48, world, rand, chunkX*16, chunkZ*16);

            // 2. МЕДЬ
            this.runGeneratorGeneric(ModBlocks.oreCopper, 6, 10, 40, 75, world, rand, chunkX*16, chunkZ*16);

            // 3. ОЛОВО
            this.runGeneratorGeneric(ModBlocks.oreTin, 5, 8, 20, 55, world, rand, chunkX*16, chunkZ*16);
        }
    }

    // Это УНИВЕРСАЛЬНЫЙ метод, он заменит старый runGenerator
    private void runGeneratorGeneric(Block block, int veinSize, int chance, int minY,
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
