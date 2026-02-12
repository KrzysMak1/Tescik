package dev.project.gui.components;

import dev.project.modules.Module;
import java.util.*;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public final class Panel {
    private final String title; public int x,y,w,h; private boolean dragging; private int dragX,dragY; private final List<ModuleButton> buttons=new ArrayList<>();
    public Panel(String title,int x,int y,int w,int h,List<Module> mods){ this.title=title; this.x=x; this.y=y; this.w=w; this.h=h; int oy=h; for(var m:mods){ buttons.add(new ModuleButton(m, this, oy)); oy+=14; } }
    public void render(DrawContext c, TextRenderer tr, int mx,int my){ if(dragging){x=mx-dragX;y=my-dragY;} c.fill(x,y,x+w,y+h,0xCC262626); c.drawTextWithShadow(tr,title,x+4,y+5,0xFFFFFF); for(var b:buttons) b.render(c,tr,mx,my); }
    public boolean mouseClicked(double mx,double my,int button){ if(mx>=x&&mx<=x+w&&my>=y&&my<=y+h){ if(button==0){dragging=true;dragX=(int)mx-x;dragY=(int)my-y;} return true;} for(var b:buttons) if(b.mouseClicked(mx,my,button)) return true; return false; }
    public void mouseReleased(){ dragging=false; }
}
