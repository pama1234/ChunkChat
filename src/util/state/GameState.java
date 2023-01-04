package util.state;

public enum GameState{
  home,settings,game;
  public static GameState getDefault() {
    return home;
  }
}