package dev.project.modules.visual;

import com.google.gson.*;
import dev.project.configs.ESPConfig;
import dev.project.modules.*;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

public final class ESP extends Module {
    public static final ESP INSTANCE = new ESP();
    private final ESPConfig cfg = new ESPConfig();
    private ESP(){ super("ESP", Category.VISUAL, new ESPConfig()); }
    @Override public void onEnable(){ WorldRenderEvents.AFTER_ENTITIES.register(this::render); }
    private void render(WorldRenderContext ctx){ MinecraftClient c=MinecraftClient.getInstance(); if(c.player==null||c.world==null) return; c.world.getPlayers().stream().filter(p->p!=c.player&&p.distanceTo(c.player)<=cfg.maxRange).forEach(p->{ if(cfg.outline)p.setGlowing(true); }); }
    @Override public void onDisable(){ MinecraftClient c=MinecraftClient.getInstance(); if(c.world!=null) c.world.getPlayers().forEach(p->p.setGlowing(false)); }
    @Override protected void applyConfig(JsonObject o){ new Gson().fromJson(o, ESPConfig.class).validate(); }
}
