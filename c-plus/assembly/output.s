.global _start
.align 2

_start:
   // Declare i
   MOV X1, #10
   MOV X2, #10
   MUL X0, X1, X2

   MOV X1, #5
   ADD X0, X0, X1

   MOV X1, #3
   SUB X0, X0, X1

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80
