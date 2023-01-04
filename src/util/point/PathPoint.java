package util.point;

import util.math.Vec2f;

public class PathPoint{
  public Vec2f pos,des;
  public float f;
  {
    pos=new Vec2f();
    des=new Vec2f();
    f=0.2f;
  }
  public PathPoint(float a,float b) {
    des.set(a,b);
  }
  public void update() {
    pos.add((des.x-pos.x)*f,(des.y-pos.y)*f);
  }
}
