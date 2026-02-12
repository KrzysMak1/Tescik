package dev.project.modules;

import dev.project.modules.combat.AntiKnockback;
import dev.project.modules.combat.AutoTotem;
import dev.project.modules.combat.CrystalAura;
import dev.project.modules.combat.KillAura;
import dev.project.modules.movement.Flight;
import dev.project.modules.movement.NoSlowdown;
import dev.project.modules.movement.Speed;
import dev.project.modules.utility.AutoTool;
import dev.project.modules.utility.InvManager;
import dev.project.modules.utility.Timer;
import dev.project.modules.visual.ESP;
import dev.project.modules.visual.Fullbright;
import dev.project.modules.visual.XRay;
import dev.project.modules.world.Nuker;
import dev.project.modules.world.Scaffold;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.MinecraftClient;

public final class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    public void registerAll() {
        modules.clear();
        modules.add(KillAura.INSTANCE);
        modules.add(AutoTotem.INSTANCE);
        modules.add(CrystalAura.INSTANCE);
        modules.add(AntiKnockback.INSTANCE);

        modules.add(Flight.INSTANCE);
        modules.add(Speed.INSTANCE);
        modules.add(NoSlowdown.INSTANCE);

        modules.add(ESP.INSTANCE);
        modules.add(Fullbright.INSTANCE);
        modules.add(XRay.INSTANCE);

        modules.add(Scaffold.INSTANCE);
        modules.add(Nuker.INSTANCE);

        modules.add(AutoTool.INSTANCE);
        modules.add(InvManager.INSTANCE);
        modules.add(Timer.INSTANCE);
    }

    public List<Module> all() {
        return Collections.unmodifiableList(modules);
    }

    public List<Module> byCategory(Category category) {
        return modules.stream().filter(module -> module.getCategory() == category).toList();
    }

    public Module find(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void onClientTick(MinecraftClient client) {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onClientTick(client);
            }
        }
    }
}
