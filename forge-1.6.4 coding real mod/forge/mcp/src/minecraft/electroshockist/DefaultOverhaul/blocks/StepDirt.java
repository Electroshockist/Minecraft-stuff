//Block Declaration Class (for the Generic Dirt block)

package electroshockist.DefaultOverhaul.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

//states that this class(the GenericBlock class) is an extension of the  class
public class StepDirt extends BlockHalfSlab {

	public StepDirt(int id, boolean DoubleSlab, Material material) {
		super(id, DoubleSlab, material);
		
	
	}
	
	 public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	    {
		 boolean flag = (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0;

         if (flag)
         {
             this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
         }
         else
         {
             this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
         }
	    }
	@Override
	public String getFullSlabName(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}


	
	