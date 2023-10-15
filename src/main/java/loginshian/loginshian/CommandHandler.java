package loginshian.loginshian;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.security.NoSuchAlgorithmException;

public class CommandHandler implements CommandExecutor {
    private DataReader dataReader;

    public CommandHandler(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    @ParametersAreNonnullByDefault


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("login")) {

            if (!(sender instanceof Player)) {
                // 如果是玩家發的命令的話
                return false;

            }
            if (!LoginData.hasPlayerName(sender.getName())) {
                sender.sendMessage(ChatColor.GREEN + "你已經登入了");
                return true;
                // 已經登入
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "必須輸入密碼");

                return false;

            }
            String pwdConcat = String.join("<space>", args);

            if (dataReader.isPlayerRegistered(sender.getName())) {
                // 如果已經註冊
                if (dataReader.verifyPassword(sender.getName(), pwdConcat)) {
                    // 驗證密碼
                    LoginData.removePlayerName(sender.getName());
                    // 解鎖玩家
                    Bukkit.getPlayer(sender.getName()).setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage(ChatColor.GREEN + "登錄成功 歡迎光臨!");

                } else {
                    sender.sendMessage(ChatColor.RED + "密碼錯誤！");
                }
                return true;


            } else {
                // 玩家没註冊
                dataReader.addPlayer(sender.getName(), pwdConcat, ((Player) sender).getUniqueId().toString());
                // 註冊玩家
                LoginData.removePlayerName(sender.getName());
                // 解鎖玩家
                Bukkit.getPlayer(sender.getName()).setGameMode(GameMode.SURVIVAL);
                sender.sendMessage(ChatColor.GREEN + "註冊成功！");
                return true;
            }

        }


        return false;
    }
}
