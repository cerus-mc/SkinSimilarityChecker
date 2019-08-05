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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.cerus.skinsimilaritycheck.common.util.DiscordUtil;

import java.io.*;
import java.net.HttpURLConnection;

public class DiscordTest {

    public static void main(String[] args) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new java.net.URL("https://discordapp.com/api/webhooks/608023590436077575/Z7B2e8E8CQzwYLNL3nYxk9tLwT9VWgTaS8eJa_QIWffKhX_uoc_H-wnukSD8wP60BgtP").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Custom");

            DiscordUtil.DiscordMessage build = new DiscordUtil.DiscordMessage.DiscordMessageBuilder()
                    .user("SSC")
                    .avatar("https://img.freepik.com/vektoren-kostenlos/hand-haelt-flache-designillustration-des-stoppschildes_16734-88.jpg?size=338&ext=jpg")
                    .content("Player x joined with a staff skin! " + (true ? "(Player got automatically kicked)" : ""))
                    .build();
            System.out.println(build.toJson().toString());

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(build.toJson().toString().getBytes());
            outputStream.flush();

            System.out.println(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
