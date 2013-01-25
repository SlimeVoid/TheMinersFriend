package slimevoid.tmf.minerals.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class ItemMineralDust extends ItemMineral {

	public ItemMineralDust(int id) {
		super(id);
		this.setPotionEffect(PotionHelper.spiderEyeEffect);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	} 

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (player.canEat(true)) {
			player.setItemInUse(item, this.getMaxItemUseDuration(item));
		}
		
		return item;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public ItemStack onFoodEaten(ItemStack stack, World world, EntityPlayer player){
		--stack.stackSize;
		player.getFoodStats().addStats(0,0);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		
        float strength = 1.0F;
        world.createExplosion(player, player.posX, player.posY, player.posZ, strength, true);
		player.setVelocity(0, 5, 0);
		return stack;
	}
}
