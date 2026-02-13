package com.govinkiller.industrialmod;

import com.govinkiller.industrialmod.inventory.ContainerCoalGenerator;
import com.govinkiller.industrialmod.tileentity.TileEntityCoalGenerator;
import com.govinkiller.industrialmod.client.gui.GuiCoalGenerator; // Создадим его чуть позже
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityCoalGenerator) {
            return new ContainerCoalGenerator(player.inventory, (TileEntityCoalGenerator) tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityCoalGenerator) {
            return new GuiCoalGenerator(player.inventory, (TileEntityCoalGenerator) tile);
        }
        return null;
    }
}
