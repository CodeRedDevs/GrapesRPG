package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import com.google.gson.JsonObject;
import com.mojang.authlib.properties.Property;
import me.trqhxrd.grapesrpg.Grapes;
import net.minecraft.server.v1_16_R3.EntityPlayer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * This class stores skin data for NPCs.
 *
 * @author Trqhxrd
 */
public class Skin {

    /**
     * This constant contains the default skin, if no skin can be loaded.
     */
    public static final Skin DEFAULT_SKIN = Skin.getSkin("Notch");

    /**
     * This constant contains the data about the skin of Trqhxrd.
     */
    public static final Skin TRQHXRD = Skin.getSkin("Trqhxrd");

    /**
     * This constant contains the data about the skin of Tabbyplayz.
     */
    public static final Skin TABBYPLAYZ = Skin.getSkin("Tabbyplayz");

    /**
     * Every Skin is stored in a value and a signature.
     * This field contains the value of the skin.
     */
    private final String value;

    /**
     * Every Skin is stored in a value and a signature.
     * This field contains the signature of the skin.
     */
    private final String signature;

    /**
     * This constructor creates a new Skin-Object, with the value and signature, you gave in the parameters.
     *
     * @param value     The value of the skin.
     * @param signature The signature of the skin.
     */
    public Skin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    /**
     * This method returns the Skin of a certain Player.
     *
     * @param name The name of the player, from which you want to get the skin.
     * @return The Skin of the player, store in a skin-object.
     */
    public static Skin getSkin(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = Grapes.GSON.fromJson(reader, JsonObject.class).get("id").getAsString();

            URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader1 = new InputStreamReader(url1.openStream());
            JsonObject property = Grapes.GSON.fromJson(reader1, JsonObject.class).get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new Skin(value, signature);
        } catch (IOException e) {
            e.printStackTrace();
            return DEFAULT_SKIN;
        }
    }

    /**
     * This method applies a skin to the given NPC.
     *
     * @param npc  The NPC, who is supposed to get the skin.
     * @param skin The skin for the NPC.
     */
    public static void apply(EntityPlayer npc, Skin skin) {
        skin.apply(npc);
    }

    /**
     * This method applies the skin to the NPC, you gave in the parameters.
     *
     * @param npc The NPC, who should get its skin changed.
     */
    public void apply(EntityPlayer npc) {
        npc.getProfile().getProperties().put("textures", new Property("textures", this.value, this.signature));
    }

    /**
     * Getter for the Skins value.
     *
     * @return The Skins value.
     */
    public String getValue() {
        return value;
    }

    /**
     * This method returns the skins signature.
     *
     * @return The skins signature.
     */
    public String getSignature() {
        return signature;
    }
}
