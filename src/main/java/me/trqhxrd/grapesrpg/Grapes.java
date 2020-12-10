package me.trqhxrd.grapesrpg;

import org.bukkit.plugin.java.JavaPlugin;

public class Grapes extends JavaPlugin {

    private static Grapes grapes;

    public static Grapes getGrapes() {
        return grapes;
    }

    @Override
    public void onEnable() {
        grapes = this;
    }

    @Override
    public void onDisable() {

    }
}
