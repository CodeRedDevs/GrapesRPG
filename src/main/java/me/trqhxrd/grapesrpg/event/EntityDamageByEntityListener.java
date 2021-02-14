package me.trqhxrd.grapesrpg.event;

import me.trqhxrd.grapesrpg.Grapes;
import me.trqhxrd.grapesrpg.api.objects.item.GrapesItem;
import me.trqhxrd.grapesrpg.api.objects.item.ItemType;
import me.trqhxrd.grapesrpg.api.utils.group.Group3;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This listener handles the damage-system.
 *
 * @author Trqhxrd
 */
public class EntityDamageByEntityListener implements Listener {

    /**
     * This field stores data about players, who just got hit.
     * This is used to disable healing during PVP.
     */
    private static final Set<UUID> waitForRegen = new HashSet<>();

    /**
     * This constructor registers the listener.
     */
    public EntityDamageByEntityListener() {
        Bukkit.getPluginManager().registerEvents(this, Grapes.getGrapes());
    }

    /**
     * Getter for the set of UUIDs, which can't regenerate.
     * @return The set of UUIDs.
     */
    public static Set<UUID> getWaitForRegen() {
        return waitForRegen;
    }

    /**
     * The Listener method.
     *
     * @param e This is an EntityDamageByEntityEvent.
     */
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof LivingEntity) {
            EntityEquipment attackEquipment = ((LivingEntity) e.getDamager()).getEquipment();
            EntityEquipment armorEquipment = ((LivingEntity) e.getEntity()).getEquipment();

            Group3<Integer, Integer, Integer> defenceValues = new Group3<>(0, 0, 0);
            Group3<Integer, Integer, Integer> damageValues = new Group3<>(1, 0, 0);
            Group3<Double, Double, Double> appliedDamage = new Group3<>(0., 0., 0.);
            if (armorEquipment != null) {
                ItemStack[] armorContents = armorEquipment.getArmorContents();
                for (ItemStack is : armorContents) {
                    if (is != null) {
                        GrapesItem part = GrapesItem.fromItemStack(is);
                        if (part != null && part.getType() == ItemType.ARMOR) {
                            defenceValues.setX(defenceValues.getX() + part.getPhysicalStats());
                            defenceValues.setY(defenceValues.getY() + part.getMagicalStats());
                            defenceValues.setZ(defenceValues.getZ() + part.getVoidStats());
                        }
                    }
                }
                if (defenceValues.getX() > 975) defenceValues.setX(975);
                if (defenceValues.getY() > 975) defenceValues.setY(975);
                if (defenceValues.getZ() > 975) defenceValues.setZ(975);
            }

            if (attackEquipment != null) {
                GrapesItem weapon = GrapesItem.fromItemStack(attackEquipment.getItem(EquipmentSlot.HAND));
                if (weapon != null && weapon.getType() == ItemType.MELEE) damageValues = weapon.getStats();
            }

            appliedDamage.setX(((double) damageValues.getX()) / 1000. * (1000. - ((double) defenceValues.getX())));
            appliedDamage.setY(((double) damageValues.getY()) / 1000. * (1000. - ((double) defenceValues.getY())));
            appliedDamage.setZ(((double) damageValues.getZ()) / 1000. * (1000. - ((double) defenceValues.getZ())));
            double damage = appliedDamage.getX() + appliedDamage.getY() + appliedDamage.getZ();

            // Critical hits
            if (!e.getDamager().isOnGround()) damage *= 1.5;

            LivingEntity entity = ((LivingEntity) e.getEntity());
            e.setDamage(0);
            e.setCancelled(entity.getHealth() - damage <= 0);
            entity.setHealth(Math.max(0, entity.getHealth() - damage));

            if (e.getEntity() instanceof Player) waitForRegen.add(e.getEntity().getUniqueId());
        }
    }
}
