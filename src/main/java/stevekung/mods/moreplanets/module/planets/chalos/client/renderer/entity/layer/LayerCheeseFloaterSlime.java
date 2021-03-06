package stevekung.mods.moreplanets.module.planets.chalos.client.renderer.entity.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.chalos.blocks.ChalosBlocks;
import stevekung.mods.moreplanets.module.planets.chalos.client.renderer.entity.RenderCheeseFloater;
import stevekung.mods.moreplanets.module.planets.chalos.entity.EntityCheeseFloater;

@SideOnly(Side.CLIENT)
public class LayerCheeseFloaterSlime implements LayerRenderer<EntityCheeseFloater>
{
    private RenderCheeseFloater render;

    public LayerCheeseFloaterSlime(RenderCheeseFloater render)
    {
        this.render = render;
    }

    @Override
    public void doRenderLayer(EntityCheeseFloater entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entity.getHealth() > 0.0F)
        {
            float partialTicksTime = entity.ticksExisted + partialTicks;
            float hoverTime = MathHelper.sin(partialTicksTime / 12) / 50.0F + 0.5F;
            hoverTime = hoverTime * hoverTime + hoverTime;
            EntityItem item = new EntityItem(entity.world, 0.0D, 0.0D, 0.0D, new ItemStack(ChalosBlocks.CHEESE_SLIME_BLOCK));
            item.hoverStart = 0.0F;
            GlStateManager.translate(0.0F, 1.615F - hoverTime, 0.0F);
            GlStateManager.rotate(180.0F, 1, 0, 0);
            this.render.getRenderManager().renderEntity(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}