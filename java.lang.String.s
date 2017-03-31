extern __exception
extern __malloc
extern VTABLE$java.lang.Object
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern STATICMETHOD$java.util.Arrays$equals@char~#char~#
extern METHOD$java.lang.Object$getClass@
extern InterfaceTABLE$java.lang.Object

global InterfaceTABLE$java.lang.String
InterfaceTABLE$java.lang.String:

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

global STATICFIELDINITIALIZER$java.lang.String
STATICFIELDINITIALIZER$java.lang.String:
  ret
global METHOD$java.lang.String$length@
METHOD$java.lang.String$length@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebp
  ret

global METHOD$java.lang.String$charAt@int#
METHOD$java.lang.String$charAt@int#:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - -12]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@
CONSTRUCTOR$java.lang.String@:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, 0
  push eax
  mov ebx, 4
  mul ebx
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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

global CONSTRUCTOR$java.lang.String@char~#
CONSTRUCTOR$java.lang.String@char~#:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, [ebp - -12]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
  mov dword [eax + 8], ebx
  mov eax, ebx
  mov eax, 0
  mov dword [ebp - 4], eax
label11start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label11end
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - 4]
  mov ebx, 4
  mul ebx
  add eax, 12
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label11start
label11end:
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@java.lang.String#
CONSTRUCTOR$java.lang.String@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp + 8]
  pop ebx
  mov dword [eax + 8], ebx
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
  mov dword [ebp - 4], eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  pop ebx
  add eax, ebx
  push eax
  mov ebx, 4
  mul ebx
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - 4], eax
label12start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp + 8]
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
  je label12end
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - 4]
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 8]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label12start
label12end:
  mov eax, 0
  mov dword [ebp - 4], eax
label13start:
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
  je label13end
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  pop ebx
  add eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 8]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label13start
label13end:
  mov eax, [ebp - 8]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char~#
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
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char~#
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
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - 12], eax
  mov eax, [ebp - -12]
  push eax
  mov eax, -2147483648
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  cmp eax, 0
  je label14end
  mov eax, 52
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 11
  mov dword [eax + 8], 45
  mov dword [eax + 12], 50
  mov dword [eax + 16], 49
  mov dword [eax + 20], 52
  mov dword [eax + 24], 55
  mov dword [eax + 28], 52
  mov dword [eax + 32], 56
  mov dword [eax + 36], 51
  mov dword [eax + 40], 54
  mov dword [eax + 44], 52
  mov dword [eax + 48], 56
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
label14end:
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label15end
  mov eax, 1
  mov dword [ebp - 12], eax
  mov eax, [ebp - -12]
  mov dword [ebp - -12], eax
label15end:
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  cmp eax, 0
  je label16else
  mov eax, 48
  push eax
  mov eax, [ebp - 8]
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 8], eax
  je label16end
label16else:
label17start:
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovg eax, ecx
  cmp eax, 0
  je label17end
  sub esp, 4
  mov eax, [ebp - -12]
  mov eax, 10
  mov dword [ebp - 20], eax
  mov eax, [ebp - -12]
  mov eax, 10
  mov dword [ebp - -12], eax
  mov eax, [ebp - 20]
  push eax
  mov eax, 48
  pop ebx
  add eax, ebx
  push eax
  mov eax, [ebp - 8]
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 8], eax
  add esp, 4
  je label17start
label17end:
label16end:
  mov eax, [ebp - 12]
  cmp eax, 0
  je label18end
  mov eax, 45
  push eax
  mov eax, [ebp - 8]
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 8], eax
label18end:
  mov eax, [ebp - 8]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
  mov dword [ebp - 16], eax
  mov eax, 0
  mov dword [ebp - -12], eax
label19start:
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label19end
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  sub eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 16]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - -12]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - -12], eax
  je label19start
label19end:
  mov eax, [ebp - 16]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char~#
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
  je label20else
  mov eax, 24
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 4
  mov dword [eax + 8], 116
  mov dword [eax + 12], 114
  mov dword [eax + 16], 117
  mov dword [eax + 20], 101
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  je label20end
label20else:
  mov eax, 28
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 5
  mov dword [eax + 8], 102
  mov dword [eax + 12], 97
  mov dword [eax + 16], 108
  mov dword [eax + 20], 115
  mov dword [eax + 24], 101
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
label20end:
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
STATICMETHOD$java.lang.String$valueOf@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  cmp eax, 0
  je label21else
  mov eax, 24
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 4
  mov dword [eax + 8], 110
  mov dword [eax + 12], 117
  mov dword [eax + 16], 108
  mov dword [eax + 20], 108
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  je label21end
label21else:
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 4
  mov eax, [eax]
  call eax
  add esp, 4
