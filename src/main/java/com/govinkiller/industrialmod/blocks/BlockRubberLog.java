package com.govinkiller.industrialmod.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRubberLog extends BlockLog {
    public BlockRubberLog() {
        super();
        this.setBlockName("rubberLog");
        this.setBlockTextureName("industrialmod:rubberLog");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
