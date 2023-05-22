.global _main
.align 4

_main: STP LR, FP, [SP, #-16]! //Push LR, FP onto stack
   // Make room for local variables and potential parameter
   SUB FP, SP, #0
   SUB SP, SP, #0

   MOV X1, #1
   MOV X2, #1
   CMP X1, X2 // X1 > X2 
   B.LT elseclause
   // Declare a
   MOV X0, #1 // Load parameter into X0
   // Setup
   STP X29, LR, [SP, #-16]!     ; Save LR, FR
   ADRP X0, ptfStr@PAGE
   ADD X0, X0, ptfStr@PAGEOFF
   MOV X10, #1
   STR X10, [SP, #-32]!
   BL _printf
   MOV X1, #1 // Dummy return value from printf
   STR X1, [FP, #-0]

   B endif
elseclause: 
   // Declare a
   MOV X0, #1 // Load parameter into X0
   // Setup
   STP X29, LR, [SP, #-16]!     ; Save LR, FR
   ADRP X0, ptfStr@PAGE
   ADD X0, X0, ptfStr@PAGEOFF
   MOV X10, #1
   STR X10, [SP, #-32]!
   BL _printf
   MOV X1, #1 // Dummy return value from printf
   STR X1, [FP, #-8]

endif:
   ADD SP, SP, #0
   LDP LR, FP, [SP], #16 // Restore LR, FP
   MOV X0, #0
   MOV X16, #1
   svc #0x80
.data
ptfStr: .asciz	"Value of register: %ld\n"
.align 4
.text
