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

package de.cerus.skinsimilaritycheck.common.util;

import de.cerus.skinsimilaritycheck.common.config.GeneralConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffSkinUtil {

    private static Map<UUID, BufferedImage> staffSkins = new HashMap<>();

    private static GeneralConfig generalConfig;

    private StaffSkinUtil() {
        throw new UnsupportedOperationException();
    }

    public static void loadStaffSkins(GeneralConfig generalConfig) {
        StaffSkinUtil.generalConfig = generalConfig;

        System.out.println("Loading "+generalConfig.getStaffUuids().size()+" staff skins");
        for (UUID staffUuid : generalConfig.getStaffUuids()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(staffUuid);
            try {
                staffSkins.put(staffUuid, ImageUtil.getFromPlayer(offlinePlayer));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load skin of "+offlinePlayer.getName());
            }
        }
        System.out.println("Done");
    }

    public static boolean matchesStaffSkin(Player player) {
        BufferedImage skin;
        try {
            skin = ImageUtil.getFromPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        for (Map.Entry<UUID, BufferedImage> entry : staffSkins.entrySet()) {
            if(player.getUniqueId().equals(entry.getKey())) continue;
            if(ImageUtil.getSimilarity(skin, entry.getValue()) > generalConfig.getMaxAllowedPercentage()) {
                System.out.println("Player "+player.getName()+" matches skin of "+entry.getKey());
                return true;
            }
        }
        return false;
    }
}
