extern __exception
extern CONSTRUCTOR$java.lang.Object@
extern STATICMETHOD$java.lang.String$valueOf@byte#
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Byte
InterfaceTABLE$java.lang.Byte:

global VTABLE$java.lang.Byte
VTABLE$java.lang.Byte:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Byte$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
global STATICFIELD$java.lang.Byte$MAX_VALUE
STATICFIELD$java.lang.Byte$MAX_VALUE:
  dd 0

section .text
global STATICFIELDINITIALIZER$java.lang.Byte
STATICFIELDINITIALIZER$java.lang.Byte:
  mov eax, 127
  mov [STATICFIELD$java.lang.Byte$MAX_VALUE], eax
  ret
global CONSTRUCTOR$java.lang.Byte@byte#
CONSTRUCTOR$java.lang.Byte@byte#:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp + 8]
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Byte@
CONSTRUCTOR$java.lang.Byte@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Byte$toString@
METHOD$java.lang.Byte$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@byte#
  call eax
  add esp, 8
  pop ebp
  ret

