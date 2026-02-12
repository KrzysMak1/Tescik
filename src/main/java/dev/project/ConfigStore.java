package dev.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.project.configs.BacktrackConfig;
import dev.project.configs.FakeLagConfig;
import dev.project.configs.ModuleConfig;
import dev.project.modules.Module;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;

public final class ConfigStore {
    private static final ConfigStore INSTANCE = new ConfigStore();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path file = FabricLoader.getInstance().getConfigDir().resolve("utilitymod/config.json");
    private final Map<String, ModuleConfig> configs = new LinkedHashMap<>();

    private ConfigStore() {
        configs.put("fakelag", new FakeLagConfig());
        configs.put("backtrack", new BacktrackConfig());
    }

    public static ConfigStore getInstance() {
        return INSTANCE;
    }

    public void save() {
        try {
            Files.createDirectories(file.getParent());
            JsonObject root = new JsonObject();
            for (Module module : ModuleManager.getInstance().all()) {
                root.add(module.getName(), module.serialize());
            }
            Files.writeString(file, GSON.toJson(root));
        } catch (IOException ignored) {
        }
    }

    public void load() {
        try {
            if (!Files.exists(file)) {
                return;
            }
            JsonObject root = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
            for (Module module : ModuleManager.getInstance().all()) {
                if (root.has(module.getName())) {
                    module.deserialize(root.getAsJsonObject(module.getName()));
                }
            }
        } catch (IOException ignored) {
        }
    }

    public Map<String, ModuleConfig> getRegisteredConfigs() {
        return configs;
    }
}
