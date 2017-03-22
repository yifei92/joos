package A;

import B.B;
import C.C;

protected class A {
  protected void meth() {}
  A(B b) {
    ((A)b).meth();
  }
}
