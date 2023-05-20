.global _start
.align 2

_start:
   B main

main:
   // Make room for local variables and potential parameter
   SUB FP, SP, #16
   SUB SP, SP, #16

   // Declare i
   MOV X1, #1
   STR X1, [FP, #-0]

   LDR X3, [FP, #-0] // Load i
   MOV X1, X3
   MOV X2, #1
loop:    CMP X1, X2
   B.EQ loopdone
   B loop
loopdone:
   ADD SP, SP, #16

   MOV X16, #4
   SVC #0x80

   MOV     X0, #0
   MOV     X16, #1
   SVC     #0x80

