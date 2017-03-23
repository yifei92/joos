global VTABLE$Main
VTABLE$Main:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global STATICMETHOD$Main$test@
STATICMETHOD$Main$test@:
  sub esp, 0
  ret

