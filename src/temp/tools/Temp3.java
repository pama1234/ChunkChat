package temp.tools;

import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer.Info;

public class Temp3{
  public static void main(String[] args) {
    Info[] temp=AudioSystem.getMixerInfo();
    System.out.println(Arrays.toString(temp));
  }
}
