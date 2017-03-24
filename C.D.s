global VTABLE$C.D
VTABLE$C.D:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$C.D$funca@
  dd METHOD$C.D$funca2@

global InterfaceTABLE$C.DInterfaceTABLE$C.D:
  dd METHOD$C.D$funca@
  dd METHOD$C.D$funca2@
  dd 00000000
  dd 00000000

global CONSTRUCTOR$C.D@
CONSTRUCTOR$C.D@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$C.D
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$C.D$funca@
METHOD$C.D$funca@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

global METHOD$C.D$funca2@
METHOD$C.D$funca2@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

