package dev.project.modules.world;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.*;

public final class Nuker extends Module {
    public static final Nuker INSTANCE = new Nuker();
    private int radius=4; private String mode="flat";
    private Nuker(){ super("Nuker", Category.WORLD, new dev.project.configs.ModuleConfig()); }
    @Override public void onTick(MinecraftClient c){ if(c.player==null||c.world==null||c.interactionManager==null) return; BlockPos center=c.player.getBlockPos();
        for(int x=-radius;x<=radius;x++) for(int y=(mode.equals("flat")?-1:-radius);y<=1;y++) for(int z=-radius;z<=radius;z++) { BlockPos p=center.add(x,y,z); if(!c.world.isAir(p)){ c.interactionManager.attackBlock(p, Direction.UP); return; }}
    }
    @Override protected void applyConfig(JsonObject o){}
}
