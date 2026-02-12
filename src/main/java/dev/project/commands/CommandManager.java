package dev.project.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.project.modules.Module;
import dev.project.modules.ModuleManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public final class CommandManager {
    private static final CommandManager INSTANCE = new CommandManager();

    private CommandManager() {
    }

    public static CommandManager getInstance() {
        return INSTANCE;
    }

    public void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
            ClientCommandManager.literal("utility")
                .then(ClientCommandManager.literal("toggle")
                    .then(ClientCommandManager.argument("module", StringArgumentType.word())
                        .executes(ctx -> {
                            String moduleName = StringArgumentType.getString(ctx, "module");
                            Module module = ModuleManager.getInstance().find(moduleName);
                            if (module != null) {
                                module.toggle();
                                return 1;
                            }
                            return 0;
                        })))
        ));
    }
}
