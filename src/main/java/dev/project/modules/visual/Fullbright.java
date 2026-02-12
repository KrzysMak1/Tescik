package dev.project.modules.visual;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import net.minecraft.client.MinecraftClient;

public final class Fullbright extends Module {
    public static final Fullbright INSTANCE = new Fullbright();
    private double prev=1.0;
    private Fullbright(){ super("Fullbright", Category.VISUAL, new dev.project.configs.ModuleConfig()); }
    @Override public void onEnable(){ var c=MinecraftClient.getInstance(); prev=c.options.getGamma().getValue(); c.options.getGamma().setValue(16.0); }
    @Override public void onDisable(){ MinecraftClient.getInstance().options.getGamma().setValue(prev); }
    @Override protected void applyConfig(JsonObject o){}
}
