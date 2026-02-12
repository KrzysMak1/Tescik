package dev.project.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.project.modules.Module;
import dev.project.modules.ModuleManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;

public final class ConfigStore {
    private static final ConfigStore INSTANCE = new ConfigStore();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("utilitymod/config.json");

    private ConfigStore() {
    }

    public static ConfigStore getInstance() {
        return INSTANCE;
    }

    public void load() {
        try {
            if (!Files.exists(configPath)) {
                return;
            }
            JsonObject root = JsonParser.parseString(Files.readString(configPath)).getAsJsonObject();
            for (Module module : ModuleManager.getInstance().all()) {
                JsonElement moduleElement = root.get(module.getName());
                if (moduleElement != null && moduleElement.isJsonObject()) {
                    module.readConfig(moduleElement.getAsJsonObject(), GSON);
                }
            }
        } catch (IOException ignored) {
        }
    }

    public void save() {
        try {
            Files.createDirectories(configPath.getParent());
            JsonObject root = new JsonObject();
            for (Module module : ModuleManager.getInstance().all()) {
                root.add(module.getName(), module.writeConfig(GSON));
            }
            Files.writeString(configPath, GSON.toJson(root));
        } catch (IOException ignored) {
        }
    }
}
