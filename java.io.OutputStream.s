global VTABLE$java.io.OutputStream
VTABLE$java.io.OutputStream:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.io.OutputStream$write@char#
  dd METHOD$java.io.OutputStream$write@int#
  dd METHOD$java.io.OutputStream$flush@

global METHOD$java.io.OutputStream$write@char#
METHOD$java.io.OutputStream$write@char#:
  sub esp, 4
  ret

global METHOD$java.io.OutputStream$write@int#
METHOD$java.io.OutputStream$write@int#:
  sub esp, 4
  ret

global STATICMETHOD$java.io.OutputStream$nativeWrite@int#
STATICMETHOD$java.io.OutputStream$nativeWrite@int#:
  sub esp, 4
  ret

global METHOD$java.io.OutputStream$flush@
METHOD$java.io.OutputStream$flush@:
  sub esp, 0
  ret

