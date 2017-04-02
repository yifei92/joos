extern __exception
extern __malloc
extern STATICMETHOD$java.lang.String$valueOf@byte#
extern STATICFIELDINITIALIZER$java.lang.Byte
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$hashCode@
extern STATICFIELDINITIALIZER$java.util.Arrays
extern STATICMETHOD$java.lang.String$valueOf@int#
extern METHOD$java.lang.Object$toString@
extern STATICFIELDINITIALIZER$java.lang.Integer
extern METHOD$java.lang.Object$getClass@
extern STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
extern STATICFIELDINITIALIZER$java.lang.Object
extern InterfaceTABLE$java.lang.String
extern STATICMETHOD$java.lang.String$valueOf@char#
extern STATICFIELDINITIALIZER$java.lang.Short
extern METHOD$java.lang.Object$clone@
extern STATICFIELDINITIALIZER$java.lang.System
extern STATICFIELDINITIALIZER$java.lang.Number
extern CONSTRUCTOR$java.lang.Object@
extern STATICFIELDINITIALIZER$java.io.PrintStream
extern STATICMETHOD$java.lang.String$valueOf@boolean#
extern STATICMETHOD$java.lang.String$valueOf@short#
extern STATICFIELDINITIALIZER$java.io.OutputStream
extern InterfaceTABLE$java.lang.Object
extern STATICFIELDINITIALIZER$java.lang.Character
extern STATICFIELDINITIALIZER$java.lang.Boolean
extern STATICFIELDINITIALIZER$java.lang.String
extern subtypecheckingtable
extern STATICFIELDINITIALIZER$java.lang.Class

global InterfaceTABLE$J1_A_ConcatInStaticInvoke
InterfaceTABLE$J1_A_ConcatInStaticInvoke:

global VTABLE$J1_A_ConcatInStaticInvoke
VTABLE$J1_A_ConcatInStaticInvoke:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
section .text
global STATICFIELDINITIALIZER$J1_A_ConcatInStaticInvoke
STATICFIELDINITIALIZER$J1_A_ConcatInStaticInvoke:
  ret
global CONSTRUCTOR$J1_A_ConcatInStaticInvoke@
CONSTRUCTOR$J1_A_ConcatInStaticInvoke@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$J1_A_ConcatInStaticInvoke$method@java.lang.String#
STATICMETHOD$J1_A_ConcatInStaticInvoke$method@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$23m
  call __exception
EXCEPTION$23m:
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  add esp, 0
  pop ebp
  ret
  pop ebp
  ret

global STATICMETHOD$J1_A_ConcatInStaticInvoke$test@
STATICMETHOD$J1_A_ConcatInStaticInvoke$test@:
  push ebp
  mov ebp, esp
  sub esp, 24
  mov eax, 1
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, 49
  mov dword [ebp - 12], eax
  mov eax, 2
  mov dword [ebp - 16], eax
  mov eax, 3
  mov dword [ebp - 20], eax
  mov eax, 0
  mov dword [ebp - 24], eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],20
  mov dword [eax + 8], 0
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 144
  pop ebx
  mov dword [eax + 8], ebx
  push eax
  mov eax, [ebp - 4]
  pop ebx
  push ebx
  mov ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@boolean#
  call eax
  add esp, 8
  mov ebx, eax
  pop eax
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
  mov eax, [ebp - 8]
  pop ebx
  push ebx
  mov ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@byte#
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
  mov eax, [ebp - 12]
  pop ebx
  push ebx
  mov ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@char#
  call eax
  add esp, 8
  mov ebx, eax
  pop eax
  push ebx
  cmp eax, 0
  jne EXCEPTION$3m
  call __exception
EXCEPTION$3m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  mov eax, [ebp - 16]
  pop ebx
  push ebx
  mov ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@short#
  call eax
  add esp, 8
  mov ebx, eax
  pop eax
  push ebx
  cmp eax, 0
  jne EXCEPTION$4m
  call __exception
EXCEPTION$4m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  mov eax, [ebp - 20]
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
  jne EXCEPTION$5m
  call __exception
EXCEPTION$5m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  mov eax, [ebp - 24]
  pop ebx
  push ebx
  mov ebx, eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
  call eax
  add esp, 8
  mov ebx, eax
  pop eax
  push ebx
  cmp eax, 0
  jne EXCEPTION$6m
  call __exception
EXCEPTION$6m:
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  mov eax, 0
  pop ebx
  push ebx
  mov eax, 28
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],20
  mov dword [eax + 8], 4
  mov dword [eax + 12], 110
  mov dword [eax + 16], 117
  mov dword [eax + 20], 108
  mov dword [eax + 24], 108
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 144
  pop ebx
  mov dword [eax + 8], ebx
  mov ebx, eax
  pop eax
  push ebx
  cmp eax, 0
  jne EXCEPTION$7m
  call __exception
EXCEPTION$7m:
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
  push 0
  mov eax, STATICMETHOD$J1_A_ConcatInStaticInvoke$method@java.lang.String#
  call eax
  add esp, 8
  push eax
  mov eax, 107
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  add esp, 24
  pop ebp
  ret
  add esp, 24
  pop ebp
  ret

global _start
_start:
  call STATICFIELDINITIALIZER$java.io.OutputStream
  call STATICFIELDINITIALIZER$java.io.PrintStream
  call STATICFIELDINITIALIZER$java.util.Arrays
  call STATICFIELDINITIALIZER$java.lang.System
  call STATICFIELDINITIALIZER$java.lang.Integer
  call STATICFIELDINITIALIZER$J1_A_ConcatInStaticInvoke
  call STATICFIELDINITIALIZER$java.lang.Character
  call STATICFIELDINITIALIZER$java.lang.Short
  call STATICFIELDINITIALIZER$java.lang.Boolean
  call STATICFIELDINITIALIZER$java.lang.Class
  call STATICFIELDINITIALIZER$java.lang.Object
  call STATICFIELDINITIALIZER$java.lang.String
  call STATICFIELDINITIALIZER$java.lang.Byte
  call STATICFIELDINITIALIZER$java.lang.Number
  push 0
  mov eax, STATICMETHOD$J1_A_ConcatInStaticInvoke$test@
  call eax
  add esp, 4
  mov ebx, eax
  mov eax, 1
  int 0x80
