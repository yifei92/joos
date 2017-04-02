extern __exception
extern METHOD$java.lang.Object$equals@java.lang.Object#
extern METHOD$java.lang.Object$clone@
extern subtypecheckingtable
extern METHOD$java.lang.Object$hashCode@
extern CONSTRUCTOR$java.lang.Number@
extern STATICMETHOD$java.lang.String$valueOf@int#
extern METHOD$java.lang.Object$getClass@

global InterfaceTABLE$java.lang.Integer
InterfaceTABLE$java.lang.Integer:

global VTABLE$java.lang.Integer
VTABLE$java.lang.Integer:
  dd METHOD$java.lang.Object$equals@java.lang.Object#
  dd METHOD$java.lang.Integer$toString@
  dd METHOD$java.lang.Object$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.Integer$intValue@

section .data
global STATICFIELD$java.lang.Integer$MAX_VALUE
STATICFIELD$java.lang.Integer$MAX_VALUE:
  dd 0

section .text
global STATICFIELDINITIALIZER$java.lang.Integer
STATICFIELDINITIALIZER$java.lang.Integer:
  mov eax, 2147483647
  mov [STATICFIELD$java.lang.Integer$MAX_VALUE], eax
  ret
global CONSTRUCTOR$java.lang.Integer@int#
CONSTRUCTOR$java.lang.Integer@int#:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Number@
  add esp, 4
  mov ebx, [ebp + 8]
  mov dword [ebx + 8], eax
  mov eax, [ebp - -12]
  mov ebx, eax
  mov eax, [ebp + 8]
  mov dword [eax + 8], ebx
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Integer$intValue@
METHOD$java.lang.Integer$intValue@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  add esp, 0
  pop ebp
  ret
  pop ebp
  ret

global STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 12
  mov eax, 0
  mov dword [ebp - 4], eax
  mov eax, 0
  mov dword [ebp - 8], eax
  mov eax, 0
  mov dword [ebp - 12], eax
label8start:
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$69m
  call __exception
EXCEPTION$69m:
  push eax
  mov eax, [eax]
  add eax, 20
  mov eax, [eax]
  call eax
  add esp, 4
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovl eax, ecx
  push eax
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$76m
  call __exception
EXCEPTION$76m:
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, 45
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  push eax
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$88m
  call __exception
EXCEPTION$88m:
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, 48
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovge eax, ecx
  push eax
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$99m
  call __exception
EXCEPTION$99m:
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, 57
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmovle eax, ecx
  pop ebx ; pop last result
  and ebx, eax ; && op
  push ebx
  pop eax
  pop ebx ; pop last result
  or ebx, eax  ; || op
  push ebx
  pop eax
  pop ebx ; pop last result
  and ebx, eax ; && op
  push ebx
  pop eax
  cmp eax, 0
  je label8end
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$115m
  call __exception
EXCEPTION$115m:
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  push eax
  mov eax, 45
  pop ebx
  cmp ebx, eax
  mov eax, 0
mov ecx, 1
cmove eax, ecx
  cmp eax, 0
  je label9else
  mov eax, [ebp - 8]
  not eax 
  and eax, 1
  mov dword [ebp - 8], eax
  jmp label9end
label9else:
  mov eax, [ebp - 4]
  push eax
  mov eax, 10
  pop ebx
  imul ebx, eax
  push ebx
  pop eax
  push eax
  mov eax, [ebp - 12]
  push eax
  mov eax, [ebp - -12]
  cmp eax, 0
  jne EXCEPTION$139m
  call __exception
EXCEPTION$139m:
  push eax
  mov eax, [eax]
  add eax, 24
  mov eax, [eax]
  call eax
  add esp, 8
  pop ebx
  add ebx, eax
  push ebx
  mov eax, 48
  pop ebx
  sub ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 4], eax
label9end:
  mov eax, [ebp - 12]
  push eax
  mov eax, 1
  pop ebx
  add ebx, eax
  push ebx
  pop eax
  mov dword [ebp - 12], eax
  jmp label8start
label8end:
  mov eax, [ebp - 8]
  cmp eax, 0
  je label10end
  mov eax, [ebp - 4]
  imul eax, -1
  mov dword [ebp - 4], eax
label10end:
  mov eax, [ebp - 4]
  add esp, 12
  pop ebp
  ret
  add esp, 12
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Integer@java.lang.String#
CONSTRUCTOR$java.lang.Integer@java.lang.String#:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Number@
  add esp, 4
  mov ebx, [ebp + 8]
  mov dword [ebx + 8], eax
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.Integer$parseInt@java.lang.String#
  call eax
  add esp, 8
  mov ebx, eax
  mov eax, [ebp + 8]
  mov dword [eax + 8], ebx
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.Integer@
CONSTRUCTOR$java.lang.Integer@:
  push ebp
  mov ebp, esp
  push dword [ebp + 8]
  call CONSTRUCTOR$java.lang.Number@
  add esp, 4
  mov ebx, [ebp + 8]
  mov dword [ebx + 8], eax
  mov eax, 0
  mov ebx, eax
  mov eax, [ebp + 8]
  mov dword [eax + 8], ebx
  mov eax, ebx
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.Integer$toString@
METHOD$java.lang.Integer$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 8]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  add esp, 0
  pop ebp
  ret
  pop ebp
  ret

