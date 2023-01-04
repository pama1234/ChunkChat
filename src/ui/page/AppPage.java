package ui.page;

import app.UtilApp;
import util.state.StateEngine;

public abstract class AppPage implements Page{
  public UtilApp p;
  public StateEngine state;
  public AppPage(UtilApp parent,StateEngine state) {
    this.p=parent;
    this.state=state;
  }
}
