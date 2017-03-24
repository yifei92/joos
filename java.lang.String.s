extern __malloc
extern METHOD$java.lang.Object$clone@
extern STATICMETHOD$java.util.Arrays$equals@char[]#char[]#
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.String
InterfaceTABLE$java.lang.String:
  dd 00000000

global VTABLE$java.lang.String
VTABLE$java.lang.String:
  dd METHOD$java.lang.String$equals@java.lang.Object#
  dd METHOD$java.lang.String$toString@
  dd METHOD$java.lang.String$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.String$length@
  dd METHOD$java.lang.String$charAt@int#
  dd METHOD$java.lang.String$concat@java.lang.String#
  dd METHOD$java.lang.String$substring@int#int#
  dd METHOD$java.lang.String$trim@
  dd METHOD$java.lang.String$compareTo@java.lang.Object#
  dd METHOD$java.lang.String$compareTo@java.lang.String#
  dd METHOD$java.lang.String$toCharArray@
  dd METHOD$java.lang.String$indexOf@java.lang.String#

global METHOD$java.lang.String$length@
METHOD$java.lang.String$length@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  pop ebp
  ret

global METHOD$java.lang.String$charAt@int#
METHOD$java.lang.String$charAt@int#:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - -12]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@
CONSTRUCTOR$java.lang.String@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 0
  mov eax, 0
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$56:
  cmp eax, ebx
  je ARRAYINIT$56.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$56
.end:
  pop eax
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@char[]#
CONSTRUCTOR$java.lang.String@char[]#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 0
  mov eax, [ebp - -12]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$76:
  cmp eax, ebx
  je ARRAYINIT$76.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$76
.end:
  pop eax
  push eax
  mov eax, [ebp + 8]
  pop ebx
  mov dword [eax + 4], ebx
  mov eax, ebx
  mov eax, 0
  mov dword [ebp - 4], eax
for_body22start:
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  cmp eax, 0
  je for_body22end
  mov eax, [ebp - -12]
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 4]
  mov ebx, 4
  mul ebx
  add eax, 8
  push eax
  mov eax, [ebp + 8]
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, 1
  mov dword [ebp - 4], eax
  je for_body22start
for_body22end:
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@java.lang.String#
CONSTRUCTOR$java.lang.String@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 0
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  push eax
  mov eax, [ebp + 8]
  pop ebx
  mov dword [eax + 4], ebx
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.String$concat@java.lang.String#
METHOD$java.lang.String$concat@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$150:
  cmp eax, ebx
  je ARRAYINIT$150.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$150
.end:
  pop eax
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
for_body24start:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  cmp eax, 0
  je for_body24end
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 8]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body24start
for_body24end:
  mov eax, 0
  mov dword [ebp - 8], eax
for_body25start:
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  cmp eax, 0
  je for_body25end
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body25start
for_body25end:
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@char#
STATICMETHOD$java.lang.String$valueOf@char#:
  push ebp
  mov ebp, esp
  sub esp, 4
  mov eax, 1
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$250:
  cmp eax, ebx
  je ARRAYINIT$250.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$250
.end:
  pop eax
  mov dword [ebp - 4], eax
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 4
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@int#
STATICMETHOD$java.lang.String$valueOf@int#:
  push ebp
  mov ebp, esp
  sub esp, 16
  mov eax, 15
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$285:
  cmp eax, ebx
  je ARRAYINIT$285.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$285
.end:
  pop eax
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 12], eax
  mov eax, 0
  mov dword [ebp - 16], eax
  mov eax, [ebp - -12]
  mov eax, -2147483648
  cmp eax, 0
  je block27end
  mov eax, 0
block27end:
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block27end
  mov eax, 1
  mov dword [ebp - 16], eax
  mov eax, [ebp - -12]
  mov dword [ebp - -12], eax
block27end:
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block27else
  mov eax, 0
  push eax
  mov eax, [ebp - 12]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 12]
  mov eax, 1
  mov dword [ebp - 12], eax
  je block27end
block27else:
block34start:
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block34end
  sub esp, 4
  mov eax, [ebp - -12]
  mov eax, 10
  mov dword [ebp - 20], eax
  mov eax, [ebp - -12]
  mov eax, 10
  mov dword [ebp - -12], eax
  mov eax, [ebp - 20]
  mov eax, 0
  push eax
  mov eax, [ebp - 12]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 12]
  mov eax, 1
  mov dword [ebp - 12], eax
  add esp, 4
  je block34start
block34end:
block27end:
  mov eax, [ebp - 16]
  cmp eax, 0
  je block27end
  mov eax, -1
  push eax
  mov eax, [ebp - 12]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 12]
  mov eax, 1
  mov dword [ebp - 12], eax
block27end:
  mov eax, [ebp - 12]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$425:
  cmp eax, ebx
  je ARRAYINIT$425.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$425
.end:
  pop eax
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - -12], eax
for_body39start:
  mov eax, [ebp - -12]
  mov eax, [ebp - 12]
  cmp eax, 0
  je for_body39end
  mov eax, [ebp - 4]
  mov eax, [ebp - 12]
  mov eax, 1
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - -12]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 8]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - -12]
  mov eax, 1
  mov dword [ebp - -12], eax
  je for_body39start
for_body39end:
  mov eax, [ebp - 8]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 16
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@short#
STATICMETHOD$java.lang.String$valueOf@short#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@byte#
STATICMETHOD$java.lang.String$valueOf@byte#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@boolean#
STATICMETHOD$java.lang.String$valueOf@boolean#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  cmp eax, 0
  je block42else
  mov eax, 0
  je block42end
block42else:
  mov eax, 0
