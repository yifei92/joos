global VTABLE$java.lang.Integer
VTABLE$java.lang.Integer:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Integer$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Integer$intValue@

global InterfaceTABLE$java.lang.IntegerInterfaceTABLE$java.lang.Integer:
  dd 00000000
  dd 00000000
  dd 00000000
  dd 00000000

global STATICFIELD$java.lang.Integer$MAX_VALUE
STATICFIELD$java.lang.Integer$MAX_VALUE:
  dd 0

global CONSTRUCTOR$java.lang.Integer@int#
CONSTRUCTOR$java.lang.Integer@int#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Integer
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - -12]
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Integer$intValue@
METHOD$java.lang.Integer$intValue@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  pop ebp
  ret

global STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 12
  mov eax, 0
  mov eax, 0
  mov eax, 0
block5start:
  mov eax, [ebp - 12]
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, -1
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, 0
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, 9
  cmp eax 0
  je block5end
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, -1
  cmp eax 0
  je block7else
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  je block7end
block7else:
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 10
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, 0
block7end:
  mov eax, [ebp - 12]
  mov eax, [ebp - 12]
  mov eax, 1
  je block5start
block5end:
  mov eax, [ebp - 8]
  cmp eax 0
  je block5end
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
block5end:
  mov eax, [ebp - 4]
  add esp, 12
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Integer@java.lang.String#
CONSTRUCTOR$java.lang.Integer@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Integer
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
  call eax
  add esp, 8
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Integer@
CONSTRUCTOR$java.lang.Integer@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Integer
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Integer$toString@
METHOD$java.lang.Integer$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebp
  ret

