package app.util;

import app.UtilApp;

public class MethodRegistrant{
  UtilApp parent;
  public MethodRegistrant(UtilApp parent) {
    this.parent=parent;
    parent.registerMethod("post",this);
  }
  public void post() {
    System.out.println(parent.pmouse+" "+parent.mouse);
    parent.pmouse.set(parent.mouse);
    //    System.out.println("yes");
  }
}
