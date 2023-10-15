package loginshian.loginshian;

import org.bukkit.configuration.file.FileConfiguration;


public class ConfigReader {
    public static FileConfiguration config = Main.instance.getConfig();

    public static boolean isPlayerRegistered(String playerName) {
        String A = playerName.toLowerCase();
        return A.equals(config.getString(playerName.toLowerCase()+"玩家.玩家名"));

    }

    public static boolean verifyPassword(String playerName, String password) {
        String A = MD5.uncode(password);
        return A.equals(config.getString(playerName.toLowerCase()+"玩家.PASSWORD"));


    }

    public static void changePassword(String playerName, String password) {
        String A = MD5.uncode(password);
        Main.instance.getConfig().set(playerName.toLowerCase() + "玩家.PASSWORD",A);
        Main.instance.saveConfig();


    }

    public static void addPlayer(String playerName, String password,String UUID){
       String A = MD5.uncode(password);
       Main.instance.getConfig().set(playerName.toLowerCase() + "玩家.玩家名",playerName.toLowerCase());
        Main.instance.getConfig().set(playerName.toLowerCase() + "玩家.UUID",UUID);
        Main.instance.getConfig().set(playerName.toLowerCase() + "玩家.PASSWORD",A);
        Main.instance.saveConfig();
    }




}
