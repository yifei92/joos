extern __exception
extern STATICFIELDINITIALIZER$java.lang.Number
extern CONSTRUCTOR$java.lang.Object@
extern __malloc
extern STATICFIELDINITIALIZER$java.lang.Byte
extern STATICFIELDINITIALIZER$java.io.PrintStream
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$hashCode@
extern STATICFIELDINITIALIZER$java.util.Arrays
extern STATICFIELDINITIALIZER$java.io.OutputStream
extern METHOD$java.lang.Object$toString@
extern STATICFIELDINITIALIZER$java.lang.Integer
extern METHOD$java.lang.Object$getClass@
extern STATICFIELDINITIALIZER$java.lang.Character
extern STATICFIELDINITIALIZER$java.lang.Boolean
extern VTABLE$java.lang.Object
extern STATICFIELDINITIALIZER$java.lang.Object
extern VTABLE$java.lang.String
extern STATICFIELDINITIALIZER$java.lang.String
extern STATICFIELDINITIALIZER$java.lang.Short
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern STATICFIELDINITIALIZER$java.lang.Class
extern STATICFIELDINITIALIZER$java.lang.System

global InterfaceTABLE$C
InterfaceTABLE$C:

global VTABLE$C
VTABLE$C:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
section .text
global STATICFIELDINITIALIZER$C
STATICFIELDINITIALIZER$C:
  ret
global CONSTRUCTOR$C@
CONSTRUCTOR$C@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$C$test@
STATICMETHOD$C$test@:
  push ebp
  mov ebp, esp
  sub esp, 12
  mov eax, 12
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 1
  mov dword [eax + 8], 97
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  mov dword [ebp - 4], eax
  mov eax, 12
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 1
  mov dword [eax + 8], 98
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  mov dword [ebp - 8], eax
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 4]
  cmp eax, 0
  jne EXCEPTION$37m
  call __exception
EXCEPTION$37m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov dword [ebp - 12], eax
  mov eax, 0
  add esp, 12
  pop ebp
  ret

global _start
_start:
  call STATICFIELDINITIALIZER$java.io.OutputStream
  call STATICFIELDINITIALIZER$java.io.PrintStream
  call STATICFIELDINITIALIZER$java.util.Arrays
  call STATICFIELDINITIALIZER$C
  call STATICFIELDINITIALIZER$java.lang.System
  call STATICFIELDINITIALIZER$java.lang.Integer
  call STATICFIELDINITIALIZER$java.lang.Character
  call STATICFIELDINITIALIZER$java.lang.Short
  call STATICFIELDINITIALIZER$java.lang.Boolean
  call STATICFIELDINITIALIZER$java.lang.Class
  call STATICFIELDINITIALIZER$java.lang.Object
  call STATICFIELDINITIALIZER$java.lang.String
  call STATICFIELDINITIALIZER$java.lang.Byte
  call STATICFIELDINITIALIZER$java.lang.Number
  push 0
  mov eax, STATICMETHOD$C$test@
  call eax
  add esp, 4
  mov ebx, eax
  mov eax, 1
  int 0x80
