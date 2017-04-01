extern __exception
extern __malloc
extern CONSTRUCTOR$java.io.PrintStream@
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.System
InterfaceTABLE$java.lang.System:

global VTABLE$java.lang.System
VTABLE$java.lang.System:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
global STATICFIELD$java.lang.System$out
STATICFIELD$java.lang.System$out:
  dd 0

section .text
global STATICFIELDINITIALIZER$java.lang.System
STATICFIELDINITIALIZER$java.lang.System:
  push 0
  call CONSTRUCTOR$java.io.PrintStream@
  add esp, 4
  mov [STATICFIELD$java.lang.System$out], eax
  ret
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
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], InterfaceTABLE$java.lang.System
  mov dword [eax + 4], 60
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  pop ebp
  ret

