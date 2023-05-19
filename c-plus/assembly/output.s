.global _start
.align 2

_start:
   // Declare i
   MOV X0, #1
   MOV X2, #5
   MOV X3, #2
   SUB X2, X2, X3

   MOV X4, #5
   MOV X5, #2
   SUB X4, X4, X5

   MOV X3, X4
   MOV X2, #3
   ADD X3, X3, X2

   MOV X1, X3
   MUL X0, X0, X1

   STR X0, [SP, #-16]!

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80
