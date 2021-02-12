package ru.fotontv.rpbase.api;

import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;
import ru.fotontv.rpbase.RPBase;
import ru.fotontv.rpbase.data.PlayerData;
import ru.fotontv.rpbase.data.PlayersManager;
import ru.fotontv.rpbase.data.ProfessionsEnum;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class PlayersApi {

    public static void addLock(Player player) {
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null) {
            data.addCountUnlock();
            if (data.getCountUnlock() == 50) {
                ProfessionsEnum professionsEnum = data.getProfession();
                data.setProfession(ProfessionsEnum.THIEF);
                data.passport.setProfession(ProfessionsEnum.THIEF.getNameProf());
                CompletableFuture<User> futureUser = RPBase.api.getUserManager().loadUser(player.getUniqueId());
                futureUser.thenAcceptAsync(user -> {
                    Group group = RPBase.api.getGroupManager().getGroup(professionsEnum.getLuckpermsGroup());
                    Group group1 = RPBase.api.getGroupManager().getGroup(ProfessionsEnum.THIEF.getLuckpermsGroup());
                    if (group != null && group1 != null) {
                        InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                        InheritanceNode node1 = InheritanceNode.builder(group1).value(true).build();
                        DataMutateResult result = user.data().remove(node);
                        DataMutateResult result1 = user.data().add(node1);
                        if(!result.wasSuccessful() && !result1.wasSuccessful()) {
                            player.sendMessage("§cLuckPerms failed with " + result.name().toUpperCase() + ".");
                            player.sendMessage("§cLuckPerms failed with " + result1.name().toUpperCase() + ".");
                            return;
                        }
                    }
                    RPBase.api.getUserManager().saveUser(user);
                });
            }
            PlayersManager.savePlayerData(data);
        }
    }

    public static boolean isTHIEF(Player player) {
        PlayerData data = PlayersManager.getPlayerData(player);
        if (data != null) {
            return data.getProfession().equals(ProfessionsEnum.THIEF);
        }
        return false;
    }
}
