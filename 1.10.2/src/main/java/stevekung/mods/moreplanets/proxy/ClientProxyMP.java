package stevekung.mods.moreplanets.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.obj.OBJLoader;
import stevekung.mods.moreplanets.client.renderer.*;
import stevekung.mods.moreplanets.core.MorePlanetsCore;
import stevekung.mods.moreplanets.init.MPItems;
import stevekung.mods.moreplanets.init.MPSchematics;
import stevekung.mods.moreplanets.module.planets.chalos.items.ChalosItems;
import stevekung.mods.moreplanets.module.planets.diona.blocks.DionaBlocks;
import stevekung.mods.moreplanets.module.planets.diona.client.particle.ParticleAlienMinerSpark;
import stevekung.mods.moreplanets.module.planets.diona.client.particle.ParticleCrystallizeFlame;
import stevekung.mods.moreplanets.module.planets.diona.client.particle.ParticleDarkPortal;
import stevekung.mods.moreplanets.module.planets.diona.items.DionaItems;
import stevekung.mods.moreplanets.module.planets.fronos.blocks.FronosBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.client.particle.ParticleAlienBerry;
import stevekung.mods.moreplanets.module.planets.nibiru.client.particle.ParticleInfectedGuardianAppearance;
import stevekung.mods.moreplanets.module.planets.nibiru.client.particle.ParticleInfectedSpore;
import stevekung.mods.moreplanets.module.planets.nibiru.items.NibiruItems;
import stevekung.mods.moreplanets.util.EnumParticleTypesMP;
import stevekung.mods.moreplanets.util.blocks.BlockStemMP;
import stevekung.mods.moreplanets.util.client.particle.ParticleBreakingMC;
import stevekung.mods.moreplanets.util.client.particle.ParticleLavaMC;
import stevekung.mods.moreplanets.util.client.particle.ParticleLiquidDrip;
import stevekung.mods.moreplanets.util.helper.ClientRegisterHelper;
import stevekung.mods.moreplanets.util.helper.ColorHelper;

public class ClientProxyMP extends ServerProxyMP
{
    @Override
    public void registerPreRendering()
    {
        OBJLoader.INSTANCE.addDomain(MorePlanetsCore.MOD_ID);
        EntityRendererMP.init();
    }

    @Override
    public void registerInitRendering()
    {
        BlockColors color = Minecraft.getMinecraft().getBlockColors();
        TileEntityItemStackRenderer.instance = new TileEntityItemStackRendererMP();
        TileEntityRenderer.init();
        ItemModelRenderer.init();
        VariantsRenderer.init();

        ClientRegisterHelper.registerBlockColor((state, world, pos, tint) -> world != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(world, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D), FronosBlocks.FRONOS_GRASS);
        ClientRegisterHelper.registerBlockColor((state, world, pos, tint) -> ColorHelper.rgbToDecimal(120, 85, 190), DionaBlocks.LARGE_INFECTED_CRYSTALLIZE);
        ClientRegisterHelper.registerBlockColor((state, world, pos, tint) -> ColorHelper.rgbToDecimal(50, 101, 236), NibiruBlocks.MULTALIC_CRYSTAL);
        ClientRegisterHelper.registerBlockColor((state, world, pos, tint) -> ColorHelper.rgbToDecimal(50, 101, 236), NibiruBlocks.MULTALIC_CRYSTAL_BLOCK);
        ClientRegisterHelper.registerBlockColor((state, world, pos, tint) -> state.getValue(BlockStemMP.AGE).intValue() * 32 << 16 | 255 - state.getValue(BlockStemMP.AGE).intValue() * 8 << 8 | state.getValue(BlockStemMP.AGE).intValue() * 4, NibiruBlocks.INFECTED_MELON_STEM);

        ClientRegisterHelper.registerItemColor((itemStack, tintIndex) -> color.colorMultiplier(((ItemBlock)itemStack.getItem()).getBlock().getStateFromMeta(itemStack.getMetadata()), null, null, tintIndex), FronosBlocks.FRONOS_GRASS);
        ClientRegisterHelper.registerItemColor((itemStack, tintIndex) -> ColorHelper.rgbToDecimal(50, 101, 236), NibiruBlocks.MULTALIC_CRYSTAL_BLOCK);
        ClientRegisterHelper.registerItemColor((itemStack, tintIndex) -> itemStack.hasTagCompound() && tintIndex == 1 ? itemStack.getTagCompound().getInteger("Color") : -1, MPItems.CAPSULE);
    }

    @Override
    public void registerPostRendering()
    {
        BlockStateMapper.init();
        MPSchematics.registerSchematicTexture();
    }

    @Override
    public void spawnParticle(EnumParticleTypesMP type, double x, double y, double z)
    {
        this.spawnParticle(type, x, y, z, 0.0D, 0.0D, 0.0D, null);
    }

    @Override
    public void spawnParticle(EnumParticleTypesMP type, double x, double y, double z, Object[] objects)
    {
        this.spawnParticle(type, x, y, z, 0.0D, 0.0D, 0.0D, objects);
    }

