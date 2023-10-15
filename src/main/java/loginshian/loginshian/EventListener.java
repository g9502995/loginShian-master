package loginshian.loginshian;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;


import static org.bukkit.Bukkit.getServer;


public class EventListener implements Listener {
    private DataReader dataReader;

    public EventListener(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public static EventListener instance;

    @EventHandler(priority= EventPriority.HIGHEST)

    public void onPlayerLogin(PlayerLoginEvent e) {
        //新增玩家名字
        String playerName = e.getPlayer().getName();

        EventListener pl = EventListener.instance;


        LoginData.addPlayerName(e.getPlayer().getName());


        if (LoginData.hasPlayerName(e.getPlayer().getName()) && dataReader.isPlayerRegistered(playerName)) {
            final Player player = e.getPlayer();

            new BukkitRunnable() {
                int i = 60;

                @Override
                public void run() {
                    if (!LoginData.hasPlayerName(player.getName())) {
                        this.cancel();
                        return;
                    }
                    if (i == 60) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage("請登入 /login");
                    }
                    if (i == 30) {
                        player.sendMessage("請登入 /login，否则在30秒后将会被踢出");
                    }
                    if (i == 1) {
                        this.cancel();
                        player.kickPlayer("你因为没有登入而被踢出");
                    }
                    i--;
                }
            }.runTaskTimer(Main.getPlugin(), 0L, 20L);
        } else {
            final Player player = e.getPlayer();

            new BukkitRunnable() {
                int i = 60;

                @Override
                public void run() {
                    if (!LoginData.hasPlayerName(player.getName())) {
                        this.cancel();
                        return;
                    }
                    if (i == 60) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage("請使用 /register <密碼>  <重複密碼> 来注册你的帐号。");
                    }
                    if (i == 30) {
                        player.sendMessage("請使用 /register <密碼>  <重複密碼> 来注册你的帐号。");
                    }
                    if (i == 1) {
                        this.cancel();
                        player.kickPlayer("你因为没有注册而被踢出");
                    }
                    i--;
                }
            }.runTaskTimer(Main.getPlugin(), 0L, 20L);
        }
    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        //離線移除玩家名字
        LoginData.removePlayerName(e.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        // 阻止玩家聊天
        if (LoginData.hasPlayerName(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }
    public static void cancelIfNotLoggedIn(Cancellable e) {


        if (e instanceof PlayerEvent) {

            if (LoginData.hasPlayerName(((PlayerEvent) e).getPlayer().getName())) {


                e.setCancelled(true);
            }
        } else if (e instanceof InventoryOpenEvent) {

            if (LoginData.hasPlayerName(((InventoryOpenEvent) e).getPlayer().getName())) {

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void restrictMove(PlayerMoveEvent e) {

        cancelIfNotLoggedIn(e);

    }

    @EventHandler
    public void restrictInteract(PlayerInteractEvent e) {

        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictInteractAtEntity(PlayerInteractAtEntityEvent e) {

        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictPortal(PlayerPortalEvent e) {

        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictTeleport(PlayerTeleportEvent e) {

        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictOpenInventory(InventoryOpenEvent e) {

        cancelIfNotLoggedIn(e);
    }




}
