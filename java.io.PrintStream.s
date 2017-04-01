extern __exception
extern __malloc
extern STATICMETHOD$java.lang.String$valueOf@byte#
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern STATICMETHOD$java.lang.String$valueOf@boolean#
extern STATICMETHOD$java.lang.String$valueOf@short#
extern METHOD$java.io.OutputStream$write@char#
extern METHOD$java.lang.Object$hashCode@
extern STATICMETHOD$java.lang.String$valueOf@int#
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@
extern VTABLE$java.lang.Object
extern STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
extern VTABLE$java.lang.String
extern STATICMETHOD$java.lang.String$valueOf@char#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.io.OutputStream$flush@
extern subtypecheckingtable
extern METHOD$java.io.OutputStream$write@int#

global InterfaceTABLE$java.io.PrintStream
InterfaceTABLE$java.io.PrintStream:

global VTABLE$java.io.PrintStream
VTABLE$java.io.PrintStream:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.io.OutputStream$write@char#
  dd METHOD$java.io.OutputStream$write@int#
  dd METHOD$java.io.OutputStream$flush@
  dd METHOD$java.io.PrintStream$print@java.lang.String#
  dd METHOD$java.io.PrintStream$println@
  dd METHOD$java.io.PrintStream$println@java.lang.String#
  dd METHOD$java.io.PrintStream$println@java.lang.Object#
  dd METHOD$java.io.PrintStream$println@boolean#
  dd METHOD$java.io.PrintStream$println@byte#
  dd METHOD$java.io.PrintStream$println@char#
  dd METHOD$java.io.PrintStream$println@short#
  dd METHOD$java.io.PrintStream$println@int#
  dd METHOD$java.io.PrintStream$print@java.lang.Object#
  dd METHOD$java.io.PrintStream$print@boolean#
  dd METHOD$java.io.PrintStream$print@byte#
  dd METHOD$java.io.PrintStream$print@char#
  dd METHOD$java.io.PrintStream$print@short#
  dd METHOD$java.io.PrintStream$print@int#

section .data
section .text
global STATICFIELDINITIALIZER$java.io.PrintStream
STATICFIELDINITIALIZER$java.io.PrintStream:
  ret
global CONSTRUCTOR$java.io.PrintStream@
CONSTRUCTOR$java.io.PrintStream@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.io.PrintStream
  mov dword [eax + 4], 28
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@java.lang.String#
METHOD$java.io.PrintStream$print@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 0
  mov dword [ebp - 4], eax
label1start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label1end
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 8
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  je label1start
label1end:
  add esp, 4
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@
METHOD$java.io.PrintStream$println@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 0
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@java.lang.String#
METHOD$java.io.PrintStream$println@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  mov eax, 10
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@java.lang.Object#
METHOD$java.io.PrintStream$println@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@boolean#
METHOD$java.io.PrintStream$println@boolean#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@boolean#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@byte#
METHOD$java.io.PrintStream$println@byte#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@byte#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@char#
METHOD$java.io.PrintStream$println@char#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@char#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@short#
METHOD$java.io.PrintStream$println@short#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@short#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@int#
METHOD$java.io.PrintStream$println@int#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@java.lang.Object#
METHOD$java.io.PrintStream$print@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@boolean#
METHOD$java.io.PrintStream$print@boolean#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@boolean#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@byte#
METHOD$java.io.PrintStream$print@byte#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@byte#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@char#
METHOD$java.io.PrintStream$print@char#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@char#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@short#
METHOD$java.io.PrintStream$print@short#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@short#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@int#
METHOD$java.io.PrintStream$print@int#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

