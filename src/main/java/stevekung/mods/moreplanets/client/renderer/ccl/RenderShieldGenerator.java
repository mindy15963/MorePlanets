package stevekung.mods.moreplanets.client.renderer.ccl;

import java.util.HashMap;
import java.util.Map;

import codechicken.lib.render.CCModelState;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.TRSRTransformation;
import stevekung.mods.moreplanets.client.renderer.tileentity.TileEntityShieldGeneratorRenderer;

public class RenderShieldGenerator extends CCLRenderBase
{
    @Override
    public void renderItem(ItemStack itemStack, ItemCameraTransforms.TransformType type)
    {
        TileEntityShieldGeneratorRenderer.INSTANCE.render();
    }

    @Override
    protected CCModelState getCustomTransforms()
    {
        Map<TransformType, TRSRTransformation> map = new HashMap<>();
        TRSRTransformation thirdPerson = TransformUtils.create(0, 2.5F, 0, 75, 45, 0, 0.375F);
        map.put(TransformType.GUI, TransformUtils.create(0, -2.65F, 0, 30, 135, 0, 0.455F));
        map.put(TransformType.GROUND, TransformUtils.create(0, 3, 0, 0, 0, 0, 0.25F));
        map.put(TransformType.FIXED, TransformUtils.create(0, 0, 0, 0, 180, 0, 0.5F));
        map.put(TransformType.THIRD_PERSON_RIGHT_HAND, thirdPerson);
        map.put(TransformType.THIRD_PERSON_LEFT_HAND, thirdPerson);
        map.put(TransformType.FIRST_PERSON_RIGHT_HAND, TransformUtils.create(0, 0, 0, 0, 315, 0, 0.4F));
        map.put(TransformType.FIRST_PERSON_LEFT_HAND, TransformUtils.create(0, 0, 0, 0, 315, 0, 0.4F));
        return new CCModelState(map);
    }
}