package fi.kask.difmod.config;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fi.kask.difmod.DifMod;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigReader {
    private static final String ConfigFileName = "damage-config.json";

    private final String basePath;

    public ConfigReader(String basePath) {
        this.basePath = basePath;
    }

    public DifModConfig getModConfig() {
        var gson = new Gson();
        String jsonString;

        try {
            jsonString = Files.readString(Path.of(basePath, ConfigFileName), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            DifMod.LOGGER.warn("DifMod damage-config.json does not exist, ignoring..");
            return getEmptyConfig();
        } catch (IOException e) {
            DifMod.LOGGER.error("Unable to read config file", e);
            return getEmptyConfig();
        }

        try {
            return gson.fromJson(jsonString, DifModConfig.class);
        } catch (JsonSyntaxException e) {
            DifMod.LOGGER.error("DifMof config file has invalid syntax!");
        }

        return getEmptyConfig();
    }

    private static DifModConfig getEmptyConfig() {
        return new DifModConfig();
    }
}
