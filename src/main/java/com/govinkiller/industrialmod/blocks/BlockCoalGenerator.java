package com.govinkiller.industrialmod.blocks;

import com.govinkiller.industrialmod.IndustrialMod;
import com.govinkiller.industrialmod.tileentity.TileEntityCoalGenerator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

public class BlockCoalGenerator extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon iconFront;
    @SideOnly(Side.CLIENT)
    private IIcon[] energyIcons;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    public BlockCoalGenerator() {
        super(Material.iron);
        this.setBlockName("coalGenerator");
        this.setCreativeTab(IndustrialMod.tabIndustrial);
        this.setHardness(3.5F);
        this.setStepSound(soundTypeMetal);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCoalGenerator();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(IndustrialMod.instance, 0, world, x, y, z);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        if (l == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        if (l == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        if (l == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & 8) != 0 ? 13 : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("industrialmod:generator_side");
        this.iconFront = iconRegister.registerIcon("industrialmod:generator_front");
        this.iconTop = iconRegister.registerIcon("industrialmod:generator_top");

        this.energyIcons = new IIcon[5];
        for (int i = 0; i < 5; i++) {
            this.energyIcons[i] = iconRegister.registerIcon("industrialmod:generator_front_on_" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int meta = world.getBlockMetadata(x, y, z);
        int direction = meta & 7;
        boolean isActive = (meta & 8) != 0;

        if (side == direction) {
            if (isActive) {
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile instanceof TileEntityCoalGenerator) {
                    int stage = ((TileEntityCoalGenerator) tile).getEnergyScaled(4);
                    if (stage < 0) stage = 0;
                    if (stage > 4) stage = 4;
                    return this.energyIcons[stage];
                }
            }
            return this.iconFront;
        }
        return side == 1 || side == 0 ? this.iconTop : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1 || side == 0) return this.iconTop;
        if (side == 3) return this.iconFront;
        return this.blockIcon;
    }

    public static void updateBlockState(boolean active, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getTileEntity(x, y, z);

        // Сохраняем направление (0-7), меняем только 8-й бит
        int newMeta = active ? (meta & 7) | 8 : (meta & 7);

        // Ставим метадату с флагом 3 (обновить всё)
        world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);

        if (tileentity != null) {
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }

        // Генерируем пакет обновления для всех игроков рядом
        world.markBlockForUpdate(x, y, z);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, java.util.Random random) {
        int meta = world.getBlockMetadata(x, y, z);

        // Проверяем 8-й бит (горит ли уголь)
        if ((meta & 8) != 0) {
            int direction = meta & 7; // Направление лицевой панели

            // Координаты центра блока
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F; // Отступ от центра до края панели
            float f4 = random.nextFloat() * 0.6F - 0.3F;

            // Создаем искры в зависимости от того, куда смотрит блок
            if (direction == 4) { // West
                world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (direction == 5) { // East
                world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            } else if (direction == 2) { // North
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            } else if (direction == 3) { // South
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
