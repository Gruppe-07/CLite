.global _start
.align 2

_start:
   // Declare i
   MOV X0, #5
   MOV X1, #2
   ADD X2, X0, X1

   MOV X0, X2
   MOV X1, #3
   ADD X2, X0, X1

   MOV X0, #1
   MOV X1, X2
   MUL X2, X0, X1

   STR X2, [SP, #-16]!

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80
