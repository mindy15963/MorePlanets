package stevekung.mods.moreplanets.planets.nibiru.world.gen.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import stevekung.mods.moreplanets.init.MPBlocks;
import stevekung.mods.moreplanets.utils.blocks.BlockBushMP;

public class WorldGenNibiruSeaweed extends WorldGenerator
{
    private final IBlockState flower;

    public WorldGenNibiruSeaweed(IBlockState flower)
    {
        this.flower = flower;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos pos1 = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (world.getBlockState(pos1).getBlock() == MPBlocks.INFECTED_WATER_FLUID_BLOCK && pos1.getY() < 255 && ((BlockBushMP)this.flower.getBlock()).canBlockStay(world, pos1, this.flower))
            {
                world.setBlockState(pos1, this.flower, 2);
            }
        }
        return true;
    }
}