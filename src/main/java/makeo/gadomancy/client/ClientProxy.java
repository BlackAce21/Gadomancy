package makeo.gadomancy.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import makeo.gadomancy.client.events.ClientTickHandler;
import makeo.gadomancy.client.events.RenderEventHandler;
import makeo.gadomancy.client.events.ResourceReloadListener;
import makeo.gadomancy.client.gui.InfusionClawGui;
import makeo.gadomancy.client.renderers.block.BlockExtendedNodeJarRenderer;
import makeo.gadomancy.client.renderers.block.RenderBlockStoneMachine;
import makeo.gadomancy.client.renderers.block.RenderBlockTransparent;
import makeo.gadomancy.client.renderers.entity.RenderAdditionalGolemBase;
import makeo.gadomancy.client.renderers.item.ItemCreativeNodeRenderer;
import makeo.gadomancy.client.renderers.item.ItemExNodeRenderer;
import makeo.gadomancy.client.renderers.item.ItemJarExtendedNodeRenderer;
import makeo.gadomancy.client.renderers.item.ItemRenderFamiliar;
import makeo.gadomancy.client.renderers.item.ItemRenderRemoteJar;
import makeo.gadomancy.client.renderers.item.ItemRenderStoneMachine;
import makeo.gadomancy.client.renderers.item.ItemRenderTileEntity;
import makeo.gadomancy.client.renderers.tile.*;
import makeo.gadomancy.common.CommonProxy;
import makeo.gadomancy.common.blocks.tiles.TileAdditionalEldritchPortal;
import makeo.gadomancy.common.blocks.tiles.TileArcaneDropper;
import makeo.gadomancy.common.blocks.tiles.TileExtendedNode;
import makeo.gadomancy.common.blocks.tiles.TileExtendedNodeJar;
import makeo.gadomancy.common.blocks.tiles.TileInfusionClaw;
import makeo.gadomancy.common.blocks.tiles.TileManipulationFocus;
import makeo.gadomancy.common.blocks.tiles.TileManipulatorPillar;
import makeo.gadomancy.common.blocks.tiles.TileNodeManipulator;
import makeo.gadomancy.common.blocks.tiles.TileRemoteJar;
import makeo.gadomancy.common.blocks.tiles.TileStickyJar;
import makeo.gadomancy.common.registration.RegisteredBlocks;
import makeo.gadomancy.common.registration.RegisteredItems;
import makeo.gadomancy.common.utils.Injector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.client.gui.GuiGolem;
import thaumcraft.client.renderers.entity.RenderGolemBase;
import thaumcraft.client.renderers.tile.TileEldritchPortalRenderer;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.tiles.TileEldritchAltar;
import thaumcraft.common.tiles.TileEldritchCap;
import thaumcraft.common.tiles.TileEldritchObelisk;

import java.util.List;

