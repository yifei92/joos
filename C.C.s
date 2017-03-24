extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.lang.Object$hashCode@
extern CONSTRUCTOR$B.B@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$C.C
InterfaceTABLE$C.C:
  dd 00000000

global VTABLE$C.C
VTABLE$C.C:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$C.C$funcc@

global METHOD$C.C$funcc@
METHOD$C.C$funcc@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

global CONSTRUCTOR$C.C@
CONSTRUCTOR$C.C@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$C.C
  push 0
  call CONSTRUCTOR$B.B@
  add esp, 4
  mov dword [ebp - -12], eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 0
  mov eax, [eax]
  call eax
  add esp, 4
  push 0
  call CONSTRUCTOR$C.C@
  add esp, 4
  mov dword [ebp - -16], eax
  mov eax, [ebp - -16]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

