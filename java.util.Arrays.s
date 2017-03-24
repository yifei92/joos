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
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.util.Arrays
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$java.util.Arrays$equals@boolean[]#boolean[]#
STATICMETHOD$java.util.Arrays$equals@boolean[]#boolean[]#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, [ebp - -16]
  cmp eax 0
  je block112end
  mov eax, 0
block112end:
  mov eax, 0
for_body114start:
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  cmp eax 0
  je for_body114end
  mov eax, [ebp - -12]
  mov eax, [ebp - 4]
  mov eax, [ebp - -16]
  mov eax, [ebp - 4]
  cmp eax 0
  je for_body114end
  mov eax, 0
for_body114end:
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 1
  je for_body114start
for_body114end:
  add esp, 4
  mov eax, 1
  pop ebp
  ret

global STATICMETHOD$java.util.Arrays$equals@char[]#char[]#
STATICMETHOD$java.util.Arrays$equals@char[]#char[]#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, [ebp - -16]
  cmp eax 0
  je block116end
  mov eax, 0
block116end:
  mov eax, 0
for_body118start:
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  cmp eax 0
  je for_body118end
  mov eax, [ebp - -12]
  mov eax, [ebp - 4]
  mov eax, [ebp - -16]
  mov eax, [ebp - 4]
  cmp eax 0
  je for_body118end
  mov eax, 0
for_body118end:
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 1
  je for_body118start
for_body118end:
  add esp, 4
  mov eax, 1
  pop ebp
  ret

