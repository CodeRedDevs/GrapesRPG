package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.trqhxrd.grapesrpg.api.utils.Utils;
import net.citizensnpcs.trait.SkinTrait;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class represents a skin, that can be applied to an NPC.
 *
 * @author Trqhxrd
 */
public class Skin {

    /**
     * This field contains cached names and their UUIDs.
     */
    private static final Map<String, UUID> cachedNames = new HashMap<>();
    /**
     * This field contains cached UUIDs and their names.
     */
    private static final Map<UUID, String> cachedUUIDs = new HashMap<>();
    /**
     * This field contains UUIDs and their Skins.
     */
    private static final Map<UUID, Skin> cachedSkins = new HashMap<>();

    /**
     * The id of the skin, in which this field is included.
     */
    private final UUID id;
    /**
     * The value of the skin.
     */
    private final String value;
    /**
     * The signature of the skin.
     */
    private final String signature;

    /**
     * This constructor creates a new skin.
     *
     * @param id        The id of the new skin.
     * @param value     The value of the new skin.
     * @param signature The signature of the new skin.
     */
    public Skin(UUID id, String value, String signature) {
        this.id = id;
        this.value = value;
        this.signature = signature;

        cachedSkins.put(this.id, this);
    }

    /**
     * This method fetches and downloads the skin of the player with the name given and returns it.
     * If the player does not exist, this method returns null.
     *
     * @param name The name of the player, from which you want to steal the skin.
     * @return The skin of the player, of which you entered the name.
     */
    public static Skin from(String name) {
        try {
            if (!cachedNames.containsKey(name)) {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();
                cachedNames.put(name, Utils.formatStringToUUID(uuid));
            }

            return Skin.from(cachedNames.get(name));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * This method fetches and downloads the skin of the player with the uuid given and returns it.
     * If the player does not exist, this method returns null.
     *
     * @param uuid The uuid of the player, from which you want to steal the skin.
     * @return The skin of the player, of which you entered the name.
     */
    public static Skin from(UUID uuid) {
        if (cachedSkins.containsKey(uuid)) return cachedSkins.get(uuid);

        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString() + "?unsigned=false");
            InputStreamReader reader = new InputStreamReader(url.openStream());

            JsonObject root = new JsonParser().parse(reader).getAsJsonObject();
            JsonObject texture = root.get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String value = texture.get("value").getAsString();
            String signature = texture.get("signature").getAsString();

            if (!cachedUUIDs.containsKey(uuid)) {
                String name = root.get("name").getAsString();
                cachedUUIDs.put(uuid, name);
            }

            return new Skin(uuid, value, signature);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * This method applies the skin given to the NPC.
     *
     * @param npc The npc, who should get this skin applied.
     */
    public void apply(GrapesNPC npc) {
        npc.getWrappedObject().getOrAddTrait(SkinTrait.class).setSkinPersistent(this.id.toString(), signature, value);
    }

    /**
     * Getter for the skins value.
     *
     * @return The skins value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for the skins signature.
     *
     * @return The skins signature.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Getter for the skins UUID.
     *
     * @return The skins UUID.
     */
    public UUID getID() {
        return id;
    }
}
