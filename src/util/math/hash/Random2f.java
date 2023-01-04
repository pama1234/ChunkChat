package util.math.hash;

import processing.core.PApplet;

public class Random2f extends HashFunction2f{
  public Random2f(float seed) {
    super(seed);
  }
  @Override
  public float get(float x,float y) {
    float temp=PApplet.sin(x*12.9898f+y*78.233f)*(43758.5453123f+seed);
    return (temp-(int)temp)/2f+0.5f;
  }
}