label21end:
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@java.lang.String#
STATICMETHOD$java.lang.String$valueOf@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  cmp eax, 0
  je label22else
  mov eax, 24
  call __malloc
  mov dword [eax], VTABLE$java.lang.Object
  mov dword [eax + 4], 4
  mov dword [eax + 8], 110
  mov dword [eax + 12], 117
  mov dword [eax + 16], 108
  mov dword [eax + 20], 108
  push eax
  mov eax, 8
  call __malloc
  mov dword [eax], VTABLE$java.lang.String
  pop ebx
  mov dword [eax + 4], ebx
  je label22end
label22else:
  mov eax, [ebp - -12]
label22end:
  pop ebp
  ret

global METHOD$java.lang.String$equals@java.lang.Object#
METHOD$java.lang.String$equals@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  cmp eax, 0
  je label23end
  mov eax, 0
label23end:
  mov ebx, [eax + 8]
  mov eax, [subtypecheckingtable+ebx*4+116]
  cmp eax, 0
  je label24end
  mov eax, 0
label24end:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - -12]
  mov ebx, [eax + 8]
  mov ebx, [subtypecheckingtable+ebx*4+124]
 cmp ebx, 0
 je subtypingcheck25 
 call __exception
subtypingcheck25:
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.util.Arrays$equals@char~#char~#
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
  mov dword [ebp - 4], eax
  mov eax, [ebp - -12]
  push eax
  mov eax, 0
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label26end
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
label26end:
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp + 8]
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
cmovg eax, ecx
  cmp eax, 0
  je label27end
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
label27end:
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label28end
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
label28end:
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - -12]
  pop ebx
  sub eax, ebx
  push eax
  mov ebx, 4
  mul ebx
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
  mov dword [ebp - 8], eax
  mov eax, [ebp - -12]
  mov dword [ebp - 4], eax
label29start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -16]
  mov eax, [ebp - -16]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label29end
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  pop ebx
  sub eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 8]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label29start
label29end:
  mov eax, [ebp - 8]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char~#
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
label30start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp + 8]
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
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, 32
  mov eax, 32
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovle eax, ecx
  cmp eax, 0
  je label30end
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label30start
label30end:
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  push eax
  mov eax, 1
  pop ebx
  sub eax, ebx
  mov dword [ebp - 8], eax
label31start:
  mov eax, [ebp - 8]
  push eax
  mov eax, 0
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, 32
  mov eax, 32
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovle eax, ecx
  cmp eax, 0
  je label31end
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  sub eax, ebx
  mov dword [ebp - 8], eax
  je label31start
label31end:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovg eax, ecx
  cmp eax, 0
  je label32else
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
  je label32end
label32else:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 12
label32end:
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
label33start:
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label33end
  mov eax, 31
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  add esp, 4
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 8], eax
  je label33start
label33end:
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
  mov ebx, [eax + 8]
  mov ebx, [subtypecheckingtable+ebx*4+124]
 cmp ebx, 0
 je subtypingcheck34 
 call __exception
subtypingcheck34:
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 44
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
  mov dword [ebp - 4], eax
  mov eax, 1
  mov dword [ebp - 8], eax
label35start:
  mov eax, [ebp - 8]
  cmp eax, 0
  je label35end
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  cmp eax, 0
  je label36end
  mov eax, 0
label36end:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  cmp eax, 0
  je label37end
  mov eax, -1
label37end:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  cmp eax, 0
  je label38end
  mov eax, 1
label38end:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label39end
  mov eax, -1
label39end:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovg eax, ecx
  cmp eax, 0
  je label40end
  mov eax, 1
label40end:
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label35start
label35end:
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
  mov eax, [eax + 8]
  push eax
  mov ebx, 4
  mul ebx
  add eax, 12
  call __malloc
  pop ebx
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], ebx
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
label41start:
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label41end
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - 8]
  mov ebx, 4
  mul ebx
  add eax, 12
  mov ebx, eax
  mov eax, [ebp - 4]
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  add esp, 4
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 8], eax
  je label41start
label41end:
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
label42start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp + 8]
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
  je label42end
  sub esp, 4
  mov eax, 1
  mov dword [ebp - 12], eax
  mov eax, 0
  mov dword [ebp - 8], eax
label43start:
  mov eax, [ebp - 8]
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
  je label43end
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 4]
  pop ebx
  add eax, ebx
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  mov eax, [ebp + 8]
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
cmovge eax, ecx
  cmp eax, 0
  je label44else
  mov eax, 0
  mov dword [ebp - 12], eax
  je label44end
label44else:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 4]
  pop ebx
  add eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovne eax, ecx
  cmp eax, 0
  je label45end
  mov eax, 0
  mov dword [ebp - 12], eax
label45end:
label44end:
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 8], eax
  je label43start
label43end:
  mov eax, [ebp - 12]
  cmp eax, 0
  je label46end
  mov eax, [ebp - 4]
label46end:
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add eax, ebx
  mov dword [ebp - 4], eax
  je label42start
label42end:
  mov eax, -1
  add esp, 8
  pop ebp
  ret

