global VTABLE$java.io.OutputStream
VTABLE$java.io.OutputStream:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.io.OutputStream$write@char#
  dd METHOD$java.io.OutputStream$write@int#
  dd METHOD$java.io.OutputStream$flush@

global CONSTRUCTOR$java.io.OutputStream@
CONSTRUCTOR$java.io.OutputStream@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.io.OutputStream
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.io.OutputStream$write@char#
METHOD$java.io.OutputStream$write@char#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.io.OutputStream$write@int#
METHOD$java.io.OutputStream$write@int#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.io.PrintStream$nativeWrite@int#
  call eax
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.io.OutputStream$nativeWrite@int#
STATICMETHOD$java.io.OutputStream$nativeWrite@int#:
  push ebp
  mov ebp, esp
  pop ebp
  ret

global METHOD$java.io.OutputStream$flush@
METHOD$java.io.OutputStream$flush@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

