extern __exception
extern CONSTRUCTOR$java.lang.Object@
extern __malloc
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

section .data
section .text
global STATICFIELDINITIALIZER$java.lang.String
STATICFIELDINITIALIZER$java.lang.String:
  ret
global METHOD$java.lang.String$length@
METHOD$java.lang.String$length@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  cmp eax, 0
  jne EXCEPTION$29$0
  call __exception
EXCEPTION$29$0:
  mov eax, [eax + 8]
  add esp, 0
  pop ebp
  ret
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
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$43a
  call __exception
EXCEPTION$43a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  add esp, 0
  pop ebp
  ret
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@
CONSTRUCTOR$java.lang.String@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov ebx, [ebp + 8]
  mov dword [ebx + 8], eax
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
  mov dword [eax + 8], ebx
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@char~#
CONSTRUCTOR$java.lang.String@char~#:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov ebx, [ebp + 8]
  mov dword [ebx + 8], eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$79$0
  call __exception
EXCEPTION$79$0:
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
  cmp eax, 0
  jne EXCEPTION$72
  call __exception
EXCEPTION$72:
  pop ebx
  mov dword [eax + 8], ebx
  mov eax, ebx
  mov eax, 0
  mov dword [ebp - 4], eax
label11start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$93$0
  call __exception
EXCEPTION$93$0:
  mov eax, [eax + 8]
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
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$110a
  call __exception
EXCEPTION$110a:
  push eax
  mov eax, ebx
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
  cmp eax, 0
  jne EXCEPTION$103
  call __exception
EXCEPTION$103:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, [eax + 8]
  jnge EXCEPTION$103a
  call __exception
EXCEPTION$103a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
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
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label11start
label11end:
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@java.lang.String#
CONSTRUCTOR$java.lang.String@java.lang.String#:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov ebx, [ebp + 8]
  mov dword [ebx + 8], eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$127$0
  call __exception
EXCEPTION$127$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp + 8]
  cmp eax, 0
  jne EXCEPTION$123
  call __exception
EXCEPTION$123:
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
  cmp eax, 0
  jne EXCEPTION$160m
  call __exception
EXCEPTION$160m:
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  pop ebx
  mov ecx, eax
  mov eax, ebx
  mov ebx, ecx
  push ebx
  cmp eax, 0
  jne EXCEPTION$2m
  call __exception
EXCEPTION$2m:
  push eax
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  pop eax
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
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$187a
  call __exception
EXCEPTION$187a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  mov eax, [ebp - 8]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$182a
  call __exception
EXCEPTION$182a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label12start
label12end:
  mov eax, 0
  mov dword [ebp - 4], eax
label13start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$203m
  call __exception
EXCEPTION$203m:
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
  cmp eax, 0
  jne EXCEPTION$221$0
  call __exception
EXCEPTION$221$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$221a
  call __exception
EXCEPTION$221a:
  push eax
  mov eax, ebx
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
  push eax
  push ebx
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebx
  push ebx
  cmp eax, 0
  jne EXCEPTION$3m
  call __exception
EXCEPTION$3m:
  push eax
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  push ebx
  pop eax
  mov ebx, eax
  mov eax, [ebp - 8]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$212a
  call __exception
EXCEPTION$212a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label13start
label13end:
  mov eax, [ebp - 8]
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 8], 0
  push eax
  call CONSTRUCTOR$java.lang.String@char~#
  add esp, 8
  add esp, 8
  pop ebp
  ret
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
  mov ebx, eax
  mov eax, [ebp - 4]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$256a
  call __exception
EXCEPTION$256a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 8], 0
  push eax
  call CONSTRUCTOR$java.lang.String@char~#
  add esp, 8
  add esp, 4
  pop ebp
  ret
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
  mov eax, 56
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 11
  mov dword [eax + 12], 45
  mov dword [eax + 16], 50
  mov dword [eax + 20], 49
  mov dword [eax + 24], 52
  mov dword [eax + 28], 55
  mov dword [eax + 32], 52
  mov dword [eax + 36], 56
  mov dword [eax + 40], 51
  mov dword [eax + 44], 54
  mov dword [eax + 48], 52
  mov dword [eax + 52], 56
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 16
  pop ebp
  ret
label14end:
  mov eax, [ebp - -12]
  push eax
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
  imul eax, -1
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
  mov ebx, eax
  mov eax, [ebp - 4]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$337a
  call __exception
EXCEPTION$337a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
  jmp label16end
label16else:
label17start:
  mov eax, [ebp - -12]
  push eax
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
  push eax
  mov eax, 10
  pop ebx
  mov ecx, eax
  mov eax, ebx
  div ecx
  mov ebx, edx
  push ebx
  pop eax
  mov dword [ebp - 20], eax
  mov eax, [ebp - -12]
  push eax
  mov eax, 10
  pop ebx
  mov ecx, eax
  mov eax, ebx
  div ecx
  mov ebx, eax
  push ebx
  pop eax
  mov dword [ebp - -12], eax
  mov eax, [ebp - 20]
  push eax
  mov eax, 48
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  push eax
  mov eax, [ebp - 8]
  mov ebx, eax
  mov eax, [ebp - 4]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$375a
  call __exception
