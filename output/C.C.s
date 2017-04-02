extern __exception
extern __malloc
extern STATICFIELDINITIALIZER$java.lang.Byte
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$hashCode@
extern STATICFIELDINITIALIZER$java.util.Arrays
extern STATICMETHOD$java.lang.String$valueOf@int#
extern METHOD$java.lang.Object$toString@
extern STATICFIELDINITIALIZER$java.lang.Integer
extern METHOD$java.lang.Object$getClass@
extern STATICFIELDINITIALIZER$java.lang.Object
extern InterfaceTABLE$java.lang.String
extern STATICFIELD$java.lang.System$out
extern STATICFIELDINITIALIZER$java.lang.Short
extern METHOD$java.lang.Object$clone@
extern STATICFIELDINITIALIZER$java.lang.System
extern STATICFIELDINITIALIZER$java.lang.Number
extern CONSTRUCTOR$java.lang.Object@
extern STATICFIELDINITIALIZER$java.io.PrintStream
extern STATICFIELDINITIALIZER$java.io.OutputStream
extern InterfaceTABLE$java.lang.Object
extern STATICFIELDINITIALIZER$java.lang.Character
extern STATICFIELDINITIALIZER$java.lang.Boolean
extern STATICFIELDINITIALIZER$java.lang.String
extern subtypecheckingtable
extern STATICFIELDINITIALIZER$java.lang.Class

global InterfaceTABLE$C.C
InterfaceTABLE$C.C:

global VTABLE$C.C
VTABLE$C.C:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
section .text
global STATICFIELDINITIALIZER$C.C
STATICFIELDINITIALIZER$C.C:
  ret
global CONSTRUCTOR$C.C@
CONSTRUCTOR$C.C@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$C.C$test@
STATICMETHOD$C.C$test@:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 16
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],20
  mov dword [eax + 8], 1
  mov dword [eax + 12], 97
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 144
  pop ebx
  mov dword [eax + 8], ebx
  mov dword [ebp - 4], eax
  mov eax, 16
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],20
  mov dword [eax + 8], 1
  mov dword [eax + 12], 98
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 144
  pop ebx
  mov dword [eax + 8], ebx
  mov dword [ebp - 8], eax
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  pop ebx
  mov ecx, eax
  mov eax, ebx
  mov ebx, ecx
  push ebx
  cmp eax, 0
  jne EXCEPTION$1m
  call __exception
EXCEPTION$1m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  mov eax, 10
  pop ebx
  push ebx
  mov ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  mov ebx, eax
  pop eax
  push ebx
  cmp eax, 0
  jne EXCEPTION$2m
  call __exception
EXCEPTION$2m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  pop eax
  push eax
  mov eax, [STATICFIELD$java.lang.System$out]
  cmp eax, 0
  jne EXCEPTION$39m
  call __exception
EXCEPTION$39m:
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  mov eax, 0
  add esp, 8
  pop ebp
  ret
  add esp, 8
  pop ebp
  ret

global _start
_start:
  call STATICFIELDINITIALIZER$java.io.OutputStream
  call STATICFIELDINITIALIZER$java.io.PrintStream
  call STATICFIELDINITIALIZER$java.util.Arrays
  call STATICFIELDINITIALIZER$java.lang.System
  call STATICFIELDINITIALIZER$java.lang.Integer
  call STATICFIELDINITIALIZER$java.lang.Character
  call STATICFIELDINITIALIZER$java.lang.Short
  call STATICFIELDINITIALIZER$java.lang.Boolean
  call STATICFIELDINITIALIZER$C.C
  call STATICFIELDINITIALIZER$java.lang.Class
  call STATICFIELDINITIALIZER$java.lang.Object
  call STATICFIELDINITIALIZER$java.lang.String
  call STATICFIELDINITIALIZER$java.lang.Byte
  call STATICFIELDINITIALIZER$java.lang.Number
  push 0
  mov eax, STATICMETHOD$C.C$test@
  call eax
  add esp, 4
  mov ebx, eax
  mov eax, 1
  int 0x80
