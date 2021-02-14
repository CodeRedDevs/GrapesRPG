package me.trqhxrd.grapesrpg.game.objects.item.armor.crop;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CropArmorAbility implements Listener {

    private static final Set<Material> cropMaterials = new HashSet<>();

    public CropArmorAbility() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
        cropMaterials.addAll(Arrays.asList(
                Material.WHEAT_SEEDS,
                Material.WHEAT,
                Material.CARROT,
                Material.CARROTS,
                Material.POTATO,
                Material.POTATOES,
                Material.BEETROOT,
                Material.BEETROOTS,
                Material.BEETROOT_SEEDS,
                Material.NETHER_WART));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (GrapesItem.fromItemStack(inv.getBoots()) != null && GrapesItem.fromItemStack(inv.getBoots()).getId() == new CropBoots().getId()) {
            if (GrapesItem.fromItemStack(inv.getLeggings()) != null && GrapesItem.fromItemStack(inv.getLeggings()).getId() == new CropLeggings().getId()) {
                if (GrapesItem.fromItemStack(inv.getChestplate()) != null && GrapesItem.fromItemStack(inv.getChestplate()).getId() == new CropChestplate().getId()) {
                    if (GrapesItem.fromItemStack(inv.getHelmet()) != null && GrapesItem.fromItemStack(inv.getHelmet()).getId() == new CropHelmet().getId()) {
                        Block b = e.getBlock();
                        BlockData d = b.getBlockData();
                        if (cropMaterials.contains(b.getType())) {
                            if (d instanceof Ageable) {
                                Ageable a = ((Ageable) d);
                                if (a.getAge() == a.getMaximumAge()) {
                                    e.setDropItems(false);
                                    Location loc = b.getLocation();
                                    World w = loc.getWorld();
                                    switch (b.getType()) {
                                        case WHEAT:
                                        case WHEAT_SEEDS:
                                            w.dropItem(loc, new ItemStack(Material.WHEAT, ThreadLocalRandom.current().nextInt(3, 7)));
                                            w.dropItem(loc, new ItemStack(Material.WHEAT_SEEDS, ThreadLocalRandom.current().nextInt(1, 4)));
                                            break;
                                        case CARROTS:
                                            w.dropItem(loc, new ItemStack(Material.CARROT, ThreadLocalRandom.current().nextInt(3, 7)));
                                            break;
                                        case POTATOES:
                                            w.dropItem(loc, new ItemStack(Material.POTATO, ThreadLocalRandom.current().nextInt(3, 7)));
                                            break;
                                        case BEETROOTS:
                                            w.dropItem(loc, new ItemStack(Material.BEETROOT, ThreadLocalRandom.current().nextInt(3, 7)));
                                            w.dropItem(loc, new ItemStack(Material.BEETROOT_SEEDS, ThreadLocalRandom.current().nextInt(1, 4)));
                                            break;
                                        case NETHER_WART:
                                            w.dropItem(loc, new ItemStack(Material.NETHER_WART, ThreadLocalRandom.current().nextInt(3, 7)));
                                            break;
                                        default:
                                            w.dropItem(loc, new ItemStack(b.getType(), ThreadLocalRandom.current().nextInt(3, 7)));
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
