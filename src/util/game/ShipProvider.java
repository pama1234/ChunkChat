package util.game;

import java.util.LinkedList;

import app.UtilApp;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;

public class ShipProvider extends GameEntity{
  public static final boolean DEBUG_MODE=true;
  //  public static final boolean DEBUG_MODE=true;
  public static final byte CHAR_TYPE=0,LIFE_TYPE=1;
  public String path;
  public World parent;
  public LinkedList<Ship> cache;
  public int cacheSize;
  public LinkedList<Byte> saveByteCache;
  public int debugCount;
  public ShipProvider(UtilApp p,UtilAppPage page,String path,World parent) {
    super(p,page);
    this.path=path;
    this.parent=parent;
    cache=new LinkedList<Ship>();
    cacheSize=DEBUG_MODE?64:32;
    saveByteCache=new LinkedList<Byte>();
  }
  public void saveShip(Ship in) {
    cache.add(in);
    while(cacheSize<cache.size()) innerSave(cache.removeFirst());
  }
  public void innerSave(Ship in) {
    if(DEBUG_MODE) return;
    byte[] out=new byte[saveByteCache.size()];
    for(int i=0;i<out.length;i++) out[i]=saveByteCache.get(i);
    saveByteCache.clear();
    p.saveBytes(path+in.name+".bytes",out);
  }
  public Ship loadShip(String in) {
    for(Ship s:cache) if(s.name.equals(in)) {
      cache.remove(s);
      return s;
    }
    Ship out=innerLoad(in);
    out.init();
    return out;
  }
  public Ship innerLoad(String in) {
    Ship out=new Ship(p,page,parent,parent.text,in,debugCount++,0,0);
    if(DEBUG_MODE) return out;
    return null;
  }
  @Override
  public void init() {}
  @Override
  public void update() {}
  @Override
  public void display() {}
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
