.global _start
.align 2

_start:
   // Declare a
   MOV X6, #2
   MOV X7, #3
   MUL X8, X6, X7

   MOV X3, #1
   MOV X4, X8
   ADD X5, X3, X4

   MOV X6, #1
   MOV X7, #2
   MUL X9, X6, X7

   MOV X3, #1
   MOV X4, X9
   SUB X8, X3, X4

   MOV X0, X5
   MOV X1, X8
   ADD X2, X0, X1

   SUB FP, SP, #16
   SUB SP, SP, #16
   STR X2, [FP]

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80
