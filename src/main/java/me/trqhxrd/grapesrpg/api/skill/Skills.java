package me.trqhxrd.grapesrpg.api.skill;

import me.trqhxrd.grapesrpg.api.GrapesPlayer;
import me.trqhxrd.grapesrpg.api.attribute.Owneable;
import me.trqhxrd.grapesrpg.api.inventories.Menu;
import me.trqhxrd.grapesrpg.api.inventories.MenuHolder;
import me.trqhxrd.grapesrpg.api.utils.reflection.Reflection;
import me.trqhxrd.grapesrpg.game.inventories.SkillsMenu;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Every {@link GrapesPlayer} has one of these stored.
 * This object contains al skills of a player and is able to load, store and save the skills values.
 *
 * @author Trqhxrd
 */
public class Skills implements MenuHolder, Owneable<GrapesPlayer> {

    /**
     * This map stores all skills and their keys.
     */
    private final Map<String, Skill> skills = new HashMap<>();
    /**
     * This field stores the player, that owns this skill-object.
     */
    private final GrapesPlayer owner;
    /**
     * This field stores the skill-menu of the player.
     */
    private final Menu menu;

    /**
     * This constructor creates a new set of skills and loads their values, if their values are stored in the config.
     * If the values aren't stored, the values wil be set to the default.
     *
     * @param owner The owner of this skills-set.
     */
    public Skills(GrapesPlayer owner) {
        this.owner = owner;

        Reflection.executeIfClassExtends("me.trqhxrd.grapesrpg", Skill.class, c -> {
            try {
                Skill s = (Skill) c.getConstructor(Skills.class).newInstance(this);
                this.registerSkill(s.getKey(), s, true, false);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        Skills.load(this);
        Skills.save(this, true);
        this.menu = new SkillsMenu(this);
    }

    /**
     * This method loads a skill-set from the file.
     *
     * @param skills The object, that should be filled with the config-values.
     */
    public static void load(Skills skills) {
        skills.skills.keySet().forEach(name -> {
            if (skills.owner.getFile().contains("skill." + name)) {
                Skill s = skills.getSkill(name);
                s.setLevel(skills.owner.getFile().getInt("skill." + name + ".level"));
                s.setXP(skills.owner.getFile().getInt("skill." + name + ".xp"));
            }
        });
    }

    /**
     * This method copies the values from the skills-set to the file.
     *
     * @param skills     The set of skills, that should be saved.
     * @param saveToFile If set to true, the config will be saved. If set to false, the data is cached but not written to the file. This can be useful, if you want to save big amounts of data.
     */
    public static void save(Skills skills, boolean saveToFile) {
        skills.skills.keySet().forEach(name -> {
            Skill s = skills.getSkill(name);
            skills.owner.getFile().set("skill." + name + ".level", s.getLevel());
            skills.owner.getFile().set("skill." + name + ".xp", s.getXP());
        });
        if (saveToFile) skills.owner.getFile().save();
    }

    /**
     * This method adds a new skill to the map of skills.
     *
     * @param key    The key of the skill.
     * @param skill  The skill itself.
     * @param force  If this is set to true, any old skill with the same key will be overwritten.
     * @param reload If set to true, the value will automatically loaded from the file.
     * @return If a skill was already in the map, this will return false. Otherwise true.
     */
    public boolean registerSkill(String key, Skill skill, boolean force, boolean reload) {
        if (!skills.containsKey(key) || force) {
            this.skills.put(key, skill);
            if (reload) Skills.load(this);
            return true;
        } else return false;
    }

    /**
     * This method adds a skill to the skill map.
     * If the skill is already in the map, it won't be overwritten.
     * The skills data won't be loaded from the config.
     *
     * @param key   The key of the skill.
     * @param skill The skill itself.
     * @return This method returns false, if the skill already exists.
     */
    public boolean registerSkill(String key, Skill skill) {
        return this.registerSkill(key, skill, false, false);
    }

    /**
     * This method adds a skill to the skill map.
     * If the skill is already in the map, it won't be overwritten.
     * The skills data won't be loaded from the config.
     * The key is the return value of {@link Skill#getKey()}.
     *
     * @param skill The skill itself.
     * @return This method returns false, if the skill already exists.
     */
    public boolean registerSkill(Skill skill) {
        return this.registerSkill(skill.getKey(), skill, false, false);
    }

    /**
     * This method checks for every skill in this skill collection if it is able to level up.
     * If it is possible, it will be leveled up.
     */
    public void levelUp() {
        this.forEach((st, sk) -> sk.levelUp());
    }

    /**
     * This method get a certain skill from the collection of skills.
     *
     * @param key The key of the skill.
     * @return The skill, which corresponds to the key.
     */
    public Skill getSkill(String key) {
        return skills.get(key);
    }

    /**
     * This method returns the owner of this object.
     *
     * @return The owner of this object.
     */
    @Override
    public GrapesPlayer getOwner() {
        return owner;
    }

    public void save(boolean saveToFIle) {
        Skills.save(this, saveToFIle);
    }

    /**
     * This method returns the objects Menu.
     *
     * @return The objects menu.
     */
    @Override
    public Menu getMenu() {
        return menu;
    }

    public void forEach(BiConsumer<String, Skill> action) {
        this.skills.forEach(action);
    }
}
