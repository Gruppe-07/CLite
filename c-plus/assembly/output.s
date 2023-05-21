.global _main
.align 4

f: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack
   // Make room for local variables and potential parameter
   SUB FP, SP, #16
   SUB SP, SP, #16

   STR X0, [FP, #-0] // Push parameter to stack
   LDR X4, [FP, #-0] // Load a
   MOV X1, #1
   MOV X2, X4
   ADD X3, X1, X2

   MOV X0, X3// Load return value into X0 register
   ADD SP, SP, #16
   LDP LR, FP, [SP], #16 // Restore LR, FP
   RET
_main:
   // Make room for local variables and potential parameter
   SUB FP, SP, #16
   SUB SP, SP, #16

   // Declare a
   MOV X0, #1 // Load parameter into X0
   BL f
   MOV X1, X0 // X0 = Register of function return value
   STR X1, [FP, #-0]

   // Declare i
   LDR X5, [FP, #-0] // Load a
   MOV X1, X5
   MOV X2, #1
   ADD X4, X1, X2

   MOV X0, X4 // Load parameter into X0
   // Setup
   STP X29, LR, [SP, #-16]!
   ADRP X0, ptfStr@PAGE
   ADD X0, X0, ptfStr@PAGEOFF
   MOV X10, X4
   STR X10, [SP, #-32]!
   BL _printf
   MOV X1, #1 // Dummy return value from printf
   STR X1, [FP, #-8]

   MOV X0, #0
   MOV X16, #1
   SVC #0x80

   ADD SP, SP, #16
   LDP LR, FP, [SP], #16 // Restore LR, FP
   RET
.data
ptfStr: .asciz	"Value of register: %ld\n"
.align 4
.text
