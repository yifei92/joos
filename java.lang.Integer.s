extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.lang.Object$hashCode@
extern $$subtypecheckingtable
extern STATICMETHOD$java.lang.String$valueOf@int#
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Integer
InterfaceTABLE$java.lang.Integer:
  dd 00000000

global VTABLE$java.lang.Integer
VTABLE$java.lang.Integer:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Integer$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Integer$intValue@

global STATICFIELD$java.lang.Integer$MAX_VALUE
STATICFIELD$java.lang.Integer$MAX_VALUE:
  dd 0

global CONSTRUCTOR$java.lang.Integer@int#
CONSTRUCTOR$java.lang.Integer@int#:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Integer
  mov dword [eax + 4], 0
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Integer$intValue@
METHOD$java.lang.Integer$intValue@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebp
  ret

global STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 12
  mov eax, 0
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - 12], eax
  mov eax, [ebp - 12]
  mov ebx, eax
  mov eax, [ebp - -12]
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
  cmp ebx, eax
  mov eax, 0
cmovl eax, 1  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  mov eax, 45
  cmp ebx, eax
  mov eax, 0
cmove eax, 1  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  mov eax, 48
  mov eax, 48
  cmp ebx, eax
  mov eax, 0
cmovge eax, 1  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  mov eax, 57
  mov eax, 57
  cmp ebx, eax
  mov eax, 0
cmovle eax, 1  cmp eax, 0
  je block5end
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  mov ebx, eax
  mov eax, 45
  cmp ebx, eax
  mov eax, 0
cmove eax, 1  cmp eax, 0
  je block7else
  mov eax, [ebp - 8]
  mov dword [ebp - 8], eax
  je block7end
block7else:
  mov eax, [ebp - 4]
  mov eax, 10
  mov ebx, eax
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
  call eax
  add esp, 8
  add eax, ebx
  mov dword [ebp - 4], eax
block7end:
  mov eax, [ebp - 12]
  mov ebx, eax
  mov eax, 1
  add eax, ebx
  mov dword [ebp - 12], eax
  je block5start
block5end:
  mov eax, [ebp - 8]
  cmp eax, 0
  je 6end
  mov eax, [ebp - 4]
  mov dword [ebp - 4], eax
block5end:
  mov eax, [ebp - 4]
  add esp, 12
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Integer@java.lang.String#
CONSTRUCTOR$java.lang.Integer@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Integer
  mov dword [eax + 4], 0
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
  call eax
  add esp, 8
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Integer@
CONSTRUCTOR$java.lang.Integer@:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Integer
  mov dword [eax + 4], 0
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
  mov eax, 0
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Integer$toString@
METHOD$java.lang.Integer$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebp
  ret

