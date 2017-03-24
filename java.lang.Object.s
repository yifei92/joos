global VTABLE$java.lang.Object
VTABLE$java.lang.Object:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global CONSTRUCTOR$java.lang.Object@
CONSTRUCTOR$java.lang.Object@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.Object
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Object$equals@java.lang.Object#
METHOD$java.lang.Object$equals@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [ebp - -12]
  pop ebp
  ret

global METHOD$java.lang.Object$toString@
METHOD$java.lang.Object$toString@:
  push ebp
  mov ebp, esp
  mov eax, 0
  pop ebp
  ret

global METHOD$java.lang.Object$hashCode@
METHOD$java.lang.Object$hashCode@:
  push ebp
  mov ebp, esp
  mov eax, 42
  pop ebp
  ret

global METHOD$java.lang.Object$clone@
METHOD$java.lang.Object$clone@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Object$getClass@
METHOD$java.lang.Object$getClass@:
  push ebp
  mov ebp, esp
  mov eax, 0
  pop ebp
  ret

