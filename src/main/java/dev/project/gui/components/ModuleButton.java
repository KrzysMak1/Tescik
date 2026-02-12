package dev.project.gui.components;

import dev.project.modules.Module;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public final class ModuleButton {
    private final Module module; private final Panel panel; private final int offset;
    public ModuleButton(Module module, Panel panel, int offset){ this.module=module; this.panel=panel; this.offset=offset; }
    public void render(DrawContext c, TextRenderer tr, int mx, int my){ int y=panel.y+offset; c.fill(panel.x,y,panel.x+panel.w,y+12,module.isEnabled()?0xAA208020:0xAA802020); c.drawTextWithShadow(tr,module.getName(),panel.x+4,y+2,0xFFFFFF); }
    public boolean mouseClicked(double mx,double my,int b){ int y=panel.y+offset; if(mx>=panel.x&&mx<=panel.x+panel.w&&my>=y&&my<=y+12){ if(b==0) module.toggle(); return true;} return false; }
}
