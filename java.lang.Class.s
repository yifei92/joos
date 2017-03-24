global VTABLE$java.lang.Class
VTABLE$java.lang.Class:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.ClassInterfaceTABLE$java.lang.Class:
  dd 00000000
  dd 00000000
  dd 00000000
  dd 00000000

global CONSTRUCTOR$java.lang.Class@
CONSTRUCTOR$java.lang.Class@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Class
  mov eax, [ebp + 8]
  pop ebp
  ret

