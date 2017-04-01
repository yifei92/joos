extern __exception
extern __malloc
extern VTABLE$java.lang.String
extern subtypecheckingtable

global InterfaceTABLE$java.lang.Object
InterfaceTABLE$java.lang.Object:

global VTABLE$java.lang.Object
VTABLE$java.lang.Object:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
section .text
global STATICFIELDINITIALIZER$java.lang.Object
STATICFIELDINITIALIZER$java.lang.Object:
  ret
global CONSTRUCTOR$java.lang.Object@
CONSTRUCTOR$java.lang.Object@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], 124
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Object$equals@java.lang.Object#
METHOD$java.lang.Object$equals@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  push eax
  mov eax, [ebp - -12]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  pop ebp
  ret

global METHOD$java.lang.Object$toString@
METHOD$java.lang.Object$toString@:
  push ebp
  mov ebp, esp
  mov eax, 80
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 18
  mov dword [eax + 8], 83
  mov dword [eax + 12], 111
  mov dword [eax + 16], 109
  mov dword [eax + 20], 101
  mov dword [eax + 24], 32
  mov dword [eax + 28], 114
  mov dword [eax + 32], 97
  mov dword [eax + 36], 110
  mov dword [eax + 40], 100
  mov dword [eax + 44], 111
  mov dword [eax + 48], 109
  mov dword [eax + 52], 32
  mov dword [eax + 56], 111
  mov dword [eax + 60], 98
  mov dword [eax + 64], 106
  mov dword [eax + 68], 101
  mov dword [eax + 72], 99
  mov dword [eax + 76], 116
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  pop ebp
  ret

global METHOD$java.lang.Object$hashCode@
METHOD$java.lang.Object$hashCode@:
  push ebp
  mov ebp, esp
  mov eax, 42
  pop ebp
  ret

global METHOD$java.lang.Object$clone@
METHOD$java.lang.Object$clone@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Object$getClass@
METHOD$java.lang.Object$getClass@:
  push ebp
  mov ebp, esp
  mov eax, 0
  pop ebp
  ret