    @Override
    public void spawnParticle(EnumParticleTypesMP type, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        this.spawnParticle(type, x, y, z, motionX, motionY, motionZ, null);
    }

    @Override
    public void spawnParticle(EnumParticleTypesMP type, double x, double y, double z, double motionX, double motionY, double motionZ, Object[] data)
    {
        Particle entityfx = null;
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.getRenderViewEntity() != null && mc.effectRenderer != null && mc.theWorld != null)
        {
            int i = mc.gameSettings.particleSetting;
            double d6 = mc.getRenderViewEntity().posX - x;
            double d7 = mc.getRenderViewEntity().posY - y;
            double d8 = mc.getRenderViewEntity().posZ - z;
            double d9 = 16.0D;

            if (i == 1 && mc.theWorld.rand.nextInt(3) == 0)
            {
                i = 2;
            }
            if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
            {
                return;
            }
            else if (i > 1)
            {
                return;
            }

            if (type == EnumParticleTypesMP.CRYSTALLIZE_FLAME)
            {
                entityfx = new ParticleCrystallizeFlame(mc.theWorld, x, y, z);
            }
            else if (type == EnumParticleTypesMP.CHEESE_OF_MILK_DRIP)
            {
                entityfx = new ParticleLiquidDrip(mc.theWorld, x, y, z, 1.0F, 0.85F, 0.5F, 0.4F, false);
            }
            else if (type == EnumParticleTypesMP.INFECTED_SPORE)
            {
                entityfx = new ParticleInfectedSpore(mc.theWorld, x, y, z, motionX, motionY, motionZ);
            }
            else if (type == EnumParticleTypesMP.ALIEN_MINER_SPARK)
            {
                entityfx = new ParticleAlienMinerSpark(mc.theWorld, x, y, z, (float) data[0]);
            }
            else if (type == EnumParticleTypesMP.INFECTED_GUARDIAN_APPEARANCE)
            {
                entityfx = new ParticleInfectedGuardianAppearance(mc.theWorld, x, y, z);
            }
            else if (type == EnumParticleTypesMP.DARK_PORTAL)
            {
                entityfx = new ParticleDarkPortal(mc.theWorld, x, y, z, motionX, motionY, motionZ);
            }
            else if (type == EnumParticleTypesMP.ALIEN_BERRY_LEAVES)
            {
                entityfx = new ParticleAlienBerry(mc.theWorld, x, y, z);
            }
            else if (type == EnumParticleTypesMP.CHEESE_SLIME)
            {
                entityfx = new ParticleBreakingMC(mc.theWorld, x, y, z, ChalosItems.CHEESE_SLIMEBALL);
            }
            else if (type == EnumParticleTypesMP.INFECTED_CRYSTALLIZE_SLIME)
            {
                entityfx = new ParticleBreakingMC(mc.theWorld, x, y, z, DionaItems.INFECTED_CRYSTALLIZE_SLIMEBALL);
            }
            else if (type == EnumParticleTypesMP.INFECTED_EGG)
            {
                entityfx = new ParticleBreakingMC(mc.theWorld, x, y, z, motionX, motionY, motionZ, NibiruItems.INFECTED_EGG, 0);
            }
            else if (type == EnumParticleTypesMP.INFECTED_SNOWBALL)
            {
                entityfx = new ParticleBreakingMC(mc.theWorld, x, y, z, NibiruItems.INFECTED_SNOWBALL);
            }
            else if (type == EnumParticleTypesMP.INFECTED_WATER_DRIP)
            {
                entityfx = new ParticleLiquidDrip(mc.theWorld, x, y, z, 0.95F, 0.4F, 0.3F, 0.6F, false);
            }
            else if (type == EnumParticleTypesMP.CRYSTALLIZE_WATER_DRIP)
            {
                entityfx = new ParticleLiquidDrip(mc.theWorld, x, y, z, 0.6F, 0.2F, 0.8F, 0.6F, false);
            }
            else if (type == EnumParticleTypesMP.CRYSTALLIZE_LAVA_DRIP)
            {
                entityfx = new ParticleLiquidDrip(mc.theWorld, x, y, z, 0.6F, 0.2F, 0.8F, 1.0F, true);
            }
            else if (type == EnumParticleTypesMP.CRYSTALLIZE_LAVA)
            {
                entityfx = new ParticleLavaMC(mc.theWorld, x, y, z, "crystallize_lava");
            }
            else if (type == EnumParticleTypesMP.MC_SMOKE_LARGE)
            {
                mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0.0D, 0.0D, 0.0D);
            }
            else if (type == EnumParticleTypesMP.NUCLEAR_WASTE_DRIP)
            {
                entityfx = new ParticleLiquidDrip(mc.theWorld, x, y, z, 0.4F, 0.8F, 0.1F, 1.0F, true);
            }
            else if (type == EnumParticleTypesMP.PURIFY_WATER_DRIP)
            {
                entityfx = new ParticleLiquidDrip(mc.theWorld, x, y, z, 0.45F, 0.8F, 1.0F, 0.6F, false);
            }

            if (entityfx != null)
            {
                mc.effectRenderer.addEffect(entityfx);
            }
        }
    }
}