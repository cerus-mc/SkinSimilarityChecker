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

package de.cerus.skinsimilaritycheck.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ImageUtil {

    private ImageUtil() {
        throw new UnsupportedOperationException();
    }

    public static double getSimilarity(BufferedImage one, BufferedImage two) {
        double percentage = 0;
        try {
            DataBuffer dataBufferOne = one.getData().getDataBuffer();
            int sizeOne = dataBufferOne.getSize();
            DataBuffer dataBufferTwo = two.getData().getDataBuffer();
            int sizeTwo = dataBufferTwo.getSize();
            int count = 0;

            if (sizeOne == sizeTwo) {
                for (int i = 0; i < sizeOne; i++) {
                    if (dataBufferOne.getElem(i) == dataBufferTwo.getElem(i)) {
                        count = count + 1;
                    }
                }
                percentage = (count * 100) / sizeOne;
            }
        } catch (Exception ignored) {
            System.out.println("Failed to compare image files ...");
        }

        return percentage;
    }

    public static BufferedImage getFromPlayer(OfflinePlayer player) throws IOException {
        Class<?> craftOfflinePlayerClass;
        Class<?> craftPlayerClass;
        try {
            craftOfflinePlayerClass = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".CraftOfflinePlayer");
            craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".entity.CraftPlayer");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Object cast;
        try {
            cast = craftOfflinePlayerClass.cast(player);
        } catch (ClassCastException ignored) {
            cast = craftPlayerClass.cast(player);
        }
        GameProfile profile;
        try {
            profile = (GameProfile) cast.getClass().getMethod("getProfile").invoke(cast);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

        JsonObject parent;
        if (!profile.getProperties().asMap().isEmpty()) {
            Collection<Property> textures = profile.getProperties().get("textures");
            String value = textures.iterator().next().getValue();

            parent = new JsonParser().parse(new String(Base64.getDecoder().decode(value))).getAsJsonObject();
        } else {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.minetools.eu/profile/" +
                    player.getUniqueId().toString().replace("-", "")).openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Request for " + player.getUniqueId() + " returned " + responseCode);
                if(responseCode == 429)
                    System.out.println("This system got ratelimited! Please slow down your actions.");
                return null;
            }

            StringBuilder content = new StringBuilder();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");
            connection.disconnect();
            reader.close();
            inputStream.close();

            JsonObject response = new JsonParser().parse(content.toString().trim()).getAsJsonObject();
            if(!response.get("raw").getAsJsonObject().get("status").getAsString().equals("OK")){
                System.out.println("Request for " + player.getUniqueId() + " returned status " + response.get("raw").getAsJsonObject().get("status").getAsString());
                return null;
            }

            JsonArray properties = response.get("raw").getAsJsonObject().get("properties").getAsJsonArray();
            JsonObject property = properties.get(0).getAsJsonObject();
            parent = new JsonParser().parse(new String(Base64.getDecoder().decode(property.get("value").getAsString()))).getAsJsonObject();
        }

        JsonObject texturesObj = parent.get("textures").getAsJsonObject();
        JsonObject skin = texturesObj.get("SKIN").getAsJsonObject();
        String skinUrl = skin.get("url").getAsString();

        if (skinUrl == null)
            return null;
        BufferedImage bufferedImage = ImageIO.read(new URL(skinUrl));
        if(bufferedImage == null)
            return null;

        return bufferedImage;
    }

    private static String getServerVersion() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return "unknown";
        }
        return version;
    }
}
