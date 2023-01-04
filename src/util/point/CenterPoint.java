package util.point;

import util.math.Vec2f;

public class CenterPoint extends MassPoint{
  public Vec2f des;
  public float s;
  {
    des=new Vec2f();
    s=0.2f;
  }
  public CenterPoint(float a,float b) {
    super(a,b);
    des.set(a,b);
  }
  @Override
  public void update() {
    super.update();
    vel.add((des.x-pos.x)*s,(des.y-pos.y)*s);
  }
}
