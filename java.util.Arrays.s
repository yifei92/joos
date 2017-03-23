global VTABLE$java.util.Arrays
VTABLE$java.util.Arrays:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global STATICMETHOD$java.util.Arrays$equals@boolean[]#boolean[]#
STATICMETHOD$java.util.Arrays$equals@boolean[]#boolean[]#:
  sub esp, 8
  ret

global STATICMETHOD$java.util.Arrays$equals@char[]#char[]#
STATICMETHOD$java.util.Arrays$equals@char[]#char[]#:
  sub esp, 8
  ret

