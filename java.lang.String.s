global VTABLE$java.lang.String
VTABLE$java.lang.String:
  dd METHOD$java.lang.String$equals@java.lang.Object#
  dd METHOD$java.lang.String$toString@
  dd METHOD$java.lang.String$hashCode@
  dd METHOD$java.lang.Object$clone@
  dd METHOD$java.lang.Object$getClass@
  dd METHOD$java.lang.String$length@
  dd METHOD$java.lang.String$charAt@int#
  dd METHOD$java.lang.String$concat@java.lang.String#
  dd METHOD$java.lang.String$substring@int#int#
  dd METHOD$java.lang.String$trim@
  dd METHOD$java.lang.String$compareTo@java.lang.Object#
  dd METHOD$java.lang.String$compareTo@java.lang.String#
  dd METHOD$java.lang.String$toCharArray@
  dd METHOD$java.lang.String$indexOf@java.lang.String#

global METHOD$java.lang.String$length@
METHOD$java.lang.String$length@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  pop ebp
  ret

global METHOD$java.lang.String$charAt@int#
METHOD$java.lang.String$charAt@int#:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - -12]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@
CONSTRUCTOR$java.lang.String@:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.String
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, 0
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@char[]#
CONSTRUCTOR$java.lang.String@char[]#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.String
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [ebp - -12]
  mov eax, 0
nullstart:
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  cmp eax 0
  je nullend
  mov eax, [ebp + 8]
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  mov eax, [ebp - 4]
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 1
  je nullstart
nullend:
  add esp, 4
  mov eax, [ebp + 8]
  pop ebp
  ret

global CONSTRUCTOR$java.lang.String@java.lang.String#
CONSTRUCTOR$java.lang.String@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, 8
  call __malloc
  mov dword [ebp + 8], eax
  mov dword [eax], VTABLE$java.lang.String
  mov dword [eax + 4], 0
  mov eax, [ebp + 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.String$concat@java.lang.String#
METHOD$java.lang.String$concat@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  mov eax, [ebp - 8]
  mov eax, 0
nullstart:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 8]
  mov eax, 0
nullstart:
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@char#
STATICMETHOD$java.lang.String$valueOf@char#:
  push ebp
  mov ebp, esp
  sub esp, 4
  mov eax, 1
  mov eax, [ebp - 4]
  mov eax, 0
  mov eax, [ebp - -12]
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 4
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@int#
STATICMETHOD$java.lang.String$valueOf@int#:
  push ebp
  mov ebp, esp
  sub esp, 16
  mov eax, 15
  mov eax, 0
  mov eax, 0
  mov eax, [ebp - -12]
  mov eax, -2147483648
  cmp eax 0
  je nullend
  mov eax, 0
nullend:
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullend
  mov eax, [ebp - 16]
  mov eax, 1
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
nullend:
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullelse
  mov eax, [ebp - 4]
  mov eax, [ebp - 12]
  mov eax, 0
  mov eax, [ebp - 12]
  mov eax, [ebp - 12]
  mov eax, 1
  je nullend
nullelse:
nullstart:
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullend
  sub esp, 4
  mov eax, [ebp - -12]
  mov eax, 10
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  mov eax, 10
  mov eax, [ebp - 4]
  mov eax, [ebp - 12]
  mov eax, [ebp - 20]
  mov eax, 0
  mov eax, [ebp - 12]
  mov eax, [ebp - 12]
  mov eax, 1
  add esp, 4
  je nullstart
nullend:
nullend:
  mov eax, [ebp - 16]
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, [ebp - 12]
  mov eax, -1
  mov eax, [ebp - 12]
  mov eax, [ebp - 12]
  mov eax, 1
nullend:
  mov eax, [ebp - 12]
  mov eax, [ebp - -12]
  mov eax, 0
nullstart:
  mov eax, [ebp - -12]
  mov eax, [ebp - 12]
  cmp eax 0
  je nullend
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [ebp - 4]
  mov eax, [ebp - 12]
  mov eax, 1
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  mov eax, [ebp - -12]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 8]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 16
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@short#
STATICMETHOD$java.lang.String$valueOf@short#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@byte#
STATICMETHOD$java.lang.String$valueOf@byte#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.lang.String$valueOf@int#
  call eax
  add esp, 8
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@boolean#
STATICMETHOD$java.lang.String$valueOf@boolean#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  cmp eax 0
  je nullelse
  mov eax, 0
  je nullend
nullelse:
  mov eax, 0
nullend:
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@java.lang.Object#
STATICMETHOD$java.lang.String$valueOf@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullelse
  mov eax, 0
  je nullend
nullelse:
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 4
  call eax
  add esp, 4
nullend:
  pop ebp
  ret

global STATICMETHOD$java.lang.String$valueOf@java.lang.String#
STATICMETHOD$java.lang.String$valueOf@java.lang.String#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullelse
  mov eax, 0
  je nullend
nullelse:
  mov eax, [ebp - -12]
nullend:
  pop ebp
  ret

global METHOD$java.lang.String$equals@java.lang.Object#
METHOD$java.lang.String$equals@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullend
  mov eax, 0
