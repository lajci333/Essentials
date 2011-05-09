package com.earth2me.essentials.commands;

import org.bukkit.Server;
import com.earth2me.essentials.ItemDb;
import com.earth2me.essentials.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class Commanditem extends EssentialsCommand
{
	public Commanditem()
	{
		super("item");
	}

	@Override
	public void run(Server server, User user, String commandLabel, String[] args) throws Exception
	{
		if (args.length < 1)
		{
			throw new NotEnoughArgumentsException();
		}
		ItemStack stack = ItemDb.get(args[0]);

		String itemname = stack.getType().toString().toLowerCase().replace("_", "");
		if (ess.getSettings().permissionBasedItemSpawn()
			? (!user.isAuthorized("essentials.itemspawn.item-all")
			   && !user.isAuthorized("essentials.itemspawn.item-" + itemname)
			   && !user.isAuthorized("essentials.itemspawn.item-" + stack.getTypeId()))
			: (!user.isAuthorized("essentials.itemspawn.exempt")
			   && !user.canSpawnItem(stack.getTypeId())))
		{
			user.sendMessage(ChatColor.RED + "You are not allowed to spawn the item " + itemname);
			return;
		}

		if (args.length > 1 && Integer.parseInt(args[1]) > 0)
		{
			stack.setAmount(Integer.parseInt(args[1]));
		}

		if (stack.getType() == Material.AIR)
		{
			user.sendMessage(ChatColor.RED + "You can't get air.");
			return;
		}

		String itemName = stack.getType().name().toLowerCase().replace('_', ' ');
		charge(user);
		user.sendMessage("§7Giving " + stack.getAmount() + " of " + itemName + " to " + user.getDisplayName() + ".");
		user.getInventory().addItem(stack);
		user.updateInventory();
	}
}
