package me.trqhxrd.grapesrpg.api.utils;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("deprecation")
public class EnchantmentNames {

    private static final Map<String, Enchantment> ENCHANTMENTS = new HashMap<>();
    private static final String[] levels = new String[]{
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV"
    };

    static {
        ENCHANTMENTS.put("alldamage", Enchantment.DAMAGE_ALL);
        ENCHANTMENTS.put("sharpness", Enchantment.DAMAGE_ALL);
        ENCHANTMENTS.put("ardmg", Enchantment.DAMAGE_ARTHROPODS);
        ENCHANTMENTS.put("baneofarthropods", Enchantment.DAMAGE_ARTHROPODS);
        ENCHANTMENTS.put("undeaddamage", Enchantment.DAMAGE_UNDEAD);
        ENCHANTMENTS.put("smite", Enchantment.DAMAGE_UNDEAD);
        ENCHANTMENTS.put("digspeed", Enchantment.DIG_SPEED);
        ENCHANTMENTS.put("efficiency", Enchantment.DIG_SPEED);
        ENCHANTMENTS.put("durability", Enchantment.DURABILITY);
        ENCHANTMENTS.put("unbreaking", Enchantment.DURABILITY);
        ENCHANTMENTS.put("thorns", Enchantment.THORNS);
        ENCHANTMENTS.put("highcrit", Enchantment.THORNS);
        ENCHANTMENTS.put("fireaspect", Enchantment.FIRE_ASPECT);
        ENCHANTMENTS.put("fire", Enchantment.FIRE_ASPECT);
        ENCHANTMENTS.put("knockback", Enchantment.KNOCKBACK);
        ENCHANTMENTS.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
        ENCHANTMENTS.put("mobloot", Enchantment.LOOT_BONUS_MOBS);
        ENCHANTMENTS.put("looting", Enchantment.LOOT_BONUS_MOBS);
        ENCHANTMENTS.put("respiration", Enchantment.OXYGEN);
        ENCHANTMENTS.put("breath", Enchantment.OXYGEN);
        ENCHANTMENTS.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        ENCHANTMENTS.put("protect", Enchantment.PROTECTION_ENVIRONMENTAL);
        ENCHANTMENTS.put("blastprotect", Enchantment.PROTECTION_EXPLOSIONS);
        ENCHANTMENTS.put("fallprot", Enchantment.PROTECTION_FALL);
        ENCHANTMENTS.put("featherfall", Enchantment.PROTECTION_FALL);
        ENCHANTMENTS.put("fireprotect", Enchantment.PROTECTION_FIRE);
        ENCHANTMENTS.put("fireprot", Enchantment.PROTECTION_FIRE);
        ENCHANTMENTS.put("projectileprotection", Enchantment.PROTECTION_PROJECTILE);
        ENCHANTMENTS.put("projprot", Enchantment.PROTECTION_PROJECTILE);
        ENCHANTMENTS.put("silktouch", Enchantment.SILK_TOUCH);
        ENCHANTMENTS.put("waterworker", Enchantment.WATER_WORKER);
        ENCHANTMENTS.put("aquaaffinity", Enchantment.WATER_WORKER);
        ENCHANTMENTS.put("flame", Enchantment.ARROW_FIRE);
        ENCHANTMENTS.put("flamearrow", Enchantment.ARROW_FIRE);
        ENCHANTMENTS.put("arrowdamage", Enchantment.ARROW_DAMAGE);
        ENCHANTMENTS.put("power", Enchantment.ARROW_DAMAGE);
        ENCHANTMENTS.put("arrowknockback", Enchantment.ARROW_KNOCKBACK);
        ENCHANTMENTS.put("punch", Enchantment.ARROW_KNOCKBACK);
        ENCHANTMENTS.put("infarrows", Enchantment.ARROW_INFINITE);
        ENCHANTMENTS.put("infinity", Enchantment.ARROW_INFINITE);
        ENCHANTMENTS.put("luck", Enchantment.LUCK);
        ENCHANTMENTS.put("lure", Enchantment.LURE);
        ENCHANTMENTS.put("depthstrider", Enchantment.DEPTH_STRIDER);
        ENCHANTMENTS.put("frostwalker", Enchantment.FROST_WALKER);
        ENCHANTMENTS.put("mending", Enchantment.MENDING);
        ENCHANTMENTS.put("bindingcurse", Enchantment.BINDING_CURSE);
        ENCHANTMENTS.put("vanishingcurse", Enchantment.VANISHING_CURSE);
        ENCHANTMENTS.put("sweepingedge", Enchantment.SWEEPING_EDGE);
    }

    public static Enchantment getByName(String name) {
        Enchantment enchantment = Enchantment.getByName(name.toUpperCase(Locale.ENGLISH));
        if (enchantment == null)
            enchantment = ENCHANTMENTS.get(name.toLowerCase(Locale.ENGLISH));
        return enchantment;
    }

    public static String getName(Enchantment enchantment) {
        for (String s : ENCHANTMENTS.keySet()) {
            if (ENCHANTMENTS.get(s).equals(enchantment)) {
                String edit = s.toLowerCase();
                char[] chars = edit.toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                return new String(chars);
            }
        }
        return "NOT FOUND";
    }

    public static String getLevel(int i) {
        if (i <= 25) return levels[i - 1];
        return String.valueOf(i);
    }
}
