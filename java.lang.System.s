global VTABLE$java.lang.System
VTABLE$java.lang.System:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.SystemInterfaceTABLE$java.lang.System:
  dd 00000000
  dd 00000000
  dd 00000000
  dd 00000000

global STATICFIELD$java.lang.System$out
STATICFIELD$java.lang.System$out:
  dd 0

global STATICMETHOD$java.lang.System$gc@
STATICMETHOD$java.lang.System$gc@:
  push ebp
  mov ebp, esp
  pop ebp
  ret

global CONSTRUCTOR$java.lang.System@
CONSTRUCTOR$java.lang.System@:
  push ebp
  mov ebp, esp
  mov eax, 4
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.System
  mov eax, [ebp + 8]
  pop ebp
  ret

