package me.yoshiro09.simpleplayerpee.nbtwrapper;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.yoshiro09.simpleplayerpee.Main;

public class NBT_PersistentDataHolder {
	
	public ItemStack setNBTTag(ItemStack item, String name) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), name);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "SimplePee");
		item.setItemMeta(itemMeta);
		return item;
	}

	public Boolean hasNBTTag(ItemStack item, String name) {
		boolean hasNBT = false;
		
		NamespacedKey key = new NamespacedKey(Main.getInstance(), name);
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer container = itemMeta.getPersistentDataContainer();

		if(container.has(key , PersistentDataType.STRING)) {
			hasNBT = true;
		}
		
		return hasNBT;
	}

	
}