EXCEPTION$375a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
  add esp, 4
  jmp label17start
label17end:
label16end:
  mov eax, [ebp - 12]
  cmp eax, 0
  je label18end
  mov eax, 45
  push eax
  mov eax, [ebp - 8]
  mov ebx, eax
  mov eax, [ebp - 4]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$404a
  call __exception
EXCEPTION$404a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
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
  sub ebx, eax
  push ebx
  mov eax, [ebp - -12]
  pop ebx
  sub ebx, eax
  push ebx
  pop eax
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$452a
  call __exception
EXCEPTION$452a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp - 16]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$447a
  call __exception
EXCEPTION$447a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - -12]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - -12], eax
  jmp label19start
label19end:
  mov eax, [ebp - 16]
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 8], 0
  push eax
  call CONSTRUCTOR$java.lang.String@char~#
  add esp, 8
  add esp, 16
  pop ebp
  ret
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
  add esp, 0
  pop ebp
  ret
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
  add esp, 0
  pop ebp
  ret
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@boolean#
STATICMETHOD$java.lang.String$valueOf@boolean#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  cmp eax, 0
  je label20else
  mov eax, 28
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 4
  mov dword [eax + 12], 116
  mov dword [eax + 16], 114
  mov dword [eax + 20], 117
  mov dword [eax + 24], 101
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 0
  pop ebp
  ret
  jmp label20end
label20else:
  mov eax, 32
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 5
  mov dword [eax + 12], 102
  mov dword [eax + 16], 97
  mov dword [eax + 20], 108
  mov dword [eax + 24], 115
  mov dword [eax + 28], 101
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 0
  pop ebp
  ret
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
  mov eax, 28
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 4
  mov dword [eax + 12], 110
  mov dword [eax + 16], 117
  mov dword [eax + 20], 108
  mov dword [eax + 24], 108
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 0
  pop ebp
  ret
  jmp label21end
label21else:
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$561m
  call __exception
EXCEPTION$561m:
  push eax
  mov eax, [eax]
  add eax, 4
  mov eax, [eax]
  call eax
  add esp, 4
  add esp, 0
  pop ebp
  ret
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
  mov eax, 28
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 4
  mov dword [eax + 12], 110
  mov dword [eax + 16], 117
  mov dword [eax + 20], 108
  mov dword [eax + 24], 108
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 0
  pop ebp
  ret
  jmp label22end
label22else:
  mov eax, [ebp - -12]
  add esp, 0
  pop ebp
  ret
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
  add esp, 0
  pop ebp
  ret
label23end:
  mov ebx, [eax + 8]
  mov eax, [subtypecheckingtable*37+116]
  not eax 
  and eax, 1
  cmp eax, 0
  je label24end
  mov eax, 0
  add esp, 0
  pop ebp
  ret
label24end:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - -12]
  mov ebx, [eax + 8]
  mov ebx, [subtypecheckingtable+ebx*37+124] ; check cast expression
 cmp ebx, 0
 je subtypingcheck25 
 call __exception
subtypingcheck25:
  cmp eax, 0
  jne EXCEPTION$626
  call __exception
EXCEPTION$626:
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.util.Arrays$equals@char~#char~#
  call eax
  add esp, 12
  add esp, 0
  pop ebp
  ret
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
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label26end
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 0
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 8
  pop ebp
  ret
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
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovg eax, ecx
  cmp eax, 0
  je label27end
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 0
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 8
  pop ebp
  ret
label27end:
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - -12]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label28end
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 0
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 8
  pop ebp
  ret
label28end:
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - -12]
  pop ebx
  sub ebx, eax
  push ebx
  pop eax
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
  sub ebx, eax
  push ebx
  pop eax
  mov ebx, eax
  mov eax, [ebp - 8]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$714a
  call __exception
EXCEPTION$714a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label29start
label29end:
  mov eax, [ebp - 8]
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  mov dword [eax + 8], 0
  push eax
  call CONSTRUCTOR$java.lang.String@char~#
  add esp, 8
  add esp, 8
  pop ebp
  ret
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
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  push eax
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
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovle eax, ecx
  pop ebx ; pop last result
  and ebx, eax ; && op
  push ebx
  pop eax
  cmp eax, 0
  je label30end
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label30start
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
  sub ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
label31start:
  mov eax, [ebp - 8]
  push eax
  mov eax, 0
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  push eax
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
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovle eax, ecx
  pop ebx ; pop last result
  and ebx, eax ; && op
  push ebx
  pop eax
  cmp eax, 0
  je label31end
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  sub ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
  jmp label31start
