package com.govinkiller.industrialmod.inventory;

import com.govinkiller.industrialmod.tileentity.TileEntityCoalGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCoalGenerator extends Container {
    private TileEntityCoalGenerator tileEntity;
    private int lastEnergy;
    private int lastBurnTime;
    private int lastMaxBurnTime;

    public ContainerCoalGenerator(InventoryPlayer playerInv, TileEntityCoalGenerator tile) {
        this.tileEntity = tile;

        // СЛОТ ТОПЛИВА (38, 52)
        this.addSlotToContainer(new Slot(tile, 0, 44, 52));

        // ИНВЕНТАРЬ ИГРОКА (9x3 сетка)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // ХОТБАР (9 слотов)
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);
        // Отправляем начальные значения при открытии GUI
        icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.storage.getEnergyStored());
        icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
        icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.maxBurnTime);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            // Синхронизация Энергии (ID 0)
            if (this.lastEnergy != this.tileEntity.storage.getEnergyStored()) {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.storage.getEnergyStored());
            }
            // Синхронизация Огня (ID 1)
            if (this.lastBurnTime != this.tileEntity.burnTime) {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntity.burnTime);
            }
            // Синхронизация Макс. времени горения (ID 2)
            if (this.lastMaxBurnTime != this.tileEntity.maxBurnTime) {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.maxBurnTime);
            }
        }
        this.lastEnergy = this.tileEntity.storage.getEnergyStored();
        this.lastBurnTime = this.tileEntity.burnTime;
        this.lastMaxBurnTime = this.tileEntity.maxBurnTime;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        // Принимаем данные на стороне клиента и записываем в TileEntity
        if (id == 0) this.tileEntity.storage.setEnergyStored(data);
        if (id == 1) this.tileEntity.burnTime = data;
        if (id == 2) this.tileEntity.maxBurnTime = data;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            // Если кликаем по слоту топлива (индекс 0) — переносим в инвентарь игрока
            if (slotIndex == 0) {
                if (!this.mergeItemStack(itemstack1, 1, 37, true)) return null;
            }
            // Если кликаем по инвентарю — проверяем, топливо ли это, и несем в слот 0
            else {
                if (TileEntityFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) return null;
                }
            }

            if (itemstack1.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (itemstack1.stackSize == itemstack.stackSize) return null;
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;
    }
}