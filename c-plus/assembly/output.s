.global _start
.align 2

_start:
   SUB SP, SP #4

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80