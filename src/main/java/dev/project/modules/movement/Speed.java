package dev.project.modules.movement;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public final class Speed extends Module {
    public static final Speed INSTANCE = new Speed();
    private String mode="bunnyhop"; private double mult=1.3; private boolean autoJump=true;
    private Speed(){ super("Speed", Category.MOVEMENT, new dev.project.configs.ModuleConfig()); }
    @Override public void onTick(MinecraftClient c){ if(c.player==null) return; Vec3d v=c.player.getVelocity();
        if(autoJump && c.player.isOnGround() && (c.player.forwardSpeed!=0||c.player.sidewaysSpeed!=0)) c.player.jump();
        switch(mode){ case "strafe" -> c.player.setVelocity(v.x*mult, v.y, v.z*mult); case "yport" -> { if(c.player.isOnGround()) c.player.jump(); else c.player.setVelocity(v.x*mult, -0.2, v.z*mult);} case "ground","onGround" -> { if(c.player.isOnGround()) c.player.setVelocity(v.x*mult, v.y, v.z*mult);} default -> { if(!c.player.isOnGround()) c.player.setVelocity(v.x*mult, v.y, v.z*mult);} }
    }
    @Override protected void applyConfig(JsonObject o){ if(o.has("mode")) mode=o.get("mode").getAsString(); }
}
