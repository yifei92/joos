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

global InterfaceTABLE$java.io.PrintStreamInterfaceTABLE$java.io.PrintStream:
  dd 00000000
  dd 00000000
  dd 00000000
  dd 00000000

global CONSTRUCTOR$java.io.PrintStream@
CONSTRUCTOR$java.io.PrintStream@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.io.PrintStream
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.io.PrintStream$print@java.lang.String#
METHOD$java.io.PrintStream$print@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 0
for_body96start:
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je for_body96end
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 8
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 1
  je for_body96start
for_body96end:
  add esp, 4
  pop ebp
  ret

global METHOD$java.io.PrintStream$println@
METHOD$java.io.PrintStream$println@:
  push ebp
  mov ebp, esp
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 40
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
  call eax
  add esp, 8
  mov eax, 10
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
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
  call eax
  add esp, 8
  pop ebp
  ret

