package util.game;

import processing.core.PApplet;

public class CharRect{
  public static final int LIFE_SIZE=4,N_LIFE_SIZE=-8;
  Chunk parent;
  private char data;
  private boolean changed;
  private float life;
  public CharRect(Chunk parent,char data,boolean changed,float life) {
    this.parent=parent;
    this.data=data;
    this.changed=changed;
    this.life=life;
  }
  public char getData() {
    return data;
  }
  public void setData(char data) {
    changed=true;
    this.data=data;
    setLife(0);
  }
  public boolean isChanged() {
    return changed;
  }
  public float getLife() {
    return life;
  }
  public void setLife(float life) {
    if(data==0) return;
    this.life=life;
    life=PApplet.constrain(life,N_LIFE_SIZE,LIFE_SIZE);
  }
  public void addLife(float in) {
    if(data==0) return;
    this.life+=in;
    life=PApplet.constrain(life,N_LIFE_SIZE,LIFE_SIZE);
  }
  public void clear() {
    data=0;
    changed=true;
    life=LIFE_SIZE;
  }
}
