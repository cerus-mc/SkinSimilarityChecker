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

package de.cerus.skinsimilaritycheck.bungee.commands;

import de.cerus.ceruslib.commandframework.Arguments;
import de.cerus.skinsimilaritycheck.common.util.ImageUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MatchSkinsCommand extends Command {
    public MatchSkinsCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if(!player.hasPermission("ssc.matchskins.use")) {
            player.sendMessage(TextComponent.fromLegacyText("§cYou do not have the required permissions!"));
            return;
        }

        if (strings.length == 2) {
            String nameOne = strings[0];
            String nameTwo = strings[0];

            OfflinePlayer playerOne = Bukkit.getOfflinePlayer(nameOne);
            OfflinePlayer playerTwo = Bukkit.getOfflinePlayer(nameTwo);

            BufferedImage one;
            BufferedImage two;
            try {
                one = ImageUtil.getFromPlayer(playerOne);
                two = ImageUtil.getFromPlayer(playerTwo);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            player.sendMessage(getPrefix()+ImageUtil.getSimilarity(one, two)+"%");
            return;
        }

        player.sendMessage(getPrefix()+"/matchskins <Player one> <Player two>");
        return;
    }

    private String getPrefix() {
        return "§2§lS§a§lSC §8| §f";
    }
}
