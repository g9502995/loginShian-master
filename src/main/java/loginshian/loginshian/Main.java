package loginshian.loginshian;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;




public final class Main extends JavaPlugin {
    public static JavaPlugin instance;
    public static Main plugin;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        // 如果配置文件不存在，Bukkit 会保存默认的配置
    }

    @Override
    public void onEnable() {

        System.out.println("ShianLogin Plugin v0.0.1");
        // 註冊事件
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        // 註冊事件處理
        Objects.requireNonNull(Bukkit.getPluginCommand("login")).setExecutor(new CommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("register")).setExecutor(new CommandHandlers());

        instance = this;
        setPlugin(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }
    //給予插件參數
    public static Main getPlugin() {
        return plugin;
    }

    public static void setPlugin(Main plugin) {
        Main.plugin = plugin;
    }
    

}
