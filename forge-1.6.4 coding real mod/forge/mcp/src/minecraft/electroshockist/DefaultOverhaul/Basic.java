package electroshockist.DefaultOverhaul;
 
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStep;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import electroshockist.DefaultOverhaul.blocks.StepDirt;

//Gets mod name, ID, and version from tutorial.basic.BasicInfo
@Mod(modid=BasicInfo.NAME, name=BasicInfo.ID, version=BasicInfo.V)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Basic {
	
	//Sets Generic Leaf material, data value, hardness, sound, unlocalized name, creative tab, texture, opacity and light level
	public static final BlockHalfSlab DirtSingleSlab = (BlockHalfSlab) new StepDirt(500, false, Material.ground)
			.setHardness(0.5f)
			.setStepSound(Block.soundGravelFootstep)
			.setResistance(10.0F)
			.setUnlocalizedName("DirtSlabUNLOC")
			.setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("dirt");
	
	public static final BlockHalfSlab DirtDoubleSlab = (BlockHalfSlab) new StepDirt(501, true, Material.ground)
			.setHardness(0.5f)
			.setStepSound(Block.soundGravelFootstep)
			.setResistance(10.0F)
			.setUnlocalizedName("DirtSlabUNLOC")
			.setCreativeTab(CreativeTabs.tabBlock)
			.setTextureName("dirt");
 
        // The instance of your mod that Forge uses.
        @Instance("Basic")
        public static Basic instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide=BasicInfo.CLIENTPROXY + "ClientProxy", serverSide=BasicInfo.COMMONPROXY + "CommonProxy")
        public static CommonProxy proxy;
       
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @EventHandler
        public void load(FMLInitializationEvent event) { //Use this one for recipes
        	   
        	GameRegistry.registerBlock(DirtSingleSlab,"DirtSingleSlabUNLOC");
               LanguageRegistry.addName(DirtSingleSlab, "Dirt Slab");
               MinecraftForge.setBlockHarvestLevel(DirtSingleSlab, "shovel", 0);
               
            GameRegistry.registerBlock(DirtDoubleSlab,"DirtDoubleSlabUNLOC");
               LanguageRegistry.addName(DirtDoubleSlab, "Dirt Double Slab");
               MinecraftForge.setBlockHarvestLevel(DirtDoubleSlab, "shovel", 0);  
        }
       
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
    }
       
}