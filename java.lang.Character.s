global VTABLE$java.lang.Character
VTABLE$java.lang.Character:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Character$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global CONSTRUCTOR$java.lang.Character@char#
CONSTRUCTOR$java.lang.Character@char#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Character
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - -12]
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Character@
CONSTRUCTOR$java.lang.Character@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Character
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Character$toString@
METHOD$java.lang.Character$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@char#
  call eax
  add esp, 8
  pop ebp
  ret

