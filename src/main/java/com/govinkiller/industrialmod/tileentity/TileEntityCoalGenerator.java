package com.govinkiller.industrialmod.tileentity;

import com.govinkiller.industrialmod.energy.EnergyStorage;
import com.govinkiller.industrialmod.blocks.BlockCoalGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.Constants;

public class TileEntityCoalGenerator extends TileEntity implements IInventory {

    public EnergyStorage storage = new EnergyStorage(16000, 100, 100);
    public int burnTime;    // Сколько осталось гореть текущему углю
    public int maxBurnTime; // Сколько всего горел этот уголь (для расчета % прогресса)

    private int lastEnergy;
    private ItemStack[] inventory = new ItemStack[1];

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;

        boolean isDirty = false;
        boolean wasBurning = this.burnTime > 0;

        // --- ЛОГИКА ГОРЕНИЯ ---
        if (this.burnTime > 0) {
            this.burnTime--;

            // Генерируем энергию только если есть место в буфере
            if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
                this.storage.receiveEnergy(10, false);
                isDirty = true;
            }
        }

        // --- ПОТРЕБЛЕНИЕ ТОПЛИВА ---
        // Если топливо кончилось, а энергия не полная — берем новый уголь
        if (this.burnTime <= 0 && this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
            if (inventory[0] != null) {
                int fuelValue = TileEntityFurnace.getItemBurnTime(inventory[0]);
                if (fuelValue > 0) {
                    this.maxBurnTime = fuelValue;
                    this.burnTime = fuelValue;
                    this.inventory[0].stackSize--;
                    if (this.inventory[0].stackSize <= 0) this.inventory[0] = null;
                    isDirty = true;
                }
            }
        }

        // --- ЛОГИКА ПОТЕРИ ЭНЕРГИИ (если ничего не горит) ---
        if (this.burnTime <= 0 && this.storage.getEnergyStored() > 0) {
            this.storage.extractEnergy(1, false);
            isDirty = true;
        }

        // Обновление блока (текстура огня)
        if (wasBurning != (this.burnTime > 0)) {
            BlockCoalGenerator.updateBlockState(this.burnTime > 0, worldObj, xCoord, yCoord, zCoord);
        }

        // Синхронизация с клиентом при изменении энергии
        if (this.storage.getEnergyStored() != lastEnergy) {
            this.lastEnergy = this.storage.getEnergyStored();
            this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        if (isDirty) this.markDirty();
    }

    // --- МЕТОДЫ ДЛЯ GUI ---

    /** Масштабирование энергии (вертикальная шкала) */
    public int getEnergyScaled(int pixels) {
        if (this.storage.getMaxEnergyStored() <= 0) return 0;
        return (this.storage.getEnergyStored() * pixels) / this.storage.getMaxEnergyStored();
    }

    /** Масштабирование огня (треугольник) */
    public int getBurnTimeRemainingScaled(int pixels) {
        if (this.maxBurnTime == 0) return 0;
        return (this.burnTime * pixels) / this.maxBurnTime;
    }

    /** Масштабирование стрелки (горизонтальная шкала)
     * Рассчитывается как: (Затраченное время / Общее время) * ширина
     */
    public int getCookProgressScaled(int pixels) {
        if (this.maxBurnTime <= 0) return 0;
        // Инвертируем, так как burnTime идет к нулю
        int cooked = this.maxBurnTime - this.burnTime;
        return (cooked * pixels) / this.maxBurnTime;
    }

    // --- Остальные методы (NBT, IInventory) оставляем без изменений ---
    @Override public int getSizeInventory() { return 1; }
    @Override public ItemStack getStackInSlot(int i) { return inventory[i]; }
    @Override public String getInventoryName() { return "tile.coalGenerator.name"; }
    @Override public boolean hasCustomInventoryName() { return false; }
    @Override public int getInventoryStackLimit() { return 64; }
    @Override public boolean isUseableByPlayer(EntityPlayer p) { return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && p.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64; }

    @Override
    public ItemStack decrStackSize(int i, int count) {
        if (inventory[i] != null) {
            ItemStack itemstack;
            if (inventory[i].stackSize <= count) {
                itemstack = inventory[i];
                inventory[i] = null;
                return itemstack;
            }
            itemstack = inventory[i].splitStack(count);
            if (inventory[i].stackSize == 0) inventory[i] = null;
            return itemstack;
        }
        return null;
    }

    @Override public ItemStack getStackInSlotOnClosing(int i) { return inventory[i]; }
    @Override public void setInventorySlotContents(int i, ItemStack s) { inventory[i] = s; }
    @Override public void openInventory() {}
    @Override public void closeInventory() {}
    @Override public boolean isItemValidForSlot(int i, ItemStack s) { return TileEntityFurnace.isItemFuel(s); }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
        nbt.setInteger("BurnTime", burnTime);
        nbt.setInteger("MaxBurn", maxBurnTime);
        NBTTagList list = new NBTTagList();
        if (inventory[0] != null) {
            NBTTagCompound slot = new NBTTagCompound();
            inventory[0].writeToNBT(slot);
            list.appendTag(slot);
        }
        nbt.setTag("Items", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        this.burnTime = nbt.getInteger("BurnTime");
        this.maxBurnTime = nbt.getInteger("MaxBurn");
        NBTTagList list = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        if (list.tagCount() > 0) {
            this.inventory[0] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(0));
        }
    }

    @Override
    public net.minecraft.network.Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new net.minecraft.network.play.server.S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}