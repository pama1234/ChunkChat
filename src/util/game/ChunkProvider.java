package util.game;

import java.util.LinkedList;

import app.UtilApp;
import ui.page.UtilAppPage;
import util.game.framework.GameEntity;
import util.math.hash.PerlinNoise2f;
import util.math.hash.Random2f;

public class ChunkProvider extends GameEntity{
  public static final boolean DEBUG_MODE=false;
  //  public static final boolean DEBUG_MODE=true;
  public static final byte CHAR_TYPE=0,LIFE_TYPE=1;
  public String path;
  public World parent;
  public PerlinNoise2f noise;
  public LinkedList<Chunk> cache;
  public int cacheSize;
  public LinkedList<Byte> saveByteCache;
  public ChunkProvider(UtilApp p,UtilAppPage page,String path,World parent,float seed) {
    super(p,page);
    this.path=path;
    this.parent=parent;
    noise=new PerlinNoise2f(0,new Random2f(seed));
    cache=new LinkedList<Chunk>();
    cacheSize=DEBUG_MODE?64:32;
    saveByteCache=new LinkedList<Byte>();
  }
  public Chunk loadChunk(int x,int y,int w,int h) {
    for(Chunk c:cache) if(c.pos.x==x&&c.pos.y==y&&c.data[0].length==w&&c.data.length==h) {
      cache.remove(c);
      return c;
    }
    Chunk out=innerLoad(x,y,w,h);
    out.init();
    return out;
  }
  public void saveChunk(Chunk in) {
    cache.add(in);
    while(cacheSize<cache.size()) innerSave(cache.removeFirst());
  }
  public Chunk innerLoad(int x,int y,int w,int h) {
    Chunk out=new Chunk(parent.p,parent.page,parent,x,y,w,h);
    if(DEBUG_MODE) return out;
    byte[] temp=p.loadBytes(path+"r."+x+"."+y+"."+w+"."+h+".bytes");
    if(temp!=null) {
      for(int i=0;i<temp.length;i+=7) {
        int tx=temp[i+6],ty=temp[i+5];
        switch(temp[i]) {
          case CHAR_TYPE:
            out.data[tx][ty]=new CharRect(
              out,getChar(temp[i+1],temp[i+2]),
              true,temp[i+3]+(temp[i+4]&0xff)/128f);
            break;
          case LIFE_TYPE:
            out.data[tx][ty]=new CharRect(
              out,getChar(temp[i+1],temp[i+2]),
              false,temp[i+3]+(temp[i+4]&0xff)/128f);
            break;
        }
      }
    }
    return out;
  }
  public char getChar(byte a,byte b) {
    return (char)(((a&0xff)<<8)|(b&0xff));
  }
  public void innerSave(Chunk in) {
    if(DEBUG_MODE) return;
    for(int i=0;i<in.data.length;i++) {
      for(int j=0;j<in.data[i].length;j++) {
        CharRect w=in.data[i][j];
        if(w.isChanged()) {
          saveByteCache.add(CHAR_TYPE);
          //---
          saveByteCache.add((byte)((w.getData()>>>8)&0xff));
          saveByteCache.add((byte)(w.getData()&0xff));
          //---
          saveByteCache.add((byte)((int)w.getLife()&0xff));
          saveByteCache.add((byte)(w.getLife()*256));
          //---
          saveByteCache.add((byte)(j&0xff));
          saveByteCache.add((byte)(i&0xff));
        }else if(w.getLife()!=CharRect.LIFE_SIZE) {
          saveByteCache.add(LIFE_TYPE);
          //---
          saveByteCache.add((byte)((w.getData()>>>8)&0xff));
          saveByteCache.add((byte)(w.getData()&0xff));
          //---
          saveByteCache.add((byte)((int)w.getLife()&0xff));
          saveByteCache.add((byte)(w.getLife()*256));
          //---
          saveByteCache.add((byte)(j&0xff));
          saveByteCache.add((byte)(i&0xff));
        }
      }
    }
    //    if(saveByteCache.size()==0) return;
    byte[] out=new byte[saveByteCache.size()];
    for(int i=0;i<out.length;i++) out[i]=saveByteCache.get(i);
    saveByteCache.clear();
    p.saveBytes(path+"r."+in.pos.x+"."+in.pos.y+"."+in.data[0].length+"."+in.data.length+".bytes",out);
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
  public void end() {
    for(Chunk c:cache) innerSave(c);
    cache.clear();
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
  public void frameResized(int w,int h) {}
  @Override
  public void frameMoved(int x,int y) {}
}
