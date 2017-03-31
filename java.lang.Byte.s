extern __exception
extern __malloc
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

global STATICFIELD$java.lang.Byte$MAX_VALUE
STATICFIELD$java.lang.Byte$MAX_VALUE:
  dd 0

global STATICFIELDINITIALIZER$java.lang.Byte
STATICFIELDINITIALIZER$java.lang.Byte:
  mov eax, 127
  ret
global CONSTRUCTOR$java.lang.Byte@byte#
CONSTRUCTOR$java.lang.Byte@byte#:
  push ebp
  mov ebp, esp
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Byte
  mov dword [eax + 4], 132
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
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
  mov eax, 12
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.Byte
  mov dword [eax + 4], 132
  mov dword [eax + 4], 0
  mov dword [eax + 8], 0
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

