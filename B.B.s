extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$B.B
InterfaceTABLE$B.B:
  dd METHOD$B.B$funca@

global VTABLE$B.B
VTABLE$B.B:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$B.B$funca@

global CONSTRUCTOR$B.B@
CONSTRUCTOR$B.B@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$B.B
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$B.B$funca@
METHOD$B.B$funca@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

