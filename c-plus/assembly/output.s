.global _start
.align 2
   .equ VAR, 0
   .equ RETURN, 8

_start:
   B main

f: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack
   // Make room for local variables and potential parameter
   SUB FP, SP, #16
   SUB SP, SP, #16

   STR X0, [FP, #-0] // Push parameter to stack
   // Declare b
   MOV X1, #1
   STR X1, [FP, #-8]

   LDR X1, [FP, #-8] // Load b
   LDR X5, [FP, #-8] // Load b
   MOV X2, X5
   MOV X3, #2
   ADD X4, X2, X3

   LDR X2, [FP, #-8] // Load b
   MOV X0, X2// Load return value into X0 register
   ADD SP, SP, #16
   LDP LR, FP, [SP], #16 // Restore LR, FP
   RET

main:
   // Make room for local variables and potential parameter
   SUB FP, SP, #0
   SUB SP, SP, #0

   ADD SP, SP, #0

   MOV X16, #4
   SVC #0x80

   MOV     X0, #0
   MOV     X16, #1
   SVC     #0x80

