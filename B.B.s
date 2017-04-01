extern __exception
extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$B.B
InterfaceTABLE$B.B:

global VTABLE$B.B
VTABLE$B.B:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
global STATICFIELD$B.B$a
STATICFIELD$B.B$a:
  dd 0

global STATICFIELD$B.B$b
STATICFIELD$B.B$b:
  dd 0

section .text
global STATICFIELDINITIALIZER$B.B
STATICFIELDINITIALIZER$B.B:
  push 0
  mov eax, STATICMETHOD$B.B$init@
  call eax
  add esp, 4
  push eax
  push 0
  mov eax, STATICMETHOD$B.B$init@
  call eax
  add esp, 4
  pop ebx
  add ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$B.B$init@
  call eax
  add esp, 4
  pop ebx
  sub ebx, eax
  push ebx
  pop eax
  mov [STATICFIELD$B.B$a], eax
  push 0
  mov eax, STATICMETHOD$B.B$init@
  call eax
  add esp, 4
  push eax
  push 0
  mov eax, STATICMETHOD$B.B$init@
  call eax
  add esp, 4
  pop ebx
  sub ebx, eax
  push ebx
  pop eax
  mov [STATICFIELD$B.B$b], eax
  ret
global STATICMETHOD$B.B$init@
STATICMETHOD$B.B$init@:
  push ebp
  mov ebp, esp
  mov eax, 1
  pop ebp
  ret

global CONSTRUCTOR$B.B@
CONSTRUCTOR$B.B@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$B.B
  mov dword [eax + 4], 92
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

