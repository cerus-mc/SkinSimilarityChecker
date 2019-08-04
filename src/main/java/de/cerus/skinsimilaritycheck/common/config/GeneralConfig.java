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

package de.cerus.skinsimilaritycheck.common.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GeneralConfig extends Config {

    private double maxAllowedPercentage;
    private boolean autoKick;
    private List<UUID> staffUuids;

    public GeneralConfig() {
        super(new File("plugins/SkinSimilarityCheck/config.yml"));
    }

    @Override
    public void loadValues() {
        FileConfiguration configuration = getConfiguration();
        maxAllowedPercentage = configuration.getDouble("max-allowed-percentage");
        autoKick = configuration.getBoolean("auto-kick");
        staffUuids = configuration.getStringList("staff-uuids").stream().map(UUID::fromString).collect(Collectors.toList());
    }

    @Override
    public void setDefaults() {
        FileConfiguration configuration = getConfiguration();
        configuration.set("max-allowed-percentage", 70);
        configuration.set("auto-kick", true);
        configuration.set("staff-uuids", Arrays.asList("af74a02d-19cb-445b-b07f-6866a861f783", "06f8c3cc-a3c5-4b48-bc6d-d3ee8963f2af"));
        save();
    }

    public double getMaxAllowedPercentage() {
        return maxAllowedPercentage;
    }

    public List<UUID> getStaffUuids() {
        return staffUuids;
    }

    public boolean isAutoKick() {
        return autoKick;
    }
}
