start:	LDR	R0,=0x55555555	@ initiera port D som utport
	LDR	R1,=0x40020C00
	STR	R0,[R1]
	LDR	R5,=0x40020C14	@ adressen till port D:s ut-dataregister till R5
	LDR	R6,=0x40021010	@ adressen till port E:s in-dataregister till R6
main:	LDRB	R0,[R6]		@ Ladda in data från FÖRSTA dipswitch
	SXTB	R0,R0		@ Teckenutvidga data för att kunna jämföra negativa tal
	LDRB	R1,[R6,#1]	@ Ladda in data från ANDRA dipswitch
	SXTB	R1,R1		@ Teckenutvidga data för att kunna jämföra negativa tal
	CMP	R0,R1		@ Sätt flaggor genom att jämföra talen
	BGT	main_1		@ Brancha om R0>R1 (branch greater than)
	MOV	R0,#0
	B	main_2
main_1:	MOV	R0,#0xFF
main_2:	STRB	R0,[R5]
	B	main
