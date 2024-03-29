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

package de.cerus.skinsimilaritycheck.bukkit;

import de.cerus.ceruslib.CerusPlugin;
import de.cerus.skinsimilaritycheck.bukkit.commands.MatchSkinsCommand;
import de.cerus.skinsimilaritycheck.common.config.GeneralConfig;
import de.cerus.skinsimilaritycheck.bukkit.listener.JoinListener;
import de.cerus.skinsimilaritycheck.common.util.StaffSkinUtil;
import org.bstats.bukkit.Metrics;

public class SkinSimilarityCheck extends CerusPlugin {
    @Override
    public void onPluginEnable() {
        Metrics metrics = new Metrics(this);

        GeneralConfig generalConfig = new GeneralConfig();
        generalConfig.initialize();
        generalConfig.load();

        registerAll(new JoinListener(this, generalConfig));
        registerAll(new MatchSkinsCommand(this));

        StaffSkinUtil.loadStaffSkins(generalConfig);
    }

    @Override
    public void onPluginDisable() {

    }
}
