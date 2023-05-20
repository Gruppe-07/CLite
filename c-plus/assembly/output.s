.global _start
.align 2
   .equ VAR, 0
   .equ RETURN, 8

_start:
   B main

f: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack
   // Make room for local variables and potential parameter
   SUB FP, SP, #32
   SUB SP, SP, #32

   STR X0, [FP] // Push parameter to stack
   // Declare b
   MOV X1, #1
   STR X1, [FP]

   // Declare c
   MOV X1, #1
   STR X1, [FP]

   // Declare d
   MOV X1, #1
   STR X1, [FP]

   MOV X4, #1
   MOV X5, #2
   MUL X6, X4, X5

   MOV X4, #2
   MOV X5, #3
   MUL X7, X4, X5

   MOV X1, X6
   MOV X2, X7
   ADD X3, X1, X2

   MOV X0, X3// Load return value into X0 register
   ADD SP, SP, #32
   LDP LR, FP, [SP], #16 // Restore LR, FP
   RET

main:
   // Make room for local variables and potential parameter
   SUB FP, SP, #16
   SUB SP, SP, #16

   // Declare i
   MOV X8, #1
   MOV X9, #5
   MUL X10, X8, X9

   MOV X6, X10
   MOV X4, #2
   ADD X5, X6, X4

   MOV X0, X5 // Load parameter into X0
   BL f
   MOV X1, #1
   MOV X2, X0 // Register of function return value
   ADD X7, X1, X2

   STR X7, [FP]

   ADD SP, SP, #16

   MOV X16, #4
   SVC #0x80

   MOV     X0, #0
   MOV     X16, #1
   SVC     #0x80

