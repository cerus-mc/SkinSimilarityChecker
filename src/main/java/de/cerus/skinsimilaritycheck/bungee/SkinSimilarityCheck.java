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

package de.cerus.skinsimilaritycheck.bungee;

import de.cerus.skinsimilaritycheck.common.config.GeneralConfig;
import net.md_5.bungee.api.plugin.Plugin;
import org.apache.commons.lang.NotImplementedException;
import org.bstats.bungeecord.Metrics;

public class SkinSimilarityCheck extends Plugin {

    @Override
    public void onEnable() {
        throw new NotImplementedException("Please use the Spigot plugin!");

        /*Metrics metrics = new Metrics(this);

        GeneralConfig generalConfig = new GeneralConfig();
        generalConfig.initialize();
        generalConfig.load();*/
    }
}
