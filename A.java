package A;

import B.B;
import C.C;

protected class A {
  public int ainstance = 10;
  A(B b) {
    ((A)b).ainstance = 3;
  }
}
