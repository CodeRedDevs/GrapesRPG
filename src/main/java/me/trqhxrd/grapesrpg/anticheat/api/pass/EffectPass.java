package me.trqhxrd.grapesrpg.anticheat.api.pass;

import me.trqhxrd.grapesrpg.anticheat.api.User;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This is a pass, which will let you bypass a certain test, if you have a special potion-effect.
 * @author Trqhxrd
 */
public class EffectPass extends Pass {

    /**
     * The PotionEffect, which allows you to bypass the test.
     */
    private final PotionEffectType type;

    /**
     * This constructor creates a new pass.
     * @param id The id of the test, that the player can bypass.
     * @param owner The player, who is allowed to bypass the test.
     * @param type The type of effect, which allows the player to bypass a test.
     */
    public EffectPass(String id, User owner, PotionEffectType type) {
        super(id, owner);
        this.type = type;
    }

    /**
     * This method returns true, if the player has the PotionEffect, that was specified in the constructor.
     * @return true, if the player has the potion-effect.
     */
    @Override
    public boolean bypass() {
        for (PotionEffect effect : this.owner.getWrappedObject().getActivePotionEffects()) if (effect.getType().equals(this.type)) {
            return true;
        }
       return false;
    }
}