nullend:
  mov eax, [ebp - -12]
  cmp eax 0
  je nullend
  mov eax, 0
nullend:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  push eax
  mov eax, [ebp - -12]
  push eax
  push 0
  mov eax, STATICMETHOD$java.util.Arrays$equals@char[]#char[]#
  call eax
  add esp, 12
  pop ebp
  ret

global METHOD$java.lang.String$substring@int#int#
METHOD$java.lang.String$substring@int#int#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov eax, [ebp - -12]
  mov eax, 0
  cmp eax 0
  je nullend
nullend:
  mov eax, [ebp - -16]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je nullend
nullend:
  mov eax, [ebp - -16]
  mov eax, [ebp - -12]
  cmp eax 0
  je nullend
nullend:
  mov eax, [ebp - -16]
  mov eax, [ebp - -12]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
nullstart:
  mov eax, [ebp - 8]
  mov eax, [ebp - -16]
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 4]
  push eax
  push 0
  call CONSTRUCTOR$java.lang.String@char[]#
  add esp, 8
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$trim@
METHOD$java.lang.String$trim@:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov eax, 0
  mov eax, [ebp - 4]
  mov eax, 0
nullstart:
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, -1
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  mov eax, 1
nullstart:
  mov eax, [ebp - 8]
  mov eax, 0
  mov eax, [ebp - 8]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 24
  call eax
  add esp, 8
  mov eax, -1
  cmp eax 0
  je nullend
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 4]
  mov eax, [ebp - 8]
  cmp eax 0
  je nullelse
  je nullend
nullelse:
  mov eax, [ebp - 4]
  push eax
  mov eax, [ebp - 8]
  mov eax, 1
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 32
  call eax
  add esp, 12
nullend:
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$hashCode@
METHOD$java.lang.String$hashCode@:
  push ebp
  mov ebp, esp
  sub esp, 4
  mov eax, 0
  mov eax, 0
nullstart:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, 31
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  add esp, 4
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  add esp, 4
  mov eax, [ebp - 4]
  add esp, 4
  pop ebp
  ret

global METHOD$java.lang.String$toString@
METHOD$java.lang.String$toString@:
  push ebp
  mov ebp, esp
  mov eax, [ebp + 8]
  pop ebp
  ret

global METHOD$java.lang.String$compareTo@java.lang.Object#
METHOD$java.lang.String$compareTo@java.lang.Object#:
  push ebp
  mov ebp, esp
  mov eax, [ebp - -12]
  push eax
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 44
  call eax
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$compareTo@java.lang.String#
METHOD$java.lang.String$compareTo@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov eax, 1
nullstart:
  mov eax, [ebp - 4]
  cmp eax 0
  je nullend
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  cmp eax 0
  je nullend
  mov eax, 0
nullend:
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  cmp eax 0
  je nullend
  mov eax, -1
nullend:
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  cmp eax 0
  je nullend
  mov eax, 1
nullend:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  cmp eax 0
  je nullend
  mov eax, -1
nullend:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  cmp eax 0
  je nullend
  mov eax, 1
nullend:
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  mov eax, 0
  add esp, 8
  pop ebp
  ret

global METHOD$java.lang.String$toCharArray@
METHOD$java.lang.String$toCharArray@:
  push ebp
  mov ebp, esp
  sub esp, 4
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, 0
nullstart:
  mov eax, [ebp - 8]
  mov eax, [ebp - 4]
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
  mov eax, [ebp - 8]
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  add esp, 4
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  add esp, 4
  mov eax, [ebp - 4]
  add esp, 4
  pop ebp
  ret

global METHOD$java.lang.String$indexOf@java.lang.String#
METHOD$java.lang.String$indexOf@java.lang.String#:
  push ebp
  mov ebp, esp
  sub esp, 8
  mov eax, 0
  mov eax, 0
  mov eax, [ebp - 4]
  mov eax, 0
nullstart:
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je nullend
  sub esp, 4
  mov eax, 1
  mov eax, [ebp - 8]
  mov eax, 0
nullstart:
  mov eax, [ebp - 8]
  mov eax, [ebp - -12]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je nullend
  mov eax, [ebp - 8]
  mov eax, [ebp - 4]
  mov eax, [ebp + 8]
  push eax
  mov eax, [eax]
  add eax, 20
  call eax
  add esp, 4
  cmp eax 0
  je nullelse
  mov eax, [ebp - 12]
  mov eax, 0
  je nullend
nullelse:
  mov eax, [ebp + 8]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  mov eax, [ebp - 4]
  mov eax, [ebp - -12]
  mov eax, [eax + 4]
  mov eax, [ebp - 8]
  cmp eax 0
  je nullend
  mov eax, [ebp - 12]
  mov eax, 0
nullend:
nullend:
  mov eax, [ebp - 8]
  mov eax, [ebp - 8]
  mov eax, 1
  je nullstart
nullend:
  mov eax, [ebp - 12]
  cmp eax 0
  je nullend
  mov eax, [ebp - 4]
nullend:
  add esp, 4
  mov eax, [ebp - 4]
  mov eax, [ebp - 4]
  mov eax, 1
  je nullstart
nullend:
  mov eax, -1
  add esp, 8
  pop ebp
  ret

