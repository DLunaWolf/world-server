package com.rs.game.content.minigames.crucible;

import com.rs.game.content.Effect;
import com.rs.game.content.Potions;
import com.rs.game.content.minigames.MinigameUtil;
import com.rs.game.model.entity.Entity;
import com.rs.game.model.entity.player.Controller;
import com.rs.game.model.entity.player.Player;
import com.rs.game.model.object.GameObject;
import com.rs.game.tasks.WorldTasks;
import com.rs.lib.game.Animation;
import com.rs.lib.game.Item;
import com.rs.lib.game.Tile;
import com.rs.lib.util.MapUtils;
import com.rs.lib.util.Utils;

public class CrucibleController extends Controller {
    private static final MapUtils.Area[] SAFE_ZONES = new MapUtils.Area[] {
            MapUtils.getArea(3314,6136,3322,6149),
            MapUtils.getArea(3258,6196,3269,6203),
            MapUtils.getArea(3204,6139,3212,6149),
            MapUtils.getArea(3255,6084,3266,6093)
    };

    private transient boolean wasInArea;
    private boolean dangerous;

    public CrucibleController(boolean dangerous) {
        this.dangerous = dangerous;
    }

    @Override
    public void start() {
        if (dangerous) {
            Potions.checkOverloads(player);
            player.addEffect(Effect.OVERLOAD_PVP_REDUCTION, Integer.MAX_VALUE);
            player.setSkullInfiniteDelay(7);
        }
        sendInterfaces();
        Crucible.add(player, dangerous);
    }

    @Override
    public void sendInterfaces() {
        player.getInterfaceManager().sendOverlay(1296);
    }

    @Override
    public boolean sendDeath() {
        player.lock(8);
        player.stopAll();
        WorldTasks.scheduleTimer(loop -> {
            if (loop == 0)
                player.setNextAnimation(new Animation(836));
            else if (loop == 1)
                player.sendMessage("Oh dear, you have died.");
            else if (loop == 3) {
                Player killer = player.getMostDamageReceivedSourcePlayer();
                if (killer != null) {
                    killer.removeDamage(player);
                    killer.increaseKillCount(player);
                }
                if (dangerous) {
                    player.sendItemsOnDeath(killer);
                    player.getEquipment().init();
                    player.getInventory().init();
                }
                player.reset();
                player.setNextTile(getRespawnTile());
                remove(true);
                player.setNextAnimation(new Animation(-1));
            } else if (loop == 4) {
                player.jingle(90);
                return false;
            }
            return true;
        });
        return false;
    }

    @Override
    public boolean canDepositItem(Item item) {
        if (MinigameUtil.isMinigameSupply(item.getId())) {
            player.getInventory().deleteItem(item);
            return false;
        }
        return true;
    }

    @Override
    public boolean processMagicTeleport(Tile toTile) {
        player.sendMessage("A mysterious force prevents you from teleporting.");
        return false;
    }

    @Override
    public boolean processItemTeleport(Tile toTile) {
        player.sendMessage("A mysterious force prevents you from teleporting.");
        return false;
    }

    @Override
    public boolean processObjectTeleport(Tile toTile) {
        player.sendMessage("A mysterious force prevents you from teleporting.");
        return false;
    }

    @Override
    public void forceClose() {
        MinigameUtil.checkAndDeleteFoodAndPotions(player);
        remove(false);
    }

    private void remove(boolean needRemove) {
        MinigameUtil.checkAndDeleteFoodAndPotions(player);
        if (needRemove)
            removeController();
        if (wasInArea)
            player.setCanPvp(false);
        player.getInterfaceManager().removeOverlay();
        player.removeSkull();
        player.removeEffect(Effect.OVERLOAD_PVP_REDUCTION);
        Crucible.remove(player, dangerous);
    }

    @Override
    public boolean processObjectClick1(GameObject object) {
        switch (object.getId()) {
            case 72936 -> {
                player.sendOptionDialogue("What would you like to do?", ops -> {
                    ops.add("I'd like to open my bank please.", () -> player.getBank().open());
                    if (!dangerous)
                        ops.add("Kit me out with some food and potions please.", () -> {
                            player.getInventory().addItem(18715, 1);
                            player.getInventory().addItem(22373, 2);
                            player.getInventory().addItem(22379, 2);
                            player.getInventory().addItem(22375, 1);
                            player.getInventory().addItem(4049, player.getInventory().getFreeSlots());
                        });
                    ops.add("Nevermind.");
                });
                return false;
            }
            case 72922 -> {
                remove(true);
                player.useStairs(-1, Tile.of(3355, 6119, 0), 0, 1);
                return false;
            }
        }
        return true;
    }

    @Override
    public void moved() {
        boolean inArea = !inSafeArea(player);
        if (inArea && !wasInArea) {
            player.setCanPvp(true);
            wasInArea = true;
        } else if (!inArea && wasInArea) {
            player.setCanPvp(false);
            wasInArea = false;
        }
    }

    public static Tile getRespawnTile() {
        return SAFE_ZONES[Utils.random(SAFE_ZONES.length)].getRandomTile(1);
    }

    @Override
    public boolean canAttack(Entity target) {
        if (canHit(target))
            return true;
        return false;
    }

    @Override
    public boolean canHit(Entity target) {
        if (target instanceof Player p) {
            if (p.getControllerManager().getController() != null && p.getControllerManager().getController() instanceof CrucibleController controller) {
                if (dangerous && !controller.dangerous) {
                    player.sendMessage("That player isn't in " + (dangerous ? "dangerous" : "safe") + " mode with you.");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inSafeArea(Player player) {
        for (MapUtils.Area area : SAFE_ZONES)
            if (area.within(player.getTile()))
                return true;
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public boolean login() {
        start();
        moved();
        return false;
    }
}
