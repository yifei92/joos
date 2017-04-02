extern __exception
extern CONSTRUCTOR$java.lang.Object@
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Class
InterfaceTABLE$java.lang.Class:

global VTABLE$java.lang.Class
VTABLE$java.lang.Class:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
section .text
global STATICFIELDINITIALIZER$java.lang.Class
STATICFIELDINITIALIZER$java.lang.Class:
  ret
global CONSTRUCTOR$java.lang.Class@
CONSTRUCTOR$java.lang.Class@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

