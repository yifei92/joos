package B;

public class B {
  public static int a = B.init() + B.init() - B.init();
  public static int b = B.init() - B.init();

  public static int init() {
    return 1;
  }

  B() {
  }
}
