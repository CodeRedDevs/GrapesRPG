package me.trqhxrd.grapesrpg.api.objects.entity.npc;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.trqhxrd.grapesrpg.Grapes;
import net.minecraft.server.v1_16_R3.EntityPlayer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Skin {

    public static final Skin DEFAULT_SKIN = Skin.getSkin("Notch");
    public static final Skin TRQHXRD = Skin.getSkin("Trqhxrd");
    public static final Skin TABBYPLAYZ = Skin.getSkin("Tabbyplayz");

    private final String value;
    private final String signature;

    public Skin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

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

    public static void apply(EntityPlayer npc, Skin skin) {
        skin.apply(npc);
    }

    public void apply(EntityPlayer npc) {
        GameProfile profile = npc.getProfile();
        profile.getProperties().put("textures", new Property("textures", this.value, this.signature));
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
