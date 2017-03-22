global VTABLE$A.A
VTABLE$A.A:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$A.A$instanceMethod@

global METHOD$A.A$instanceMethod@
METHOD$A.A$instanceMethod@:
  sub esp, 0
  ret

global STATICMETHOD$A.A$staticMethod@
STATICMETHOD$A.A$staticMethod@:
  sub esp, 0
  ret

