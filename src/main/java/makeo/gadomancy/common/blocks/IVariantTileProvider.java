package makeo.gadomancy.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * HellFirePvP@Admin
 * Date: 21.04.2016 / 22:38
 * on Gadomancy_1_8
 * IVariantTileProvider
 */
public interface IVariantTileProvider {

    public TileEntity provideTileEntity(World world, IBlockState state);

}
