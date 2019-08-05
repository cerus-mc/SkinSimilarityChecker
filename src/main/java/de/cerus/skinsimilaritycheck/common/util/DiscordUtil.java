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

import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class DiscordUtil {

    public static boolean sendMessage(String webhook, DiscordMessage message) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new java.net.URL(webhook).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Custom");

            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(message.toJson().toString().getBytes());
            outputStream.flush();

            connection.getResponseCode();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static class DiscordMessage {

        private String user;
        private String avatar;
        private String content;

        private DiscordMessage() {
        }

        public JsonObject toJson() {
            JsonObject object = new JsonObject();
            if (user != null)
                object.addProperty("username", user);
            if (avatar != null)
                object.addProperty("avatar_url", avatar);
            object.addProperty("content", content);
            return object;
        }

        private void setUser(String user) {
            this.user = user;
        }

        private void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        private void setContent(String content) {
            this.content = content;
        }

        public static class DiscordMessageBuilder {

            private DiscordMessage message;

            public DiscordMessageBuilder() {
                message = new DiscordMessage();
            }

            public DiscordMessageBuilder user(String user) {
                message.setUser(user);
                return this;
            }

            public DiscordMessageBuilder avatar(String avatar) {
                message.setAvatar(avatar);
                return this;
            }

            public DiscordMessageBuilder content(String content) {
                message.setContent(content);
                return this;
            }

            public DiscordMessage build() {
                return message;
            }
        }
    }
}
