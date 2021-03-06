package stevekung.mods.moreplanets.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import stevekung.mods.moreplanets.init.MPBlocks;

public class TeleporterMP extends Teleporter
{
    private WorldServer worldServerInstance;
    private Random random;
    private Long2ObjectMap<PortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap<>(4096);
    private List<Long> destinationCoordinateKeys = new ArrayList<>();

    public TeleporterMP(WorldServer world)
    {
        super(world);
        this.worldServerInstance = world;
        this.random = new Random(world.getSeed());
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if (GCCoreUtil.getDimensionID(this.worldServerInstance) != 1)
        {
            if (!this.placeInExistingPortal(entity, rotationYaw))
            {
                this.makePortal(entity);
                this.placeInExistingPortal(entity, rotationYaw);
            }
        }
        else
        {
            int x = MathHelper.floor(entity.posX);
            int y = MathHelper.floor(entity.posY) - 1;
            int z = MathHelper.floor(entity.posZ);
            this.worldServerInstance.setBlockState(new BlockPos(x, y, z), MPBlocks.SPACE_PORTAL.getDefaultState());
            entity.setLocationAndAngles(x, y + 0.75D, z, entity.rotationYaw, 0.0F);
            entity.motionX = entity.motionY = entity.motionZ = 0.0D;
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        double d0 = -1.0D;
        int j = MathHelper.floor(entity.posX);
        int k = MathHelper.floor(entity.posZ);
        boolean flag = true;
        BlockPos blockpos = BlockPos.ORIGIN;
        long l = ChunkPos.asLong(j, k);

        if (this.destinationCoordinateCache.containsKey(l))
        {
            PortalPosition teleporter$portalposition = this.destinationCoordinateCache.get(l);
            d0 = 0.0D;
            blockpos = teleporter$portalposition;
            teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            flag = false;
        }
        else
        {
            BlockPos blockpos3 = new BlockPos(entity);

            for (int i1 = -128; i1 <= 128; ++i1)
            {
                BlockPos blockpos2;

                for (int j1 = -128; j1 <= 128; ++j1)
                {
                    for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2)
                    {
                        blockpos2 = blockpos1.down();

                        if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == MPBlocks.SPACE_PORTAL)
                        {
                            while (this.worldServerInstance.getBlockState(blockpos2 = blockpos1.down()).getBlock() == MPBlocks.SPACE_PORTAL)
                            {
                                blockpos1 = blockpos2;
                            }

                            double d1 = blockpos1.distanceSq(blockpos3);

                            if (d0 < 0.0D || d1 < d0)
                            {
                                d0 = d1;
                                blockpos = blockpos1;
                            }
                        }
                    }
                }
            }
        }

