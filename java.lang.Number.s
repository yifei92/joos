global VTABLE$java.lang.Number
VTABLE$java.lang.Number:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Number$intValue@

global InterfaceTABLE$java.lang.NumberInterfaceTABLE$java.lang.Number:
  dd 00000000
  dd 00000000
  dd 00000000
  dd 00000000

global METHOD$java.lang.Number$intValue@
METHOD$java.lang.Number$intValue@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Number@
CONSTRUCTOR$java.lang.Number@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Number
  mov eax, [ebp + 8]
  pop ebp
  ret

