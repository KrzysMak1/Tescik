package dev.project;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public final class CommandManager {
    private static final CommandManager INSTANCE = new CommandManager();
    public static CommandManager getInstance() { return INSTANCE; }

    public void register() {
        ClientCommandRegistrationCallback.EVENT.register((d, r) -> d.register(ClientCommandManager.literal("um")
            .then(ClientCommandManager.literal("toggle").then(ClientCommandManager.argument("module", StringArgumentType.string()).executes(ctx -> {
                var m = ModuleManager.getInstance().find(StringArgumentType.getString(ctx, "module"));
                if (m == null) return 0;
                m.toggle();
                ConfigStore.getInstance().save();
                return 1;
            })))));
    }
}
