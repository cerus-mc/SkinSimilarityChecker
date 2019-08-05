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

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ConfigRepresenter {

    private File file;
    private Object config;
    private boolean spigot;

    public ConfigRepresenter(File file, Object config, boolean spigot) {
        this.file = file;
        this.config = config;
        this.spigot = spigot;
    }

    public Object getConfig() {
        return config;
    }

    public Collection<String> getKeys(boolean deep) {
        if(spigot) {
            YamlConfiguration config = (YamlConfiguration) this.config;
            return config.getKeys(deep);
        } else {
            Configuration configuration = (Configuration) config;
            return configuration.getKeys();
        }
    }

    public List<String> getStringList(String path) {
        return (List<String>) getList(path);
    }

    public List<?> getList(String path) {
        return (List<?>) get(path);
    }

    public boolean getBoolean(String path) {
        return (boolean) get(path);
    }

    public double getDouble(String path) {
        Object o = get(path);
        if(o instanceof Integer)
            return getInt(path);
        return (double) o;
    }

    public int getInt(String path) {
        return (int) get(path);
    }

    public String getString(String path) {
        return (String) get(path);
    }

    public Object get(String path) {
        return spigot ? ((YamlConfiguration) config).get(path) : ((Configuration) config).get(path);
    }

    public void set(String path, Object o) {
        if (spigot)
            ((YamlConfiguration) config).set(path, o);
        else ((Configuration) config).set(path, o);
    }

    public boolean contains(String path) {
        if (spigot)
            return ((YamlConfiguration) config).contains(path);
        else return ((Configuration) config).contains(path);
    }

    public void save() {
        try {
            if (spigot) {
                ((YamlConfiguration) config).save(file);
            } else {
                ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(((Configuration) config), file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
