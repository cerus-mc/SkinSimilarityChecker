/*
 *  Copyright (c) 2018 Cerus
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Cerus
 *
 */

package de.cerus.skinsimilaritycheck.commands;

import de.cerus.ceruslib.commandframework.Arguments;
import de.cerus.ceruslib.commandframework.Command;
import de.cerus.skinsimilaritycheck.util.ImageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.CraftOfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MatchSkinsCommand extends Command {
    public MatchSkinsCommand(JavaPlugin plugin) {
        super(plugin, "matchskins");
        setPrefix("§2§lS§a§lSC §8| §f");
        setNoPermsMessage("§cYou do not have the required permission(s): §e");
    }

    @Override
    public boolean onPlayerCommand(Player player, org.bukkit.command.Command command, String s, Arguments arguments) {
        if (!checkPermission(player, "ssc.matchskins.use"))
            return true;

        if (arguments.size() == 2) {
            String nameOne = arguments.get(0).getArgument();
            String nameTwo = arguments.get(1).getArgument();

            OfflinePlayer playerOne = Bukkit.getOfflinePlayer(nameOne);
            OfflinePlayer playerTwo = Bukkit.getOfflinePlayer(nameTwo);

            BufferedImage one;
            BufferedImage two;
            try {
                one = ImageUtil.getFromPlayer(playerOne);
                two = ImageUtil.getFromPlayer(playerTwo);
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }

            player.sendMessage(getPrefix()+ImageUtil.getSimilarity(one, two)+"%");
            return true;
        }

        player.sendMessage(getPrefix()+"/matchskins <Player one> <Player two>");
        return true;
    }
}
