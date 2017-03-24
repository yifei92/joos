extern __malloc
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern STATICMETHOD$java.lang.String$valueOf@short#
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Short
InterfaceTABLE$java.lang.Short:
  dd 00000000

global VTABLE$java.lang.Short
VTABLE$java.lang.Short:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Short$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Short$intValue@

global CONSTRUCTOR$java.lang.Short@short#
CONSTRUCTOR$java.lang.Short@short#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Short
  mov dword [eax + 4], 0
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
  mov eax, [eax + 4]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Short@
CONSTRUCTOR$java.lang.Short@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Short
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Short$toString@
METHOD$java.lang.Short$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@short#
  call eax
  add esp, 8
  pop ebp
  ret

