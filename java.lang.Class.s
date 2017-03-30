extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.lang.Object$hashCode@
extern $$subtypecheckingtable
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Class
InterfaceTABLE$java.lang.Class:
  dd 00000000

global VTABLE$java.lang.Class
VTABLE$java.lang.Class:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global CONSTRUCTOR$java.lang.Class@
CONSTRUCTOR$java.lang.Class@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Class
  mov dword [eax + 4], 0
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

