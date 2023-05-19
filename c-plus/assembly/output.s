.global _start
.align 2

_start:
   // Declare a
   MOV X0, #1
   MOV X1, #2
   ADD X0, X0, X1

   MOV X1, #3
   MOV X2, #5
   MUL X1, X1, X2

   MOV X4, #1
   MOV X5, #2
   ADD X4, X4, X5

   MOV X2, X4
   MOV X5, #3
   MOV X6, #5
   MUL X5, X5, X6

   MOV X3, X5
   ADD X2, X2, X3

   MOV X7, #1
   MOV X8, #2
   ADD X7, X7, X8

   MOV X8, #3
   MOV X9, #5
   MUL X8, X8, X9

   MOV X11, #1
   MOV X12, #2
   ADD X11, X11, X12

   MOV X9, X11
   MOV X12, #3
   MOV X13, #5
   MUL X12, X12, X13

   MOV X10, X12
   ADD X9, X9, X10

   MOV X3, X9
   MOV X6, #1
   SUB X3, X3, X6

   mov X16, #4
   svc #0x80

   mov     X0, #0
   mov     X16, #1
   svc     #0x80
