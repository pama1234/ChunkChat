package util.game;

import java.util.LinkedList;

import app.UtilApp;
import processing.core.PApplet;
import processing.core.PConstants;
import ui.component.TextInput;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;
import util.math.Tools;

public class World extends GameEntity{
  //  Robot robot;
  //---
  public LinkedList<Chunk> chunkList;
  public LinkedList<Chunk> chunkAdded;
  public LinkedList<Chunk> chunkRemoved;
  public ChunkProvider chunkProvider;
  //---
  //  public LinkedList<Ship> shipList;
  //  public LinkedList<Ship> shipAdded;
  //  public LinkedList<Ship> shipRemoved;
  //---
  public Ship me;
  public Cam cam;
  public TextInput text;
  public ShipProvider shipProvider;
  //---
  public float rectUnit;
  public World(UtilApp p,UtilAppPage page,float seed) {
    super(p,page);
    frameResized(p.width,p.height);
    //---
    //    try {
    //      robot=new Robot();
    //    }catch(AWTException e) {
    //      e.printStackTrace();
    //    }
    //---
    chunkList=new LinkedList<Chunk>();
    chunkAdded=new LinkedList<Chunk>();
    chunkRemoved=new LinkedList<Chunk>();
    chunkProvider=new ChunkProvider(p,page,p.sketchPath()+"/data/saved/region/",this,seed);
    //---
    //    shipList=new LinkedList<Ship>();
    //    shipAdded=new LinkedList<Ship>();
    //    shipRemoved=new LinkedList<Ship>();
    shipProvider=new ShipProvider(p,page,p.sketchPath()+"/data/saved/player/",this);
  }
  public CharRect getRect(int x,int y) {
    for(Chunk c:chunkList) if(c.inBox(x,y)) return c.getRect(x,y);
    return null;
  }
  public Chunk getChunk(int x,int y) {
    for(Chunk c:chunkList) if(c.pos.x==x&&c.pos.y==y) return c;
    return null;
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    if(PApplet.abs(me.point.vel.x)>1e-24f||PApplet.abs(me.point.vel.y)>1e-24f) chunkUpdate();
    //---
    for(Chunk c:chunkRemoved) chunkProvider.saveChunk(c);
    //---
    chunkList.addAll(chunkAdded);
    chunkAdded.clear();
    chunkList.removeAll(chunkRemoved);
    chunkRemoved.clear();
    //---
    for(Chunk c:chunkList) c.update();
    //---
    me.select.update();
    me.inventory.update();
    me.update();
  }
  public void chunkUpdate() {
    for(int i=-2;i<=2;i++) {
      for(int j=-2;j<=2;j++) {
        int tx=PApplet.floor(me.point.pos.x/32+i)*32,ty=PApplet.floor(me.point.pos.y/32+j)*32;
        if(getChunk(tx,ty)==null&&Tools.intersects(tx,ty,32,32,me.point.pos.x-16,me.point.pos.y-16,32,32)) {
          chunkAdded.add(chunkProvider.loadChunk(tx,ty,32,32));
        }
      }
    }
    //---
    for(Chunk c:chunkList) if(!c.intersects(me.point.pos.x-32,me.point.pos.y-32,64,64)) chunkRemoved.add(c);
  }
  @Override
  public void display() {
    p.textSize(rectUnit);
    p.textAlign(PConstants.CENTER,PConstants.CENTER);
    for(Chunk c:chunkList) if(c.intersects(me.point.pos.x-16,me.point.pos.y-16,32,32)) c.display();
    me.select.display();
    p.textSize(rectUnit/3);
    me.display();
    p.textSize(rectUnit/4);
    me.inventory.display();
    if(p.grabMouse) {
      float tx=p.mouse.x/cam.scale+cam.pos.x*rectUnit,ty=p.mouse.y/cam.scale+cam.pos.y*rectUnit;
      float tsw=p.g.strokeWeight;
      p.strokeWeight(tsw*4);
      p.stroke(0xccffffff);
      p.line(tx,ty-rectUnit/4,tx,ty+rectUnit/4);
      p.stroke(0xcc000000);
      p.line(tx-rectUnit/4,ty,tx+rectUnit/4,ty);
      p.stroke(0xccCC0000);
      p.point(tx,ty);
      p.stroke(0);
      p.strokeWeight(tsw);
    }
  }
  @Override
  public void dispose() {}
  @Override
  public void start() {
    chunkUpdate();
  }
  @Override
  public void end() {
    for(Chunk c:chunkRemoved) chunkProvider.saveChunk(c);
    chunkList.removeAll(chunkRemoved);
    chunkRemoved.clear();
    chunkList.addAll(chunkAdded);
    chunkAdded.clear();
    for(Chunk c:chunkList) chunkProvider.saveChunk(c);
    chunkList.clear();
    chunkProvider.end();
  }
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
  public void frameResized(int w,int h) {
    rectUnit=PApplet.min(w,h)/10f;
  }
  @Override
  public void frameMoved(int x,int y) {}
}
