package util.point;

import util.math.Vec2f;

public class MassPoint{
  public Vec2f pos,vel;
  public float f;
  {
    pos=new Vec2f();
    vel=new Vec2f();
    f=0.8f;
  }
  public MassPoint(float a,float b) {
    pos.set(a,b);
  }
  public void update() {
    pos.add(vel);
    if(f!=1) vel.scale(f);
  }
}
