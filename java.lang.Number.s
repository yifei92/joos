extern __exception
extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Number
InterfaceTABLE$java.lang.Number:

global VTABLE$java.lang.Number
VTABLE$java.lang.Number:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Number$intValue@

section .data
section .text
global STATICFIELDINITIALIZER$java.lang.Number
STATICFIELDINITIALIZER$java.lang.Number:
  ret
global METHOD$java.lang.Number$intValue@
METHOD$java.lang.Number$intValue@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Number@
CONSTRUCTOR$java.lang.Number@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Number
  mov dword [eax + 4], 148
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

