extern __exception
extern STATICFIELDINITIALIZER$java.lang.Number
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
extern STATICFIELDINITIALIZER$java.lang.Object
extern STATICFIELDINITIALIZER$java.lang.String
extern STATICFIELDINITIALIZER$java.lang.Short
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern STATICFIELDINITIALIZER$java.lang.Class
extern STATICFIELDINITIALIZER$java.lang.System

global InterfaceTABLE$C.C
InterfaceTABLE$C.C:

global VTABLE$C.C
VTABLE$C.C:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global STATICFIELD$C.C$a
STATICFIELD$C.C$a:
  dd 0

global STATICFIELDINITIALIZER$C.C
STATICFIELDINITIALIZER$C.C:
  mov eax, 10
  ret
global CONSTRUCTOR$C.C@
CONSTRUCTOR$C.C@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$C.C
  mov dword [eax + 4], 100
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$C.C$test@
STATICMETHOD$C.C$test@:
  push ebp
  mov ebp, esp
  mov eax, 123
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
