package stevekung.mods.moreplanets.module.planets.chalos.blocks;

import net.minecraft.block.Block;
import stevekung.mods.moreplanets.util.blocks.BlockFarmlandMP;
import stevekung.mods.moreplanets.util.helper.BlockStateHelper;

public class BlockCheeseFarmland extends BlockFarmlandMP
{
    public BlockCheeseFarmland(String name)
    {
        super();
        this.name = name;
        this.setDefaultState(this.getDefaultState().withProperty(BlockStateHelper.MOISTURE, Integer.valueOf(0)));
        this.setUnlocalizedName(name);
    }

    @Override
    protected Block getDirtBlock()
    {
        return ChalosBlocks.CHEESE_DIRT;
    }

    @Override
    protected Block getSourceBlock()
    {
        return ChalosBlocks.CHEESE_OF_MILK_FLUID_BLOCK;
    }
}