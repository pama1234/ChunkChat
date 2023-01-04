package util.game;

import app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;
import util.math.HexUtil;
import util.math.Tools;
import util.math.Vec2i;

public class Chunk extends GameEntity{
  public static final int FLOAT_TO_CHAR=(1<<16),
    FLOAT_TO_ASICC_MAX=(1<<7),FLOAT_TO_ASICC_MIN=(1<<5);
  World w;
  Vec2i pos;
  WordRect[][] data;
  ChunkProvider parent;
  public Chunk(UtilApp p,UtilAppPage page,World world,int x,int y,int wIn,int h) {
    super(p,page);
    this.w=world;
    parent=world.chunkProvider;
    pos=new Vec2i(x,y);
    data=new WordRect[h][wIn];
  }
  public boolean intersects(float x,float y,float w,float h) {
    return Tools.intersects(pos.x,pos.y,data[0].length,data.length,x,y,w,h);
  }
  public WordRect getRect(int x,int y) {
    return data[y-pos.y][x-pos.x];
  }
  public boolean inBox(int a,int b) {
    return Tools.inBox(a,b,pos.x-1,pos.y-1,data[0].length+1,data.length+1);
  }
  @Override
  public void init() {
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        if(data[i][j]==null) data[i][j]=new WordRect(
          this,(char)(PApplet.map(
            parent.noise.get((pos.x+j)/8f,(pos.y+i)/8f),
            //            0,1,FLOAT_TO_ASICC_MIN,FLOAT_TO_ASICC_MAX)),
            0,1,0,FLOAT_TO_CHAR)),
          false,WordRect.LIFE_SIZE);
      }
    }
  }
  @Override
  public void update() {
    for(WordRect[] wordRects:data) {
      for(WordRect wordRect:wordRects) {
        wordRect.addLife(0.001953125f*PApplet.pow(wordRect.getData(),0.125f)*4);
      }
    }
  }
  @Override
  public void display() {
    p.noFill();
    p.rect(pos.x*w.rectUnit,pos.y*w.rectUnit,w.rectUnit*data[0].length,w.rectUnit*data.length);
    for(int i=0;i<data.length;i++) {
      for(int j=0;j<data[i].length;j++) {
        WordRect word;
        if((word=data[i][j]).getData()==0) continue;
        float x=j+0.5f+pos.x,y=i+0.5f+pos.y,z;
        float tw=(p.width/w.cam.scale)/w.rectUnit,th=(p.height/w.cam.scale)/w.rectUnit;
        if(Tools.inBox(x,y,
          w.cam.pos.x-tw/2-1,
          w.cam.pos.y-th/2-1,
          tw+2,th+2)&&
          (z=PApplet.dist(w.me.point.pos.x,w.me.point.pos.y,x,y))<16) {
          if(word.getLife()>0) {
            if(word.getLife()!=WordRect.LIFE_SIZE) {
              float temp=w.rectUnit*word.getLife()/WordRect.LIFE_SIZE;
              p.fill(0);
              p.rect(
                (j+pos.x)*w.rectUnit,
                (i+pos.y)*w.rectUnit,
                w.rectUnit,w.rectUnit);
              p.fill(p.g.backgroundColor);
              p.rect(
                (j+pos.x)*w.rectUnit+(w.rectUnit-temp)/2,
                (i+pos.y)*w.rectUnit+(w.rectUnit-temp)/2,
                temp,temp);
            }
            float ta=PApplet.map(z,12,16,255,63);
            if(word.isChanged()) p.fill(255,ta);
            else p.fill(0xff8BD6D6,ta);
            p.text(
              word.getData(),
              x*w.rectUnit,
              y*w.rectUnit-page.textDescent);
          }else {
            p.noFill();
            float ts=PApplet.map(PApplet.sq(word.getLife()),PApplet.sq(WordRect.N_LIFE_SIZE),0,w.rectUnit,0);
            if(ts>p.g.strokeWeight/2) p.ellipse(x*w.rectUnit,y*w.rectUnit,ts,ts);
            else p.point(x*w.rectUnit,y*w.rectUnit);
            for(int a=0;a<4;a++) {
              p.fill(HexUtil.index[(word.getData()>>>(a*4))&0xf].color,ts/w.rectUnit*512+64);
              p.stroke(p.g.fillColor);
              float tp=PApplet.sq(ts/4)/16+a*PConstants.HALF_PI;
              p.ellipse(
                x*w.rectUnit+
                  PApplet.sin(tp)*ts*0.4f,
                y*w.rectUnit+
                  PApplet.cos(tp)*ts*0.4f,
                w.rectUnit/(24-a*4),w.rectUnit/(24-a*4));
            }
            p.stroke(0);
            p.fill(255);
            p.textSize(w.rectUnit/4);
            p.text(
              "0x"+HexUtil.format(word.getData()),
              x*w.rectUnit,
              (y+0.5f)*w.rectUnit-page.textDescent);
            //---
            float ta=PApplet.map(z,0,16,127,0);
            if(word.isChanged()) p.fill(255,ta);
            else p.fill(0xff8BD6D6,ta);
            p.textSize(w.rectUnit);
            p.text(
              word.getData(),
              x*w.rectUnit,
              y*w.rectUnit-page.textDescent);
          }
        }
      }
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
  public void keyPressed() {}
  @Override
  public void keyReleased() {}
  @Override
  public void keyTyped() {}
  @Override
  public void frameResized(int wIn,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
