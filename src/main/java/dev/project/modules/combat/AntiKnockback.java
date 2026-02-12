package dev.project.modules.combat;

import com.google.gson.*;
import dev.project.configs.AntiKnockbackConfig;
import dev.project.modules.*;

public final class AntiKnockback extends Module {
    public static final AntiKnockback INSTANCE = new AntiKnockback();
    private final AntiKnockbackConfig cfg = new AntiKnockbackConfig();
    private AntiKnockback(){ super("AntiKnockback", Category.COMBAT, new AntiKnockbackConfig()); }
    public double h(){ return 1.0-(cfg.horizontal/100.0); } public double v(){ return 1.0-(cfg.vertical/100.0); }
    @Override protected void applyConfig(JsonObject o){ new Gson().fromJson(o, AntiKnockbackConfig.class).validate(); }
}