/**
 * This class is part of the Gadomancy Mod
 * Gadomancy is Open Source and distributed under the
 * GNU LESSER GENERAL PUBLIC LICENSE
 * for more read the LICENSE file
 *
 * Created by makeo @ 29.11.2014 14:15
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInitalize() {
        super.preInitalize();
    }

    @Override
    public void initalize() {
        super.initalize();

        injectGolemTextures();

        //Tiles
        ClientRegistry.bindTileEntitySpecialRenderer(TileStickyJar.class, new RenderTileStickyJar());

        RenderTileRemoteJar renderTileRemoteJar = new RenderTileRemoteJar();
        ClientRegistry.bindTileEntitySpecialRenderer(TileRemoteJar.class, renderTileRemoteJar);

        RenderTileArcaneDropper renderTileArcaneDropper = new RenderTileArcaneDropper();
        ClientRegistry.bindTileEntitySpecialRenderer(TileArcaneDropper.class, renderTileArcaneDropper);

        RenderTileInfusionClaw renderTileInfusionClaw = new RenderTileInfusionClaw();
        ClientRegistry.bindTileEntitySpecialRenderer(TileInfusionClaw.class, renderTileInfusionClaw);

        RenderTileExtendedNode nodeRenderer = new RenderTileExtendedNode();
        ClientRegistry.bindTileEntitySpecialRenderer(TileExtendedNode.class, nodeRenderer);

        RenderTileExtendedNodeJar nodeJarRenderer = new RenderTileExtendedNodeJar();
        ClientRegistry.bindTileEntitySpecialRenderer(TileExtendedNodeJar.class, nodeJarRenderer);

        ClientRegistry.bindTileEntitySpecialRenderer(TileNodeManipulator.class, new RenderTileNodeManipulator());

        ClientRegistry.bindTileEntitySpecialRenderer(TileManipulatorPillar.class, new RenderTileManipulatorPillar());
        RenderTileManipulationFocus renderTileManipulationFocus = new RenderTileManipulationFocus();
        ClientRegistry.bindTileEntitySpecialRenderer(TileManipulationFocus.class, renderTileManipulationFocus);

        ClientRegistry.bindTileEntitySpecialRenderer(TileAdditionalEldritchPortal.class, new TileEldritchPortalRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEldritchObelisk.class, new RenderTileObelisk());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEldritchAltar.class, new RenderTileCapEldritch("textures/models/obelisk_cap_altar.png"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEldritchCap.class, new RenderTileCapEldritch("textures/models/obelisk_cap.png"));

        //Items
        TileArcaneDropper fakeTile = new TileArcaneDropper();
        fakeTile.blockMetadata = 8 | ForgeDirection.SOUTH.ordinal();
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegisteredBlocks.blockArcaneDropper), new ItemRenderTileEntity(renderTileArcaneDropper, fakeTile));

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegisteredBlocks.blockInfusionClaw), new ItemRenderTileEntity(renderTileInfusionClaw, new TileInfusionClaw()));

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ConfigBlocks.blockAiry), new ItemExNodeRenderer());

        MinecraftForgeClient.registerItemRenderer(RegisteredItems.itemExtendedNodeJar, new ItemJarExtendedNodeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegisteredBlocks.blockRemoteJar), new ItemRenderRemoteJar(renderTileRemoteJar));

        ItemRenderStoneMachine itemRenderStoneMachine = new ItemRenderStoneMachine();
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegisteredBlocks.blockStoneMachine), itemRenderStoneMachine);
        itemRenderStoneMachine.registerRenderer(0, new TileManipulationFocus(), renderTileManipulationFocus);
        TileManipulationFocus tileManipulationFocus = new TileManipulationFocus();
        tileManipulationFocus.blockMetadata = 3;
        itemRenderStoneMachine.registerRenderer(3, tileManipulationFocus, renderTileManipulationFocus);

        MinecraftForgeClient.registerItemRenderer(RegisteredItems.itemFamiliar, new ItemRenderFamiliar());
        MinecraftForgeClient.registerItemRenderer(RegisteredItems.itemCreativeNode, new ItemCreativeNodeRenderer());

        //Blocks
        RegisteredBlocks.rendererTransparentBlock = registerBlockRenderer(new RenderBlockTransparent());
        RegisteredBlocks.rendererExtendedNodeJarBlock = registerBlockRenderer(new BlockExtendedNodeJarRenderer());
        RegisteredBlocks.rendererBlockStoneMachine = registerBlockRenderer(new RenderBlockStoneMachine());
    }

    @Override
    public void postInitalize() {
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        if(manager instanceof SimpleReloadableResourceManager) {
            SimpleReloadableResourceManager rm = (SimpleReloadableResourceManager) manager;
            rm.registerReloadListener(new ResourceReloadListener());
        }

        MinecraftForge.EVENT_BUS.register(new RenderEventHandler());

        FMLCommonHandler.instance().bus().register(new ClientTickHandler());

        super.postInitalize();
    }

    public int registerBlockRenderer(ISimpleBlockRenderingHandler renderer) {
        int nextId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(nextId, renderer);
        return nextId;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new GuiGolem(player, (EntityGolemBase) world.getEntityByID(x));
                //return new AdditionalGolemGui(player, (EntityGolemBase)world.getEntityByID(x));
            case 1:
                return new InfusionClawGui(player.inventory, (IInventory) world.getTileEntity(x, y, z));
        }
        return null;
    }

    public static void injectGolemTextures() {
        RenderGolemBase render = unregisterRenderer(EntityGolemBase.class, RenderGolemBase.class);
        if(render != null) {
            RenderingRegistry.registerEntityRenderingHandler(EntityGolemBase.class, new RenderAdditionalGolemBase());
        }
    }

    public static <T extends Render> T unregisterRenderer(Class<? extends Entity> entityClass, Class<T> renderClass) {
        Injector registry = new Injector(RenderingRegistry.instance());
        List entityRenderers = registry.getField("entityRenderers");

        if(entityRenderers == null) {
            FMLLog.severe("Failed to get entityRenderers field in RenderingRegistry!");
            return null;
        }

        for(int i = 0; i < entityRenderers.size(); i++) {
            Injector pair = new Injector(entityRenderers.get(i));

            Class<? extends Entity> target = pair.getField("target");

            if(entityClass.equals(target)) {
                Render render = pair.getField("renderer");
                if(renderClass.isInstance(render)) {
                    entityRenderers.remove(i);
                    return (T)render;
                }
            }
        }
        return null;
    }
}
