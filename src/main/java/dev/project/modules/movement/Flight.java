package dev.project.modules.movement;

import com.google.gson.*;
import dev.project.configs.FlightConfig;
import dev.project.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public final class Flight extends Module {
    public static final Flight INSTANCE = new Flight();
    private final FlightConfig cfg = new FlightConfig(); private int tick; private Vec3d vel = Vec3d.ZERO; private double hoverY;
    private Flight(){ super("Flight", Category.MOVEMENT, new FlightConfig()); }
    @Override public void onEnable(){ var p=MinecraftClient.getInstance().player; if(p!=null) hoverY=p.getY(); }
    @Override public void onTick(MinecraftClient c){
        if(c.player==null||c.getNetworkHandler()==null) return; cfg.validate(); tick++;
        double up=(c.options.jumpKey.isPressed()?1:0)-(c.options.sneakKey.isPressed()?1:0);
        Vec3d forward=Vec3d.fromPolar(0, c.player.getYaw()).multiply(-cfg.speed);
        vel=vel.add(forward.multiply(cfg.acceleration)); vel=vel.multiply(cfg.friction,1,cfg.friction);
        switch(cfg.mode){
            case "creative","vanilla" -> { c.player.getAbilities().allowFlying=true; c.player.getAbilities().setFlySpeed((float)(cfg.speed*0.5)); c.player.setVelocity(vel.x,up*cfg.speed,vel.z); }
            case "packet" -> { c.player.setVelocity(0,0,0); c.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(c.player.getX()+vel.x,c.player.getY()+up*cfg.speed,c.player.getZ()+vel.z,false)); }
            case "glide" -> c.player.setVelocity(vel.x,-cfg.glideFallSpeed+up*cfg.speed*0.5,vel.z);
            case "hover" -> c.player.setVelocity(vel.x,(hoverY-c.player.getY())*0.2+up*cfg.speed,vel.z);
        }
        if(cfg.antiKick && tick%cfg.antiKickTicks==0) c.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(c.player.getX(),c.player.getY()-0.03125,c.player.getZ(),false));
    }
    @Override public void onDisable(){ var p=MinecraftClient.getInstance().player; if(p!=null){p.getAbilities().allowFlying=false; p.getAbilities().flying=false;} }
    @Override protected void applyConfig(JsonObject o){ new Gson().fromJson(o, FlightConfig.class).validate(); }
}
