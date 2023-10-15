package loginshian.loginshian;

import java.util.ArrayList;
import java.util.List;

public class LoginData {
    public static final List<String> RESTRICTS = new ArrayList<>();
    //這裡儲存玩家的名字
    public static void addPlayerName(String playerNameIn) {
        String convertedName = playerNameIn.toLowerCase();

        if (!RESTRICTS.contains(convertedName)) {

            RESTRICTS.add(convertedName);
        }
    }
//這裡刪除玩家名字
    public static void removePlayerName(String playerNameIn) {
        String convertedName = playerNameIn.toLowerCase();
        RESTRICTS.remove(convertedName);
    }

//擁有玩家名字
    public static boolean hasPlayerName(String playerNameIn) {
        String convertedName = playerNameIn.toLowerCase();
        return RESTRICTS.contains(convertedName);
    }



}