block42end:
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
STATICMETHOD$java.lang.String$valueOf@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block45else
  mov eax, 0
  je block45end
block45else:
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 8
  mov eax, [eax]
  call eax
  add esp, 4
block45end:
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@java.lang.String#
STATICMETHOD$java.lang.String$valueOf@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block48else
  mov eax, 0
  je block48end
block48else:
  mov eax, [ebp - -12]
block48end:
  pop ebp
  ret

global METHOD$java.lang.String$equals@java.lang.Object#
METHOD$java.lang.String$equals@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block51end
  mov eax, 0
block51end:
  mov eax, [ebp - -12]
  cmp eax, 0
  je block51end
  mov eax, 0
block51end:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  push eax
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.util.Arrays$equals@char[]#char[]#
  call eax
  add esp, 12
  pop ebp
  ret

global METHOD$java.lang.String$substring@int#int#
METHOD$java.lang.String$substring@int#int#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax, 0
  je block54end
block54end:
  mov eax, [ebp - -16]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  cmp eax, 0
  je block54end
block54end:
  mov eax, [ebp - -16]
  mov eax, [ebp - -12]
  cmp eax, 0
  je block54end
block54end:
  mov eax, [ebp - -16]
  mov eax, [ebp - -12]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$690:
  cmp eax, ebx
  je ARRAYINIT$690.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$690
.end:
  pop eax
  mov dword [ebp - 4], eax
  mov eax, [ebp - -12]
  mov dword [ebp - 8], eax
for_body58start:
  mov eax, [ebp - 8]
  mov eax, [ebp - -16]
  cmp eax, 0
  je for_body58end
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body58start
for_body58end:
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$trim@
METHOD$java.lang.String$trim@:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - 4], eax
for_body60start:
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov eax, -1
  cmp eax, 0
  je for_body60end
  mov eax, [ebp - 4]
  mov eax, 1
  mov dword [ebp - 4], eax
  je for_body60start
for_body60end:
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, 1
  mov dword [ebp - 8], eax
for_body62start:
  mov eax, [ebp - 8]
  mov eax, 0
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov eax, -1
  cmp eax, 0
  je for_body62end
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body62start
for_body62end:
  mov eax, [ebp - 4]
  mov eax, [ebp - 8]
  cmp eax, 0
  je block59else
  je block59end
block59else:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  mov eax, 1
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 36
  mov eax, [eax]
  call eax
  add esp, 12
block59end:
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$hashCode@
METHOD$java.lang.String$hashCode@:
  push ebp
  mov ebp, esp
  sub esp, 4
  mov eax, 0
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
for_body67start:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  cmp eax, 0
  je for_body67end
  mov eax, 31
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov dword [ebp - 4], eax
  add esp, 4
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body67start
for_body67end:
  add esp, 4
  mov eax, [ebp - 4]
  add esp, 4
  pop ebp
  ret

global METHOD$java.lang.String$toString@
METHOD$java.lang.String$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.String$compareTo@java.lang.Object#
METHOD$java.lang.String$compareTo@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 48
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$compareTo@java.lang.String#
METHOD$java.lang.String$compareTo@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, 1
  mov dword [ebp - 4], eax
block71start:
  mov eax, [ebp - 4]
  cmp eax, 0
  je block71end
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  cmp eax, 0
  je block73end
  mov eax, 0
block73end:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  cmp eax, 0
  je block73end
  mov eax, -1
block73end:
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  cmp eax, 0
  je block73end
  mov eax, 1
block73end:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  cmp eax, 0
  je block73end
  mov eax, -1
block73end:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  cmp eax, 0
  je block73end
  mov eax, 1
block73end:
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je block71start
block71end:
  mov eax, 0
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$toCharArray@
METHOD$java.lang.String$toCharArray@:
  push ebp
  mov ebp, esp
  sub esp, 4
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 8
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4], ebx
  push eax
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  pop ebx
  add ebx, 8
  add eax, ebx
ARRAYINIT$1033:
  cmp eax, ebx
  je ARRAYINIT$1033.end
  add ebx, 4
  mov dword [ebx], 0
  jmp ARRAYINIT$1033
.end:
  pop eax
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
for_body80start:
  mov eax, [ebp - 8]
  mov eax, [ebp - 4]
  cmp eax, 0
  je for_body80end
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 8]
  mov ebx, 4
  mul ebx
  add eax, 8
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  add esp, 4
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body80start
for_body80end:
  add esp, 4
  mov eax, [ebp - 4]
  add esp, 4
  pop ebp
  ret

global METHOD$java.lang.String$indexOf@java.lang.String#
METHOD$java.lang.String$indexOf@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - 4], eax
for_body83start:
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  cmp eax, 0
  je for_body83end
  sub esp, 4
  mov eax, 1
  mov dword [ebp - 12], eax
  mov eax, 0
  mov dword [ebp - 8], eax
for_body85start:
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  cmp eax, 0
  je for_body85end
  mov eax, [ebp - 8]
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 4
  cmp eax, 0
  je block86else
  mov eax, 0
  mov dword [ebp - 12], eax
  je block86end
block86else:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  cmp eax, 0
  je else_statement88end
  mov eax, 0
  mov dword [ebp - 12], eax
else_statement88end:
block86end:
  mov eax, [ebp - 8]
  mov eax, 1
  mov dword [ebp - 8], eax
  je for_body85start
for_body85end:
  mov eax, [ebp - 12]
  cmp eax, 0
  je block84end
  mov eax, [ebp - 4]
block84end:
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, 1
  mov dword [ebp - 4], eax
  je for_body83start
for_body83end:
  mov eax, -1
  add esp, 8
  pop ebp
  ret

