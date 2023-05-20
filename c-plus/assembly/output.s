.global _start
.align 2

_start:
   B main

f: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack
   SUB FP, SP, #16
   SUB SP, SP, #16

   // Load return value into X0 register
   MOV X0, #1
   LDP LR, FP, [SP], #16 // Restore LR, FP
   RET

main:
   // Declare i
   BL f
   MOV X1, X0 // Register of function return value
   SUB FP, SP, #16
   SUB SP, SP, #16
   STR X1, [FP]

   MOV X16, #4
   SVC #0x80

   MOV     X0, #0
   MOV     X16, #1
   SVC     #0x80

