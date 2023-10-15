package loginshian.loginshian;

import org.bukkit.GameMode;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitScheduler;


import static org.bukkit.Bukkit.getServer;


public class EventListener implements Listener {

    public static EventListener instance;

    @EventHandler(priority= EventPriority.HIGHEST)

    public void onPlayerLogin(PlayerLoginEvent e) {
        //新增玩家名字
        String playerName = e.getPlayer().getName();

        EventListener pl = EventListener.instance;


        LoginData.addPlayerName(e.getPlayer().getName());


        if (LoginData.hasPlayerName(e.getPlayer().getName())&& ConfigReader.isPlayerRegistered(playerName)) {

            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

                int i = 60;
                @Override

                public void run() {
                    i--;
                    if(!LoginData.hasPlayerName(e.getPlayer().getName())) {
                        scheduler.cancelTasks(Main.getPlugin());
                        return ;
                    }
                    if (i == 59) {
                        e.getPlayer().setGameMode(GameMode.SPECTATOR);
                        e.getPlayer().sendMessage("請登入/login");
                    }



                    if (i == 30) {
                        e.getPlayer().sendMessage("請登入/login 在30秒後不登入將會踢除");
                    }
                    if (i == 1) {
                        scheduler.cancelTasks(Main.getPlugin());
                        e.getPlayer().kickPlayer("你因為沒登入被踢");
                    }

                }

            },0L,  1*20L);


        }else{

            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

                int i = 60;
                @Override

                public void run() {
                    i--;
                    if(!LoginData.hasPlayerName(e.getPlayer().getName())) {
                        scheduler.cancelTasks(Main.getPlugin());
                        return ;
                    }
                    if (i == 59) {
                        e.getPlayer().setGameMode(GameMode.SPECTATOR);
                        e.getPlayer().sendMessage("請使用 /register <密碼>  <重複密碼>來註冊你的帳號。");
                    }



                    if (i == 30) {
                        e.getPlayer().sendMessage("請使用 /register <密碼>  <重複密碼>來註冊你的帳號。");
                    }
                    if (i == 1) {
                        scheduler.cancelTasks(Main.getPlugin());
                        e.getPlayer().kickPlayer("你因為沒登入被踢");
                    }

                }

            },0L,  1*20L);

        }
    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        //離線移除玩家名字
        LoginData.removePlayerName(e.getPlayer().getName());
    }
    public static void cancelIfNotLoggedIn(Cancellable e) {
        // 这里写着 Cancellable，和上面的 List 是一个原理，说到底我们只需要「可以取消」这个功能就可以了，至于到底是哪个类，不重要

        if (e instanceof PlayerEvent) {
            // instanceof 关键字指示 Java 重新判断左边对象的类型是不是右边的类或者右边类的子类，也就是判断能否进行强制类型转换
            if (LoginData.hasPlayerName(((PlayerEvent) e).getPlayer().getName())) {

                // if 语句用于看看玩家是不是在限制列表中
                // (PlayerEvent) e 进行类型转换
                e.setCancelled(true);
            }
        } else if (e instanceof InventoryOpenEvent) {
            // else if 表示「上一条 if 的条件为假」并且「当前括号中的条件为真」时才执行大括号里面的内容，相当于「如果不是那样，而是这样，就做……」

            // 限制玩家打开物品栏，需要 InventoryOpenEvent
            if (LoginData.hasPlayerName(((InventoryOpenEvent) e).getPlayer().getName())) {

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void restrictMove(PlayerMoveEvent e) {
        // 移动
        cancelIfNotLoggedIn(e);
        // 你看这多方便
    }

    @EventHandler
    public void restrictInteract(PlayerInteractEvent e) {
        // 交互
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictInteractAtEntity(PlayerInteractAtEntityEvent e) {
        // 实体交互
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictPortal(PlayerPortalEvent e) {
        // 传送门
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictTeleport(PlayerTeleportEvent e) {
        // 传送
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictOpenInventory(InventoryOpenEvent e) {
        // 打开物品栏
        cancelIfNotLoggedIn(e);
    }




}
