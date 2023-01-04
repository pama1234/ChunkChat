package temp;

import app.UtilApp;
import ui.page.UtilAppPage;
import util.game.World;
import util.game.framework.GameEntity;
import util.math.HexUtil;
import util.point.CenterPoint;

public class HexDigitItem extends GameEntity{
  CenterPoint point;
  World w;
  int pointer,data;
  public HexDigitItem(UtilApp p,UtilAppPage page,World w,float x,float y,int pointer,int size) {
    super(p,page);
    this.w=w;
    point=new CenterPoint(x+p.random(-(pointer+1),(pointer+1)),y+p.random(-(pointer+1),(pointer+1)));
    point.des.set(x,y);
    point.f=1;
    point.s=0.001f;
    point.vel.add(p.random(-(pointer+1),(pointer+1))/16,p.random(-(pointer+1),(pointer+1))/16);
    this.pointer=pointer;
    this.data=size;
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    p.fill(0xffD2443F);
    p.rect((point.des.x-0.0625f)*w.rectUnit,(point.des.y-0.0625f)*w.rectUnit,w.rectUnit/8,w.rectUnit/8);
    //---
    float tx,ty;
    p.line(
      point.des.x*w.rectUnit,
      point.des.y*w.rectUnit,
      tx=point.pos.x*w.rectUnit,
      ty=point.pos.y*w.rectUnit);
    //---
    p.fill(0x7f28bdae);
    float ts=(pointer+1)*w.rectUnit/4;
    p.rect(tx-ts/2,ty-ts/2,ts,ts);
    //---
    p.fill(255);
    p.textSize(ts);
    p.text(HexUtil.index[data].word,point.pos.x*w.rectUnit,point.pos.y*w.rectUnit-p.textDescent());
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
  public void keyPressed() {}
  @Override
  public void keyReleased() {}
  @Override
  public void keyTyped() {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
