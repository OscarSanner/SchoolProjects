@
@	LDR	R1,=c
@	LDR	R2,=s
@	LDR	R3,=0
@	LDRSB	R0,[R1,R3] @NOTE
@	STRH	R0,[R2]
@
@	.ALIGN
@s:	.SPACE	2	@ assume signed
@c:	.SPACE	1	@ assume signed


	LDR	R1,=c
	LDR	R2,=s
	LDR	R3,=0
	LDRB	R0,[R1,R3] @NOTE
	STRH	R0,[R2]

	.ALIGN
s:	.SPACE	2	@ assume un-signed
c:	.SPACE	1	@ assume un-signed