label31end:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovg eax, ecx
  cmp eax, 0
  je label32else
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.Object
  mov dword [eax + 4],-4
  mov dword [eax + 8], 0
  push eax
  mov eax, 12
  call __malloc
  mov dword [eax], InterfaceTABLE$java.lang.String
  mov dword [eax + 4], 124
  pop ebx
  mov dword [eax + 8], ebx
  add esp, 8
  pop ebp
  ret
  jmp label32end
label32else:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  mov eax, [eax]
  call eax
  add esp, 12
  add esp, 8
  pop ebp
  ret
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
  cmp eax, 0
  jne EXCEPTION$852$0
  call __exception
EXCEPTION$852$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label33end
  mov eax, 31
  push eax
  mov eax, [ebp - 4]
  pop ebx
  imul ebx, eax
  push ebx
  pop eax
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$869a
  call __exception
EXCEPTION$869a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  add esp, 4
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
  jmp label33start
label33end:
  add esp, 4
  mov eax, [ebp - 4]
  add esp, 4
  pop ebp
  ret
  add esp, 4
  pop ebp
  ret

global METHOD$java.lang.String$toString@
METHOD$java.lang.String$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  add esp, 0
  pop ebp
  ret
  pop ebp
  ret

global METHOD$java.lang.String$compareTo@java.lang.Object#
METHOD$java.lang.String$compareTo@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov ebx, [eax + 8]
  mov ebx, [subtypecheckingtable+ebx*37+124] ; check cast expression
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
  add esp, 0
  pop ebp
  ret
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
  cmp eax, 0
  jne EXCEPTION$934$0
  call __exception
EXCEPTION$934$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  push eax
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$940$0
  call __exception
EXCEPTION$940$0:
  mov eax, [eax + 8]
  cmp eax, 0
  jne EXCEPTION$940$1
  call __exception
EXCEPTION$940$1:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  pop ebx ; pop last result
  and ebx, eax ; && op
  push ebx
  pop eax
  cmp eax, 0
  je label36end
  mov eax, 0
  add esp, 8
  pop ebp
  ret
label36end:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  cmp eax, 0
  jne EXCEPTION$953$0
  call __exception
EXCEPTION$953$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  cmp eax, 0
  je label37end
  mov eax, -1
  add esp, 8
  pop ebp
  ret
label37end:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$965$0
  call __exception
EXCEPTION$965$0:
  mov eax, [eax + 8]
  cmp eax, 0
  jne EXCEPTION$965$1
  call __exception
EXCEPTION$965$1:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  cmp eax, 0
  je label38end
  mov eax, 1
  add esp, 8
  pop ebp
  ret
label38end:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$976a
  call __exception
EXCEPTION$976a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$981$0
  call __exception
EXCEPTION$981$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$981a
  call __exception
EXCEPTION$981a:
  push eax
  mov eax, ebx
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
  add esp, 8
  pop ebp
  ret
label39end:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$994a
  call __exception
EXCEPTION$994a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$999$0
  call __exception
EXCEPTION$999$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$999a
  call __exception
EXCEPTION$999a:
  push eax
  mov eax, ebx
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
  add esp, 8
  pop ebp
  ret
label40end:
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label35start
label35end:
  mov eax, 0
  add esp, 8
  pop ebp
  ret
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
  cmp eax, 0
  jne EXCEPTION$1036$0
  call __exception
EXCEPTION$1036$0:
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
  cmp eax, 0
  jne EXCEPTION$1050$0
  call __exception
EXCEPTION$1050$0:
  mov eax, [eax + 8]
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
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$1066a
  call __exception
EXCEPTION$1066a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - 8]
  mov ebx, eax
  mov eax, [ebp - 4]
  cmp ebx, [eax + 8]
  jnge EXCEPTION$1061a
  call __exception
EXCEPTION$1061a:
  push eax
  mov eax, ebx
  mov ebx, 4
  mul ebx
  add eax, 12
  pop ebx
  add eax, ebx
  pop ebx
  mov dword [eax], ebx
  mov eax, ebx
  add esp, 4
  mov eax, [ebp - 8]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
  jmp label41start
label41end:
  add esp, 4
  mov eax, [ebp - 4]
  add esp, 4
  pop ebp
  ret
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
  cmp eax, 0
  jne EXCEPTION$1129m
  call __exception
EXCEPTION$1129m:
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
  add ebx, eax
  push ebx
  pop eax
  push eax
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
  jmp label44end
label44else:
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp - 4]
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$1156a
  call __exception
EXCEPTION$1156a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$1163$0
  call __exception
EXCEPTION$1163$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - 8]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$1163a
  call __exception
EXCEPTION$1163a:
  push eax
  mov eax, ebx
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
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 8], eax
  jmp label43start
label43end:
  mov eax, [ebp - 12]
  cmp eax, 0
  je label46end
  mov eax, [ebp - 4]
  add esp, 12
  pop ebp
  ret
label46end:
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  jmp label42start
label42end:
  mov eax, -1
  add esp, 8
  pop ebp
  ret
  add esp, 8
  pop ebp
  ret

