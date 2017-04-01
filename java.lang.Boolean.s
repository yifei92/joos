extern __exception
extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern STATICMETHOD$java.lang.String$valueOf@boolean#
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Boolean
InterfaceTABLE$java.lang.Boolean:

global VTABLE$java.lang.Boolean
VTABLE$java.lang.Boolean:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Boolean$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
global STATICFIELD$java.lang.Boolean$MAX_VALUE
STATICFIELD$java.lang.Boolean$MAX_VALUE:
  dd 0

section .text
global STATICFIELDINITIALIZER$java.lang.Boolean
STATICFIELDINITIALIZER$java.lang.Boolean:
  mov eax, 127
  mov [STATICFIELD$java.lang.Boolean$MAX_VALUE], eax
  ret
global CONSTRUCTOR$java.lang.Boolean@boolean#
CONSTRUCTOR$java.lang.Boolean@boolean#:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Boolean
  mov dword [eax + 4], 100
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Boolean@
CONSTRUCTOR$java.lang.Boolean@:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Boolean
  mov dword [eax + 4], 100
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Boolean$toString@
METHOD$java.lang.Boolean$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@boolean#
  call eax
  add esp, 8
  pop ebp
  ret

