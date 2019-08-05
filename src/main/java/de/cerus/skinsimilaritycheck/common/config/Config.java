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

import net.md_5.bungee.config.ConfigurationProvider;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    private File file;
    private ConfigRepresenter configuration;
    private boolean spigot = true;

    public Config(File file) {
        this.file = file;
    }

    public void initialize() {
        try {
            Class.forName("org.bukkit.configuration.file.YamlConfiguration");
        } catch (ClassNotFoundException e) {
            spigot = false;
        }

        if (spigot)
            configuration = new ConfigRepresenter(file, YamlConfiguration.loadConfiguration(file), spigot);
        else {
            try {
                if (!file.exists())
                    file.createNewFile();
                configuration = new ConfigRepresenter(file, ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file), spigot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        if (configuration.getKeys(true).isEmpty())
            setDefaults();
        update();
        loadValues();
    }

    public abstract void loadValues();

    public abstract void setDefaults();

    public void update() {
    }

    public void save() {
        configuration.save();
    }

    public File getFile() {
        return file;
    }

    public ConfigRepresenter getConfiguration() {
        return configuration;
    }
}
