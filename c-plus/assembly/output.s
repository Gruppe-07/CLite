.global _main
.align 4

_main: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack
   // Make room for local variables and potential parameter
   SUB FP, SP, #16
   SUB SP, SP, #16

   // Declare i
   MOV X1, #1
   MOV X2, #1
   ADD X3, X1, X2

   STR X3, [FP, #0]

   ADD SP, SP, #16
   LDP LR, FP, [SP], #16 // Restore LR, FP
   MOV X0, #0
   MOV X16, #1
   svc #0x80
.data
ptfStr: .asciz	"Value of register: %ld\n"
.align 4
.text
