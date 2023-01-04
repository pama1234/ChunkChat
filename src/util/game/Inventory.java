package util.game;

import app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;
import util.math.HexUtil;
import util.math.Vec2i;

public class Inventory extends GameEntity{
  public static final float PI_8=PConstants.PI/8;
  Ship parent;
  int[][] data;
  int rotate;
  boolean display;
  Vec2i select;
  public Inventory(UtilApp p,UtilAppPage page,Ship parent) {
    super(p,page);
    this.parent=parent;
    data=new int[4][16];
    select=new Vec2i();
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    //    if(p.frameCount%4==0)
    rotate++;
    if(rotate>=2880) rotate=0;
    if(display) {
      //      select.y%=data.length;
      //      if(select.y<0) select.y+=data.length;
      select.clampY(0,data.length-1);
      select.x%=data[select.y].length;
      if(select.x<0) select.x+=data[select.y].length;
    }
  }
  @Override
  public void display() {
    if(display) {
      float tx=(parent.point.pos.x)*parent.w.rectUnit,ty=(parent.point.pos.y)*parent.w.rectUnit;
      for(int i=0;i<data.length;i++) {
        float ts=parent.w.rectUnit*2*(i+2);
        p.noFill();
        p.stroke(0x3f000000);
        p.ellipse(tx,ty,ts,ts);
        for(int j=0;j<data[i].length;j++) {
          float tp=rotate/8f*(i+1)/360f*PConstants.TWO_PI+j*PI_8;
          float ex=tx+PApplet.sin(tp)*ts*0.5f,ey=ty+PApplet.cos(tp)*ts*0.5f,es=parent.w.rectUnit/8*(i+1);
          if(i==select.y&&j==select.x) {
            p.fill((~HexUtil.index[j].color)|0xff000000,191);
            p.stroke(p.g.fillColor);
            p.line(tx,ty,ex,ey);
            p.rect(ex-es*1.5f,ey-es*1.5f,es*3,es*3);
          }
          p.fill(HexUtil.index[j].color,203);
          p.stroke(p.g.fillColor,127);
          p.ellipse(ex,ey,es,es);
          p.noFill();
          p.stroke(p.g.strokeColor|0xff000000);
          for(int c=(data[i][j]+7)/8;c>0;c--) {
            float cs=es+parent.w.rectUnit/8*(1+c);
            p.ellipse(ex,ey,cs,cs);
          }
          p.fill(255);
          p.text(HexUtil.index[j].word+":"+data[i][j],ex,ey);
        }
      }
      p.stroke(0);
    }
  }
  @Override
  public void dispose() {}
  @Override
  public void start() {}
  @Override
  public void end() {}
  @Override
  public void mousePressed() {}
  @Override
  public void mouseReleased() {}
  @Override
  public void mouseClicked() {}
  @Override
  public void mouseMoved() {}
  @Override
  public void mouseDragged() {}
  @Override
  public void mouseWheel(int c) {}
  @Override
  public void keyPressed() {
    if(p.key=='e') display=!display;
    else if(display) {
      if(p.keyCode==PConstants.UP) select.y++;
      else if(p.keyCode==PConstants.DOWN) select.y--;
      else if(p.keyCode==PConstants.LEFT) select.x++;
      else if(p.keyCode==PConstants.RIGHT) select.x--;
    }
  }
  @Override
  public void keyReleased() {}
  @Override
  public void keyTyped() {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
