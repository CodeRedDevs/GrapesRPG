package me.trqhxrd.grapesrpg.game.mechanics.alchemy;

import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlock;
import me.trqhxrd.grapesrpg.api.objects.block.GrapesBlockType;
import me.trqhxrd.grapesrpg.api.utils.clock.Clock;
import me.trqhxrd.grapesrpg.api.utils.clock.ClockTask;
import me.trqhxrd.grapesrpg.game.objects.block.CrucibleBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.UUID;

/**
 * This task will be executed 20 times per second.
 * It is required, if you want to use the crucibles.
 * @author Trqhxrd
 */
public class AlchemyTask implements ClockTask {
    /**
     * The method, that will be executed by the clock.
     *
     * @param uuid     The task id of this task.
     * @param operator The clock, which executes this method.
     */
    @Override
    public void execute(UUID uuid, Clock operator) {

        //SPIN ITEMS
        GrapesBlock.forEach((loc, b) -> {
            if (b.getType() == GrapesBlockType.CRUCIBLE) {
                ((CrucibleBlock) b.getState()).updateItems(loc);
            }
        });

        if (operator.getIteration() % 5 == 0) {
            Bukkit.getWorlds().forEach(w -> {
                w.getEntities().forEach(e -> {
                    if (e instanceof Item) {
                        Item i = (Item) e;
                        Location loc = i.getLocation();
                        GrapesBlock b = GrapesBlock.getBlock(loc,false);
                        if (b.getType() == GrapesBlockType.CRUCIBLE) {
                            CrucibleBlock state = (CrucibleBlock) b.getState();

                            boolean addItem = true;
                            for (AlchemyRecipe recipe : AlchemyRecipe.getRecipes()) {
                                if (recipe.checkItems(state.getItems())) {
                                    if (recipe.getWaterLevelRequired() <= state.getWaterLevel()) {
                                        if (recipe.getCatalyst().isSimilar(i.getItemStack())) {
                                            state.setWaterLevel(state.getWaterLevel() - recipe.getWaterLevelRequired());

                                            ItemStack is = i.getItemStack();
                                            if (is.getAmount() > 1) {
                                                is.setAmount(is.getAmount() - 1);
                                                i.setItemStack(is);
                                            } else i.remove();

                                            recipe.removeItems(state);
                                            Location resultLocation = b.getLocation().clone().add(.5, 1.5, .5);

                                            // TODO: 15.03.2021 Add particle effect and sounds!

                                            Item result = Objects.requireNonNull(resultLocation.getWorld()).dropItem(resultLocation, recipe.getResult());
                                            result.setGravity(false);
                                            result.setVelocity(new Vector(0., 0., 0.));
                                            result.setPickupDelay(0);

                                            addItem = false;
                                            b.update();
                                            break;
                                        }
                                    }
                                }
                            }
                            if (addItem && state.getWaterLevel() > 0) {
                                state.addItem(i);
                                b.update();
                            }
                        }
                    }
                });
            });
        }
    }
}