        if (d0 >= 0.0D)
        {
            if (flag)
            {
                this.destinationCoordinateCache.put(l, new TeleporterMP.PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
                this.destinationCoordinateKeys.add(Long.valueOf(l));
            }
            double d5 = blockpos.getX() + 0.5D;
            double d6 = blockpos.getY() + 0.5D;
            double d7 = blockpos.getZ() + 0.5D;
            double d3 = entity.motionX;
            double d4 = entity.motionZ;
            entity.motionX = d3 * d4;
            entity.motionZ = d3 * d4;
            entity.setLocationAndAngles(d5, d6 + 0.75D, d7, entity.rotationYaw, entity.rotationPitch);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean makePortal(Entity entity)
    {
        int i = 16;
        double d0 = -1.0D;
        int j = MathHelper.floor(entity.posX);
        int k = MathHelper.floor(entity.posY);
        int l = MathHelper.floor(entity.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = this.random.nextInt(4);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j2 = j - i; j2 <= j + i; ++j2)
        {
            double d1 = j2 + 0.5D - entity.posX;

            for (int l2 = l - i; l2 <= l + i; ++l2)
            {
                double d2 = l2 + 0.5D - entity.posZ;

                label142:
                    for (int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; --j3)
                    {
                        if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2)))
                        {
                            while (j3 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2)))
                            {
                                --j3;
                            }

                            for (int k3 = i2; k3 < i2 + 4; ++k3)
                            {
                                int l3 = k3 % 2;
                                int i4 = 1 - l3;

                                if (k3 % 4 >= 2)
                                {
                                    l3 = -l3;
                                    i4 = -i4;
                                }

                                for (int j4 = 0; j4 < 3; ++j4)
                                {
                                    for (int k4 = 0; k4 < 4; ++k4)
                                    {
                                        for (int l4 = -1; l4 < 4; ++l4)
                                        {
                                            int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                            int j5 = j3 + l4;
                                            int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                            blockpos$mutableblockpos.setPos(i5, j5, k5);

                                            if (l4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
                                            {
                                                continue label142;
                                            }
                                        }
                                    }
                                }

                                double d5 = j3 + 0.5D - entity.posY;
                                double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                                if (d0 < 0.0D || d7 < d0)
                                {
                                    d0 = d7;
                                    i1 = j2;
                                    j1 = j3;
                                    k1 = l2;
                                    l1 = k3 % 4;
                                }
                            }
                        }
                    }
            }
        }

        if (d0 < 0.0D)
        {
            for (int l5 = j - i; l5 <= j + i; ++l5)
            {
                double d3 = l5 + 0.5D - entity.posX;

                for (int j6 = l - i; j6 <= l + i; ++j6)
                {
                    double d4 = j6 + 0.5D - entity.posZ;

                    label562:
                        for (int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; --i7)
                        {
                            if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6)))
                            {
                                while (i7 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6)))
                                {
                                    --i7;
                                }

                                for (int k7 = i2; k7 < i2 + 2; ++k7)
                                {
                                    int j8 = k7 % 2;
                                    int j9 = 1 - j8;

                                    for (int j10 = 0; j10 < 4; ++j10)
                                    {
                                        for (int j11 = -1; j11 < 4; ++j11)
                                        {
                                            int j12 = l5 + (j10 - 1) * j8;
                                            int i13 = i7 + j11;
                                            int j13 = j6 + (j10 - 1) * j9;
                                            blockpos$mutableblockpos.setPos(j12, i13, j13);

                                            if (j11 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos))
                                            {
                                                continue label562;
                                            }
                                        }
                                    }

                                    double d6 = i7 + 0.5D - entity.posY;
                                    double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                    if (d0 < 0.0D || d8 < d0)
                                    {
                                        d0 = d8;
                                        i1 = l5;
                                        j1 = i7;
                                        k1 = j6;
                                        l1 = k7 % 2;
                                    }
                                }
                            }
                        }
                }
            }
        }

        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if (l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if (d0 < 0.0D)
        {
            j1 = MathHelper.clamp(j1, 70, this.worldServerInstance.getActualHeight() - 10);
            k2 = j1;

            for (int j7 = -1; j7 <= 1; ++j7)
            {
                for (int l7 = 1; l7 < 3; ++l7)
                {
                    for (int k8 = -1; k8 < 3; ++k8)
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        this.worldServerInstance.setBlockState(new BlockPos(i6, k2, k6), MPBlocks.SPACE_PORTAL.getDefaultState(), 2);
        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime)
    {
        if (worldTime % 100L == 0L)
        {
            ObjectIterator<PortalPosition> objectiterator = this.destinationCoordinateCache.values().iterator();
            long i = worldTime - 300L;

            while (objectiterator.hasNext())
            {
                PortalPosition teleporter$portalposition = objectiterator.next();

                if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i)
                {
                    objectiterator.remove();
                }
            }
        }
    }

    public class PortalPosition extends BlockPos
    {
        public long lastUpdateTime;

        public PortalPosition(BlockPos pos, long lastUpdate)
        {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.lastUpdateTime = lastUpdate;
        }
    }
}