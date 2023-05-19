.global _start
.align 2

_start:
   // Declare a
   MOV X0, #1
   SUB FP, SP, #16
   SUB SP, SP, #16
   STR X0, [FP]

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80
