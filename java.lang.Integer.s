global VTABLE$java.lang.Integer
VTABLE$java.lang.Integer:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Number$intValue@
  dd METHOD$java.lang.Integer$intValue@
  dd METHOD$java.lang.Integer$toString@

global STATICFIELD$java.lang.Integer$MAX_VALUE
STATICFIELD$java.lang.Integer$MAX_VALUE:
  dd 0

global METHOD$java.lang.Integer$intValue@
METHOD$java.lang.Integer$intValue@:
  sub esp, 0
  ret

global STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#:
  sub esp, 4
  ret

global METHOD$java.lang.Integer$toString@
METHOD$java.lang.Integer$toString@:
  sub esp, 0
  ret

