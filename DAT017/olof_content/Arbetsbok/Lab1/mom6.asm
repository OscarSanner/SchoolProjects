@ mom6.asm
start:
@ initiera port D som utport
	LDR	R6,=0x55555555
	LDR	R5,=0x40020C00
	STR	R6,[R5]
@ adressen till port D:s utdataregister till R5
	LDR	R5,=0x40020C14
@ adressen till port E:s indataregister till R6
	LDR	R6,=0x40021010
main:
	LDRB	R0,[R6]		@ läs PE0-E7
	LDRB	R1,[R6,#1]	@ läs PE8-E15
@	BIC	R0,R0,R1	@ BYT MELLAN OLIKA OPERATORER
	MVN	R0,R0
	STRB	R0,[R5]
	B	main
