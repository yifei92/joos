package C;

import B.B;

public class C {
  public static int a = C.init() + C.init();
  public static int b = C.init() + C.init();

  public static int init() {
    return 2;
  }

  C() {
  }

  public static int test() {
	int a=10;
    return -a;
  }
}
