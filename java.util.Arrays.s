extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.lang.Object$hashCode@
extern $$subtypecheckingtable
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.util.Arrays
InterfaceTABLE$java.util.Arrays:
  dd 00000000

global VTABLE$java.util.Arrays
VTABLE$java.util.Arrays:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global CONSTRUCTOR$java.util.Arrays@
CONSTRUCTOR$java.util.Arrays@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.util.Arrays
  mov dword [eax + 4], 0
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$java.util.Arrays$equals@boolean~#boolean~#
STATICMETHOD$java.util.Arrays$equals@boolean~#boolean~#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp - -16]
  cmp ebx, eax
  mov eax, 0
cmovne eax, 1  cmp eax, 0
  je 2end
  mov eax, 0
block112end:
  mov eax, 0
  mov dword [ebp - 4], eax
for_body114start:
  mov eax, [ebp - 4]
  mov ebx, eax
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  cmp ebx, eax
  mov eax, 0
cmovl eax, 1  cmp eax, 0
  je for_body114end
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - 4]
  add eax, 2
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, eab
  mov eax, [eax]
  mov ebx, eax
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - 4]
  add eax, 2
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, eab
  mov eax, [eax]
  cmp ebx, eax
  mov eax, 0
cmovne eax, 1  cmp eax, 0
  je 3end
  mov eax, 0
for_body114end:
  add esp, 4
  mov eax, [ebp - 4]
  mov ebx, eax
  mov eax, 1
  add eax, ebx
  mov dword [ebp - 4], eax
  je for_body114start
for_body114end:
  add esp, 4
  mov eax, 1
  pop ebp
  ret

global STATICMETHOD$java.util.Arrays$equals@char~#char~#
STATICMETHOD$java.util.Arrays$equals@char~#char~#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp - -16]
  cmp ebx, eax
  mov eax, 0
cmovne eax, 1  cmp eax, 0
  je 4end
  mov eax, 0
block116end:
  mov eax, 0
  mov dword [ebp - 4], eax
for_body118start:
  mov eax, [ebp - 4]
  mov ebx, eax
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  cmp ebx, eax
  mov eax, 0
cmovl eax, 1  cmp eax, 0
  je for_body118end
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - 4]
  add eax, 2
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, eab
  mov eax, [eax]
  mov ebx, eax
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - 4]
  add eax, 2
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, eab
  mov eax, [eax]
  cmp ebx, eax
  mov eax, 0
cmovne eax, 1  cmp eax, 0
  je 5end
  mov eax, 0
for_body118end:
  add esp, 4
  mov eax, [ebp - 4]
  mov ebx, eax
  mov eax, 1
  add eax, ebx
  mov dword [ebp - 4], eax
  je for_body118start
for_body118end:
  add esp, 4
  mov eax, 1
  pop ebp
  ret

