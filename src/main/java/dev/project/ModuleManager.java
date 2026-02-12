package dev.project;

import dev.project.modules.Module;
import dev.project.modules.combat.*;
import dev.project.modules.exploit.*;
import dev.project.modules.movement.*;
import dev.project.modules.utility.*;
import dev.project.modules.visual.*;
import dev.project.modules.world.*;
import java.util.*;
import net.minecraft.client.MinecraftClient;

public final class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> modules = new ArrayList<>();
    public static ModuleManager getInstance() { return INSTANCE; }

    public void registerAll() {
        modules.clear();
        modules.addAll(List.of(KillAura.INSTANCE, AutoTotem.INSTANCE, CrystalAura.INSTANCE, AntiKnockback.INSTANCE,
            Flight.INSTANCE, Speed.INSTANCE, NoSlowdown.INSTANCE,
            ESP.INSTANCE, Fullbright.INSTANCE, XRay.INSTANCE,
            Scaffold.INSTANCE, Nuker.INSTANCE,
            AutoTool.INSTANCE, InvManager.INSTANCE, Timer.INSTANCE,
            FakeLag.INSTANCE, Backtrack.INSTANCE));
    }

    public void tick(MinecraftClient c) { for (Module m: modules) if (m.isEnabled()) m.onTick(c); }
    public List<Module> all() { return Collections.unmodifiableList(modules); }
    public Module find(String n) { return modules.stream().filter(m -> m.getName().equalsIgnoreCase(n)).findFirst().orElse(null); }
}
