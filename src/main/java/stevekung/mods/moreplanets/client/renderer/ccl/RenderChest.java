package stevekung.mods.moreplanets.client.renderer.ccl;

import java.util.HashMap;
import java.util.Map;

import codechicken.lib.render.CCModelState;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.model.TRSRTransformation;
import stevekung.mods.moreplanets.util.helper.ClientRegisterHelper;

public class RenderChest extends CCLRenderBase
{
    private final TileEntity tile;

    public RenderChest(TileEntity tile)
    {
        this.tile = tile;
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemCameraTransforms.TransformType type)
    {
        ClientRegisterHelper.registerTileEntityItemStackRendering(this.tile);
    }

    @Override
    protected CCModelState getCustomTransforms()
    {
        Map<TransformType, TRSRTransformation> map = new HashMap<>();
        TRSRTransformation thirdPerson = TransformUtils.create(0, 2.5F, 0, 75, 315, 0, 0.375F);
        map.put(TransformType.GUI, TransformUtils.create(0, 0, 0, 30, 45, 0, 0.625F));
        map.put(TransformType.GROUND, TransformUtils.create(0, 3, 0, 0, 0, 0, 0.25F));
        map.put(TransformType.FIXED, TransformUtils.create(0, 0, 0, 0, 0, 0, 0.5F));
        map.put(TransformType.THIRD_PERSON_RIGHT_HAND, thirdPerson);
        map.put(TransformType.THIRD_PERSON_LEFT_HAND, thirdPerson);
        map.put(TransformType.FIRST_PERSON_RIGHT_HAND, TransformUtils.create(0, 0, 0, 0, 315, 0, 0.4F));
        map.put(TransformType.FIRST_PERSON_LEFT_HAND, TransformUtils.create(0, 0, 0, 0, 315, 0, 0.4F));
        return new CCModelState(map);
    }
}