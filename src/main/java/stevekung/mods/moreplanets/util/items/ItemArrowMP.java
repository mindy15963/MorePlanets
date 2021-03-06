package stevekung.mods.moreplanets.util.items;

import java.util.List;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.core.TransformerHooks;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.core.MorePlanetsCore;
import stevekung.mods.moreplanets.module.planets.diona.entity.projectile.EntityAntiGravityArrow;
import stevekung.mods.moreplanets.module.planets.diona.entity.projectile.EntityInfectedCrystallizeArrow;
import stevekung.mods.moreplanets.module.planets.diona.items.DionaItems;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.projectile.EntityInfectedArrow;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;
import stevekung.mods.moreplanets.util.helper.ItemDescriptionHelper;

public class ItemArrowMP extends ItemArrow implements ISortableItem, ISingleItemRender
{
    private ArrowType type;
    private String name;

    public ItemArrowMP(String name, ArrowType type)
    {
        this.type = type;
        this.name = name;
        this.setUnlocalizedName(name);
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return MorePlanetsCore.ITEM_TAB;
    }

    @Override
    public EnumSortCategoryItem getItemCategory(int meta)
    {
        return EnumSortCategoryItem.ARROW;
    }

    @Override
    public EntityArrow createArrow(World world, ItemStack itemStack, EntityLivingBase shooter)
    {
        switch (this.type)
        {
        case INFECTED_CRYSTALLIZE:
            return new EntityInfectedCrystallizeArrow(world, shooter);
        case INFECTED:
            return new EntityInfectedArrow(world, shooter);
        case ANTI_GRAVITY:
            return new EntityAntiGravityArrow(world, shooter);
        default:
            return super.createArrow(world, itemStack, shooter);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
        if (this.type == ArrowType.INFECTED)
        {
            if (CommonRegisterHelper.isShiftKeyDown())
            {
                list.addAll(ItemDescriptionHelper.getDescription(this.getUnlocalizedName() + ".description"));
            }
            else
            {
                list.add(GCCoreUtil.translate("desc.shift_info.name"));
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        if (entityItem.getItem().getItem() == DionaItems.ANTI_GRAVITY_ARROW)
        {
            entityItem.motionY += TransformerHooks.getItemGravity(entityItem);
        }
        return false;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    public static enum ArrowType
    {
        INFECTED_CRYSTALLIZE,
        INFECTED,
        ANTI_GRAVITY;
    }
}