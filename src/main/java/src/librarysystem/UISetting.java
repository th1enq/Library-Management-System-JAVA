package src.librarysystem;

public class UISetting {

  private static UISetting instance = null;

  private boolean darkMode = false;
  private boolean vietnameseMode = false;
  private boolean time24formatMode = false;

  private UISetting() {
  }

  public static UISetting getInstance() {
    if (instance == null) {
      instance = new UISetting();
    }
    return instance;
  }

  public boolean getDarkMode() {
    return this.darkMode;
  }

  public boolean getVietNameseMode() {
    return this.vietnameseMode;
  }

  public boolean getTime24formatMode() {
    return this.time24formatMode;
  }

  public void setDarkMode(boolean darkMode) {
    this.darkMode = darkMode;
  }

  public void setVietnameseMode(boolean vietnameseMode) {
    this.vietnameseMode = vietnameseMode;
  }

  public void setTime24formatMode(boolean time24formatMode) {
    this.time24formatMode = time24formatMode;
  }
}
