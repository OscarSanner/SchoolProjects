@
@ binTo7Seg
@

init:
@ initiera port D som utport
	LDR	R6,=0x55555555
	LDR	R5,=0x40020C00
	STR	R6,[R5]
@ adressen till port D:s ut-dataregister till R5

	LDR	R5,=0x40020C14
@ adressen till port E:s in-dataregister till R6
	LDR	R6,=0x40021010
@ adressen till SegCodes till R4
	LDR	R4,=SegCodes

main:	
	LDR	R0,[R6]
	CMP	R0,#16
	BCC	L1
	MOV	R0,#0
	B	L2
	ADD	R0,R0,R4
L1:	LDRB	R0,[R4,R0]
L2:	STRB	R0,[R5]
	B	main

		.ALIGN
SegCodes:	.BYTE	0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x38,0x3E,0x79,0x71
		.ALIGN
