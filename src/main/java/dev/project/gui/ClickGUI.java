package dev.project.gui;

import dev.project.ConfigStore;
import dev.project.ModuleManager;
import dev.project.gui.components.Panel;
import dev.project.modules.Category;
import dev.project.modules.Module;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ClickGUI extends Screen {
    private final List<Panel> panels = new ArrayList<>();

    public ClickGUI() {
        super(Text.literal("UtilityMod"));
        int x = 20;
        for (Category category : Category.values()) {
            List<Module> modules = ModuleManager.getInstance().all().stream()
                .filter(module -> module.getCategory() == category)
                .sorted(Comparator.comparing(Module::getName))
                .toList();

            if (modules.isEmpty()) {
                continue;
            }

            panels.add(new Panel(category.name(), x, 20, 130, 18, modules));
            x += 140;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        for (Panel panel : panels) {
            panel.render(context, textRenderer, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Panel panel : panels) {
            if (panel.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Panel panel : panels) {
            panel.mouseReleased();
        }
        ConfigStore.getInstance().save();
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
