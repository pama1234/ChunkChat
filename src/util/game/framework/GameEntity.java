package util.game.framework;

import app.UtilApp;
import ui.page.UtilAppPage;

public abstract class GameEntity implements Entity{
  public UtilApp p;
  public UtilAppPage page;
  public GameEntity(UtilApp p,UtilAppPage page) {
    this.p=p;
    this.page=page;
    page.listenerList.add(this);
  }
}
