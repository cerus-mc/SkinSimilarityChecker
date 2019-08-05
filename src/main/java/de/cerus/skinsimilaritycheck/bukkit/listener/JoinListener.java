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

package de.cerus.skinsimilaritycheck.bukkit.listener;

import de.cerus.ceruslib.listenerframework.CerusListener;
import de.cerus.skinsimilaritycheck.common.config.GeneralConfig;
import de.cerus.skinsimilaritycheck.common.util.DiscordUtil;
import de.cerus.skinsimilaritycheck.common.util.StaffSkinUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener extends CerusListener {

    private GeneralConfig generalConfig;

    public JoinListener(JavaPlugin plugin, GeneralConfig generalConfig) {
        super(plugin);
        this.generalConfig = generalConfig;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new Thread(() -> {
            Player player = event.getPlayer();
            if (StaffSkinUtil.matchesStaffSkin(player) && !player.hasPermission("ssc.bypass")) {
                Bukkit.broadcast("§8§l[§c§l!§8§l] §7" + player.getName() + "'s skin matches a staff member's skin! §8§l[§c§l!§8§l]", "ssc.notify");
                if (generalConfig.isAutoKick())
                    getPlugin().getServer().getScheduler().runTask(getPlugin(), () -> player.kickPlayer("\n§cYour skin matches a staff member's skin!\n§7Change your skin in order to be able to join again."));

                if (generalConfig.isDiscordEnabled()) {
                    boolean success = DiscordUtil.sendMessage(generalConfig.getWebhook(), new DiscordUtil.DiscordMessage.DiscordMessageBuilder()
                            .user("SSC")
                            .avatar("https://img.freepik.com/vektoren-kostenlos/hand-haelt-flache-designillustration-des-stoppschildes_16734-88.jpg?size=338&ext=jpg")
                            .content("Player " + player.getName() + " joined with a staff skin! " + (generalConfig.isAutoKick() ? "(Player got automatically kicked)" : ""))
                            .build());
                    if (!success)
                        System.out.println("Failed to send discord message");
                }
            }
        }).start();
    }
}
