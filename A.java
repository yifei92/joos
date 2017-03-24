package A;

import B.B;
import C.C;

protected class A {
  A() {
    B b = new C();
    b.func();
  }
}
