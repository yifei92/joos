extern __exception
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern STATICMETHOD$java.lang.String$valueOf@short#
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern CONSTRUCTOR$java.lang.Number@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Short
InterfaceTABLE$java.lang.Short:

global VTABLE$java.lang.Short
VTABLE$java.lang.Short:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Short$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Short$intValue@

section .data
section .text
global STATICFIELDINITIALIZER$java.lang.Short
STATICFIELDINITIALIZER$java.lang.Short:
  ret
global CONSTRUCTOR$java.lang.Short@short#
CONSTRUCTOR$java.lang.Short@short#:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Number@
  add esp, 4
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Short$intValue@
METHOD$java.lang.Short$intValue@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Short@
CONSTRUCTOR$java.lang.Short@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Number@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Short$toString@
METHOD$java.lang.Short$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@short#
  call eax
  add esp, 8
  pop ebp
  ret

