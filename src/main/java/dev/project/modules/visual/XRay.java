package dev.project.modules.visual;

import com.google.gson.*;
import dev.project.modules.*;
import java.util.*;
import net.minecraft.client.MinecraftClient;

public final class XRay extends Module {
    public static final XRay INSTANCE = new XRay();
    private final Set<String> blocks = new HashSet<>(Set.of("minecraft:diamond_ore","minecraft:ancient_debris"));
    private XRay(){ super("XRay", Category.VISUAL, new dev.project.configs.ModuleConfig()); }
    public boolean allowed(String id){ return blocks.contains(id); }
    @Override public void onEnable(){ var c=MinecraftClient.getInstance(); if(c.worldRenderer!=null) c.worldRenderer.reload(); }
    @Override public void onDisable(){ var c=MinecraftClient.getInstance(); if(c.worldRenderer!=null) c.worldRenderer.reload(); }
    @Override protected void applyConfig(JsonObject o){ if(o.has("blocks")){ blocks.clear(); o.getAsJsonArray("blocks").forEach(e->blocks.add(e.getAsString())); } }
}
