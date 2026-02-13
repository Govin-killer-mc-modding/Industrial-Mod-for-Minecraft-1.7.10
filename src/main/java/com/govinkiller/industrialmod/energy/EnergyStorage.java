package com.govinkiller.industrialmod.energy;

import net.minecraft.nbt.NBTTagCompound;

public class EnergyStorage {
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) energy += energyReceived;
        return energyReceived;
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) energy -= energyExtracted;
        return energyExtracted;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.energy = nbt.getInteger("Energy");
        if (energy > capacity) energy = capacity;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Energy", energy);
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;
        if (this.energy > capacity) this.energy = capacity;
        if (this.energy < 0) this.energy = 0;
    }

    public int getEnergyStored() { return energy; }
    public int getMaxEnergyStored() { return capacity; }

    public void setEnergy(int energy) { this.energy = energy; }
}