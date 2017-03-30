extern __malloc
extern STATICMETHOD$java.io.PrintStream$nativeWrite@int#
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern METHOD$java.lang.Object$hashCode@
extern $$subtypecheckingtable
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.io.OutputStream
InterfaceTABLE$java.io.OutputStream:
  dd 00000000

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
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.io.OutputStream
  mov dword [eax + 4], 0
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.io.OutputStream$write@char#
METHOD$java.io.OutputStream$write@char#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov ebx, [eax + 8]
  mov ebx,[$$subtypecheckingtable+ebx+-4]
 cmp ebx, 0
 je subtypingcheck1 
 call __exception subtypingcheck1:
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 28
  mov eax, [eax]
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

