package net.fabicraft3d.utils;

import com.google.gson.Gson;

import java.io.*;

public class ConfigManager {
    private final File file = new File("config.json");
    private final Gson gson = new Gson();

    public void saveConfig() {
        ReadConfig readConfig = new ReadConfig("TOKEN", "954650148754051113", "971020625126756352", "971022048862277683", "roleid3", "roleid4", "General");


        try (FileWriter fileWriter = new FileWriter(file)) {
            if (!file.exists()) file.createNewFile();

            fileWriter.write(gson.toJson(readConfig));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public ReadConfig readConfig() {
        if (!file.exists()) saveConfig();

        try {
            return gson.fromJson(new FileReader(file), ReadConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
