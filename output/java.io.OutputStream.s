extern __exception
extern CONSTRUCTOR$java.lang.Object@
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern NATIVEjava.io.OutputStream.nativeWrite
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.io.OutputStream
InterfaceTABLE$java.io.OutputStream:

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

section .data
section .text
global STATICFIELDINITIALIZER$java.io.OutputStream
STATICFIELDINITIALIZER$java.io.OutputStream:
  ret
global CONSTRUCTOR$java.io.OutputStream@
CONSTRUCTOR$java.io.OutputStream@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
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
  pop eax
  call NATIVEjava.io.OutputStream.nativeWrite
  pop ebp
  ret

global METHOD$java.io.OutputStream$flush@
METHOD$java.io.OutputStream$flush@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

