package dev.project.modules.movement;

import com.google.gson.JsonObject;
import dev.project.modules.*;

public final class NoSlowdown extends Module {
    public static final NoSlowdown INSTANCE = new NoSlowdown();
    public boolean items=true, soulsand=true, web=true;
    private NoSlowdown(){ super("NoSlowdown", Category.MOVEMENT, new dev.project.configs.ModuleConfig()); }
    @Override protected void applyConfig(JsonObject o){}
}
