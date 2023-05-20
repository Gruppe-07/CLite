.global _start
.align 2

_start:
   B main

main:
   // Make room for local variables and potential parameter
   SUB FP, SP, #0
   SUB SP, SP, #0

   MOV X6, #1
   MOV X7, #2
   ADD X8, X6, X7

   MOV X3, X8
   MOV X4, #3
   ADD X5, X3, X4

   MOV X1, X5
   MOV X2, #5
   CMP X1, X2
   B.GE elseclause
   // Declare i
   MOV X1, #1
   STR X1, [FP, #-0]

   B endif
elseclause: 
   // Declare i
   MOV X1, #1
   STR X1, [FP, #-8]

endif:
   ADD SP, SP, #0

   MOV X16, #4
   SVC #0x80

   MOV     X0, #0
   MOV     X16, #1
   SVC     #0x80

