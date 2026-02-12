package dev.project;

import dev.project.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class UtilityMod implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "utilitymod";
    private static final ModuleManager MODULE_MANAGER = ModuleManager.getInstance();

    @Override
    public void onInitialize() {
        ConfigStore.getInstance().load();
        MODULE_MANAGER.registerAll();
        CommandManager.getInstance().register();
        ServerLifecycleEvents.SERVER_STOPPING.register(s -> ConfigStore.getInstance().save());
    }

    @Override
    public void onInitializeClient() {
        KeyBinding openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.utilitymod.clickgui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.utilitymod"));
        ClientTickEvents.START_CLIENT_TICK.register(client -> MODULE_MANAGER.tick(client));
        ClientTickEvents.END_CLIENT_TICK.register(client -> { while (openGui.wasPressed()) client.setScreen(new ClickGUI()); });
        ClientLifecycleEvents.CLIENT_STOPPING.register(c -> ConfigStore.getInstance().save());
    }
}
