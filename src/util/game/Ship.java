package util.game;

import app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;
import ui.component.TextInput;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;
import util.math.Tools;
import util.point.MassPoint;

public class Ship extends GameEntity{
  public static final int MOVE_UP=0,
    MOVE_DOWN=1,
    MOVE_LEFT=2,
    MOVE_RIGHT=3;
  //---
  public World w;
  //---
  public MassPoint point;
  public Cam cam;
  public SelectRect select;
  public Inventory inventory;
  public TextInput text;
  //---
  public char[] keyBind;
  public boolean[] control;
  //---
  public float moveSpeed;
  public int cooling;
  //---
  public String name;
  public int id;
  public Ship(UtilApp p,UtilAppPage page,World w,TextInput text,String name,int id,float a,float b) {
    super(p,page);
    this.w=w;
    point=new MassPoint(a,b);
    cam=new Cam(0,0);
    select=new SelectRect(p,page,this);
    inventory=new Inventory(p,page,this);
    this.text=text;
    cam.des=point.pos;
    keyBind=new char[] {'w','s','a','d',' '};
    control=new boolean[keyBind.length];
    moveSpeed=0.125f;
    this.name=name;
    this.id=id;
  }
  @Override
  public void init() {}
  @Override
  public void display() {
    p.fill(0xcbffffff);
    if(p.mousePressed) {
      if(p.mouseButton==PConstants.LEFT) p.stroke(SelectRect.DESTROY_COLOR);
      else if(p.mouseButton==PConstants.RIGHT) p.stroke(SelectRect.BUILD_COLOR);
      else p.stroke(SelectRect.NORMAL_COLOR);
    }else p.stroke(SelectRect.NORMAL_COLOR);
    p.rect((point.pos.x-0.5f)*w.rectUnit,(point.pos.y-0.5f)*w.rectUnit,w.rectUnit,w.rectUnit);
    if(control[4]||cooling>0) {
      p.fill(cooling/60f*255,0,0);
      p.rect(
        (point.pos.x-0.125f+p.random(-cooling,cooling)/960)*w.rectUnit,
        (point.pos.y-0.125f+p.random(-cooling,cooling)/960)*w.rectUnit,
        w.rectUnit/4,w.rectUnit/4);
    }
    p.stroke(0);
    //--
    if(name!=null) {
      float tw=p.textWidth(name);
      p.fill(0,127);
      p.rect(
        point.pos.x*w.rectUnit-tw/2-p.g.textSize/2,
        (point.pos.y-1.25f)*w.rectUnit-p.g.textSize/2,
        tw+p.g.textSize,p.g.textSize*1.5f);
      p.fill(255);
      p.text(name,point.pos.x*w.rectUnit,(point.pos.y-1.25f)*w.rectUnit);
    }
    //---
    String ts="<"+
      Tools.cutToLastDigit(point.pos.x)+
      ","+
      Tools.cutToLastDigit(point.pos.y)+
      ">";
    float tw=p.textWidth(ts);
    p.fill(0,127);
    p.rect(
      point.pos.x*w.rectUnit-tw/2-p.g.textSize/2,
      (point.pos.y+1)*w.rectUnit-p.g.textSize/2,
      tw+p.g.textSize,p.g.textSize*1.5f);
    p.fill(255);
    p.text(ts,point.pos.x*w.rectUnit,(point.pos.y+1)*w.rectUnit);
  }
  @Override
  public void update() {
    if(control[MOVE_UP]) point.vel.y-=moveSpeed;
    if(control[MOVE_DOWN]) point.vel.y+=moveSpeed;
    if(control[MOVE_LEFT]) point.vel.x-=moveSpeed;
    if(control[MOVE_RIGHT]) point.vel.x+=moveSpeed;
    //---
    point.update();
    cam.update();
    //---
    if(control[4]&&select.subject!=null&&select.subject.getData()!=0&&select.subject.getLife()<=0) {
      for(int i=0;i<inventory.data.length;i++) {
        //        inventory.data[i][(select.subject.getData()>>>(i*4))&0xf]++;
        int tp=(select.subject.getData()>>>(i*4))&0xf;
        if(tp!=0) inventory.data[i][tp]++;
      }
      text.insert(String.valueOf(select.subject.getData()),text.getDocument().getLength());
      select.subject.setData((char)0);
      cooling=60;
    }
    if(cooling>0) cooling--;
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
  public void mouseWheel(int c) {
    cam.scale+=c/8f;
    cam.scale=PApplet.constrain(cam.scale,0.125f,4);
  }
  @Override
  public void keyPressed() {
    char tc=Character.toLowerCase(p.key);
    for(int i=0;i<keyBind.length;i++) if(keyBind[i]==tc) control[i]=true;
  }
  @Override
  public void keyReleased() {
    char tc=Character.toLowerCase(p.key);
    for(int i=0;i<keyBind.length;i++) if(keyBind[i]==tc) control[i]=false;
  }
  @Override
  public void keyTyped() {}
  @Override
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
