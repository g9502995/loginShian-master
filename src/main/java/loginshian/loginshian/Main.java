package loginshian.loginshian;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;




public final class Main extends JavaPlugin {
    public static JavaPlugin instance;
    public static Main plugin;
    private Connection connection;
    private DataReader dataReader;
    private DatabaseManager dbManager;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        // 如果配置文件不存在，Bukkit 会保存默认的配置
    }

    @Override
    public void onEnable() {

        // 配置数据库连接信息
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/minecraft";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            getLogger().info("数据库连接成功");
        } catch (SQLException e) {
            getLogger().severe("数据库连接失败 " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        System.out.println("ShianLogin Plugin v0.0.1");
        // 註冊事件
        dataReader = new DataReader(connection, dbManager);
        Bukkit.getPluginManager().registerEvents(new EventListener(dataReader), this);

        // 註冊事件處理

        Objects.requireNonNull(Bukkit.getPluginCommand("login")).setExecutor(new CommandHandler(dataReader));
        Objects.requireNonNull(Bukkit.getPluginCommand("register")).setExecutor(new CommandHandlers(dataReader));

        instance = this;
        setPlugin(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (connection != null) {
            try {
                connection.close();
                getLogger().info("数据库關閉");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
