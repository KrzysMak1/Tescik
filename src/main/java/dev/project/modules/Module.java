package dev.project.modules;

import com.google.gson.*;
import dev.project.ConfigStore;
import dev.project.configs.ModuleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public abstract class Module {
    private final String name;
    private final Category category;
    private boolean enabled;
    private final ModuleConfig config;
    protected KeyBinding keybind;

    protected Module(String name, Category category, ModuleConfig config) {
        this.name = name; this.category = category; this.config = config;
    }
    public String getName(){return name;} public Category getCategory(){return category;} public boolean isEnabled(){return enabled;}
    public ModuleConfig getConfig(){return config;}
    public void toggle(){enabled=!enabled; if(enabled) onEnable(); else onDisable(); ConfigStore.getInstance().save();}
    public void onEnable(){} public void onDisable(){} public void onTick(MinecraftClient c){}

    public JsonObject serialize() {
        JsonObject o = new JsonObject();
        o.addProperty("enabled", enabled);
        o.add("config", JsonParser.parseString(new Gson().toJson(config)));
        return o;
    }
    public void deserialize(JsonObject o) {
        if (o.has("enabled")) enabled = o.get("enabled").getAsBoolean();
        if (o.has("config")) applyConfig(o.getAsJsonObject("config"));
    }
    protected abstract void applyConfig(JsonObject cfg);
}
