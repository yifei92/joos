extern __exception
extern CONSTRUCTOR$java.lang.Object@
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern METHOD$java.lang.Object$toString@
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.util.Arrays
InterfaceTABLE$java.util.Arrays:

global VTABLE$java.util.Arrays
VTABLE$java.util.Arrays:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Object$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@

section .data
section .text
global STATICFIELDINITIALIZER$java.util.Arrays
STATICFIELDINITIALIZER$java.util.Arrays:
  ret
global CONSTRUCTOR$java.util.Arrays@
CONSTRUCTOR$java.util.Arrays@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Object@
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global STATICMETHOD$java.util.Arrays$equals@boolean~#boolean~#
STATICMETHOD$java.util.Arrays$equals@boolean~#boolean~#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$33$0
  call __exception
EXCEPTION$33$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - -16]
  cmp eax, 0
  jne EXCEPTION$37$0
  call __exception
EXCEPTION$37$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovne eax, ecx
  cmp eax, 0
  je label2end
  mov eax, 0
label2end:
  mov eax, 0
  mov dword [ebp - 4], eax
label3start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$53$0
  call __exception
EXCEPTION$53$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label3end
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$65a
  call __exception
EXCEPTION$65a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$70a
  call __exception
EXCEPTION$70a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovne eax, ecx
  cmp eax, 0
  je label4end
  mov eax, 0
label4end:
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  je label3start
label3end:
  add esp, 4
  mov eax, 1
  pop ebp
  ret

global STATICMETHOD$java.util.Arrays$equals@char~#char~#
STATICMETHOD$java.util.Arrays$equals@char~#char~#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$100$0
  call __exception
EXCEPTION$100$0:
  mov eax, [eax + 8]
  push eax
  mov eax, [ebp - -16]
  cmp eax, 0
  jne EXCEPTION$104$0
  call __exception
EXCEPTION$104$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovne eax, ecx
  cmp eax, 0
  je label5end
  mov eax, 0
label5end:
  mov eax, 0
  mov dword [ebp - 4], eax
label6start:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$120$0
  call __exception
EXCEPTION$120$0:
  mov eax, [eax + 8]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  cmp eax, 0
  je label6end
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$132a
  call __exception
EXCEPTION$132a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  push eax
  mov eax, [ebp - -16]
  push eax
  mov eax, [ebp - 4]
  mov ebx, eax
  pop eax
  cmp ebx, [eax + 8]
  jnge EXCEPTION$137a
  call __exception
EXCEPTION$137a:
  push eax
  mov eax, ebx
  add eax, 3
  mov ebx, 4
  mul ebx
  mov ebx, eax
  pop eax
  add eax, ebx
  mov eax, [eax]
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovne eax, ecx
  cmp eax, 0
  je label7end
  mov eax, 0
label7end:
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
  je label6start
label6end:
  add esp, 4
  mov eax, 1
  pop ebp
  ret

