package A;

import B.B;
import C.C;

protected class A {
  protected void meth(int a1) {}
  A(B b) {
    ((A)b).meth(2);
  }
}
