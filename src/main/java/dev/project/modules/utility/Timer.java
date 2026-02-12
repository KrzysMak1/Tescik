package dev.project.modules.utility;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public final class Timer extends Module {
    public static final Timer INSTANCE = new Timer();
    private String packetMode="tick"; private double multiplier=1.4;
    private Timer(){ super("Timer", Category.UTILITY, new dev.project.configs.ModuleConfig()); }
    @Override public void onTick(MinecraftClient c){ if(c.player==null||c.getNetworkHandler()==null) return; int burst=Math.max(0,(int)Math.floor(multiplier-1.0)); if(packetMode.equals("full")) burst+=1; for(int i=0;i<burst;i++) c.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(c.player.isOnGround())); }
    @Override protected void applyConfig(JsonObject o){}
}
