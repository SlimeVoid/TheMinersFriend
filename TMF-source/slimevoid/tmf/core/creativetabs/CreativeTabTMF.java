package slimevoid.tmf.core.creativetabs;

import slimevoid.tmf.core.TMFCore;
import slimevoid.tmf.core.lib.ReferenceLib;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;


public class CreativeTabTMF extends CreativeTabs {
	
	public static CreativeTabs tabTMF = new CreativeTabTMF(ReferenceLib.MOD_ID);
	
	CreativeTabTMF(String par2Str)
    {
        super(par2Str);
    }

    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return TMFCore.mineralAcxiumId;
    }
}
