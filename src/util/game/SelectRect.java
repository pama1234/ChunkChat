package util.game;

import javax.swing.text.BadLocationException;

import app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;
import util.math.HexUtil;
import util.point.PathPoint;

public class SelectRect extends GameEntity{
  public static final int COOLING_SIZE=1,
    //  public static final int COOLING_SIZE=16,
    NORMAL_COLOR=0xff6fedfb,
    BUILD_COLOR=0xffffffff,
    DESTROY_COLOR=0xff000000,
    MAX_RADIUS=6;
  public static final float VIBRATION_AMP=1/16f,
    //    SCALE_FACTOR=1/1.25f;
    SCALE_FACTOR=1;
  public Ship parent;
  public PathPoint point;
  public WordRect subject;
  public int cooling;
  public boolean mouseAuto;
  public SelectRect(UtilApp p,UtilAppPage page,Ship parent) {
    super(p,page);
    this.parent=parent;
    point=new PathPoint(parent.point.pos.x,parent.point.pos.y);
  }
  public int getX() {
    return PApplet.round(point.pos.x);
  }
  public int getY() {
    return PApplet.round(point.pos.y);
  }
  //  public static float getWindowScale() {
  //    GraphicsDevice device=MouseInfo.getPointerInfo().getDevice();
  //    return device.getDisplayMode().getWidth()/(float)device.getDefaultConfiguration().getBounds().width;
  //  }
  @Override
  public void init() {}
  @Override
  public void update() {
    int tx=PApplet.round(parent.point.pos.x-0.5f),ty=PApplet.round(parent.point.pos.y-0.5f);
    if(p.grabMouse) {
      p.mouse.clampX(
        (int)(-MAX_RADIUS*parent.w.rectUnit*parent.cam.scale),
        (int)(MAX_RADIUS*parent.w.rectUnit*parent.cam.scale));
      p.mouse.clampY(
        (int)(-MAX_RADIUS*parent.w.rectUnit*parent.cam.scale),
        (int)(MAX_RADIUS*parent.w.rectUnit*parent.cam.scale));
      point.des.set(
        PApplet.floor((p.mouse.x)/(parent.w.rectUnit*parent.cam.scale)+parent.cam.pos.x),
        PApplet.floor((p.mouse.y)/(parent.w.rectUnit*parent.cam.scale)+parent.cam.pos.y));
      //      p.mouse.clampX(
      //        (int)(-MAX_RADIUS*parent.w.rectUnit),
      //        (int)(MAX_RADIUS*parent.w.rectUnit));
      //      p.mouse.clampY(
      //        (int)(-MAX_RADIUS*parent.w.rectUnit),
      //        (int)(MAX_RADIUS*parent.w.rectUnit));
      //      point.des.set(
      //        PApplet.floor((p.mouse.x)/(parent.w.rectUnit)+parent.cam.pos.x),
      //        PApplet.floor((p.mouse.y)/(parent.w.rectUnit)+parent.cam.pos.y));
    }else {
      point.des.set(
        PApplet.floor((p.mouseX-p.width/2)/(parent.w.rectUnit*parent.cam.scale)+parent.cam.pos.x),
        PApplet.floor((p.mouseY-p.height/2)/(parent.w.rectUnit*parent.cam.scale)+parent.cam.pos.y));
    }
    if(point.des.x<tx-MAX_RADIUS) point.des.x=(int)tx-MAX_RADIUS;
    if(point.des.x>tx+MAX_RADIUS) point.des.x=(int)tx+MAX_RADIUS;
    if(point.des.y<ty-MAX_RADIUS) point.des.y=(int)ty-MAX_RADIUS;
    if(point.des.y>ty+MAX_RADIUS) point.des.y=(int)ty+MAX_RADIUS;
    point.update();
    if(cooling>0) cooling--;
    subject=parent.w.getRect(getX(),getY());
    if(PApplet.dist(point.pos.x,point.pos.y,point.des.x,point.des.y)<0.5f&&subject!=null&&p.mousePressed&&cooling<=0) {
      if(p.mouseButton==PConstants.LEFT) {
        boolean tf=subject.getLife()>WordRect.N_LIFE_SIZE&&subject.getData()!=0;
        if(tf) {
          subject.addLife(-1);
          cooling=COOLING_SIZE;
        }
        //        if(mouseAuto&&(!tf||subject.getLife()<=WordRect.N_LIFE_SIZE/4f*3)) moveMouse(tx,ty);
      }else if(p.mouseButton==PConstants.RIGHT) {
        if(subject.getData()==0&&parent.text.getDocument().getLength()>0) {
          //          boolean tf=false;
          char tc=parent.text.getText().charAt(0);
          int a=(tc&0xf000)>>>12,b=(tc&0xf00)>>>8,c=(tc&0xf0)>>>4,d=tc&0xf;
          int[][] tpx=parent.inventory.data;
          boolean fa=a==0,fb=b==0,fc=c==0,fd=d==0;
          if((fa||tpx[3][a]>0)&&(fb||tpx[2][b]>0)&&(fc||tpx[1][c]>0)&&(fd||tpx[0][d]>0)) {
            if(!fa) tpx[3][a]--;
            if(!fb) tpx[2][b]--;
            if(!fc) tpx[1][c]--;
            if(!fd) tpx[0][d]--;
            subject.setData(tc);
            try {
              parent.text.getDocument().remove(0,1);
            }catch(BadLocationException e) {
              e.printStackTrace();
            }
            cooling=COOLING_SIZE;
          }
          //          else tf=true;
          //          if(mouseAuto&&(!tf||subject.getData()!=0)) moveMouse(tx,ty);
        }else {
          boolean tf=subject.getLife()<WordRect.LIFE_SIZE&&subject.getData()!=0;
          if(tf) {
            subject.addLife(1);
            cooling=COOLING_SIZE;
          }
          //          if(mouseAuto&&(!tf||subject.getData()==0)) moveMouse(tx,ty);
        }
      }
    }
  }
  //  public void moveMouse(int tx,int ty) {
  //    Point tp=MouseInfo.getPointerInfo().getLocation();
  //    float ts=parent.w.rectUnit*parent.w.cam.scale*SCALE_FACTOR;
  //    if(point.des.x>tx+MAX_RADIUS-1) {
  //      if(point.des.y>ty+MAX_RADIUS-1) parent.w.robot.mouseMove(tp.x-(int)(ts*MAX_RADIUS*2),tp.y-(int)(ts*MAX_RADIUS*2));
  //      else parent.w.robot.mouseMove(tp.x-(int)(ts*MAX_RADIUS*2),tp.y+(int)(ts));
  //    }else parent.w.robot.mouseMove(tp.x+(int)(ts),tp.y);
  //    cooling=COOLING_SIZE;
  //  }
  @Override
  public void display() {
    p.pushStyle();
    p.noFill();
    if(p.mousePressed) {
      if(p.mouseButton==PConstants.LEFT) p.stroke(DESTROY_COLOR,204-cooling*2);
      else if(p.mouseButton==PConstants.RIGHT) p.stroke(BUILD_COLOR,204-cooling*2);
      else p.stroke(NORMAL_COLOR,127-cooling*2);
    }else p.stroke(NORMAL_COLOR,127-cooling*2);
    p.strokeWeight(page.rectUnit/4f);
    float x=(point.pos.x+p.random(-VIBRATION_AMP,VIBRATION_AMP)*((float)cooling/COOLING_SIZE))*parent.w.rectUnit,
      y=(point.pos.y+p.random(-VIBRATION_AMP,VIBRATION_AMP)*((float)cooling/COOLING_SIZE))*parent.w.rectUnit;
    p.rect(x,y,
      parent.w.rectUnit,parent.w.rectUnit);
    //    p.stroke(p.g.strokeColor);
    p.line(x,y-parent.w.rectUnit/4,x+(1-(float)cooling/COOLING_SIZE)*parent.w.rectUnit,y-parent.w.rectUnit/4);
    //---
    String ts=subject==null?"0x0":subject.getData()+":0x"+HexUtil.format(subject.getData());
    p.textSize(parent.w.rectUnit/2f);
    float tw=p.textWidth(ts);
    p.fill((p.g.strokeColor&0xff)>127?63:203,191);
    p.strokeWeight(page.rectUnit/16f);
    p.rect(x+(parent.w.rectUnit-tw)/2,y+parent.w.rectUnit*1.125f,tw,parent.w.rectUnit/2f);
    p.fill(p.g.strokeColor|0xff000000);
    p.text(ts,x+parent.w.rectUnit/2,y+parent.w.rectUnit*1.25f);
    p.stroke(p.g.strokeColor&0x7fffffff);
    //---
    p.line(x,y,
      (parent.point.pos.x-0.5f)*parent.w.rectUnit,(parent.point.pos.y-0.5f)*parent.w.rectUnit);
    p.line(x+parent.w.rectUnit,y,
      (parent.point.pos.x+0.5f)*parent.w.rectUnit,(parent.point.pos.y-0.5f)*parent.w.rectUnit);
    p.line(x+parent.w.rectUnit,y+parent.w.rectUnit,
      (parent.point.pos.x+0.5f)*parent.w.rectUnit,(parent.point.pos.y+0.5f)*parent.w.rectUnit);
    p.line(x,y+parent.w.rectUnit,
      (parent.point.pos.x-0.5f)*parent.w.rectUnit,(parent.point.pos.y+0.5f)*parent.w.rectUnit);
    p.popStyle();
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
    if(p.key=='x') {
      if(p.grabMouse) p.ungrabMouse();
      else p.grabMouse();
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
