global VTABLE$A.A
VTABLE$A.A:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global CONSTRUCTOR$A.A@
CONSTRUCTOR$A.A@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$A.A
  push 0
  call CONSTRUCTOR$C.C@
  add esp, 4
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, -1
  call eax
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

