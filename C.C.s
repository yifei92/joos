global VTABLE$C.C
VTABLE$C.C:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$C.C$func@

global CONSTRUCTOR$C.C@
CONSTRUCTOR$C.C@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$C.C
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$C.C$func@
METHOD$C.C$func@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

