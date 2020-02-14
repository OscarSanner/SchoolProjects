/*
 * 	startup.c
 *
 */
 #define GPIO_D 0x40020C00																		// Pekare till D-Porten (börjar på MODER registret).
 #define GPIO_D_MODER ((unsigned long *) 0x40020C00)						// Pekare till D-Portens MODER register, används för att bestämma vilka portpinnar som ska vara in och ut (se quick-guide sida 20).
 #define GPIO_D_OTYPER ((unsigned short *) 0x40020C04)						// Pekare till D-Portens Output-TYPE register, används för konfigurera portpinnar enligt push-pull/open-drain, OBS: endast 16-BITAR (se quick-guide sida 20 och arbetsbok sida 86).
 #define GPIO_D_PUPDR ((unsigned long *) 0x40020C0C)						// Pekare till D-Portens Pull-Up/Pull-Down register, används för bestämma vilka portpinnar som ska vara floating/pull-up/-pull-down (se quick-guide sida 20 och arbetsbok sida 86).
 #define GPIO_IDR_HIGH ((unsigned char *) 0x40020C11)							// Pekare till D-Portens IN-Data register, HÖGA BYTEN, används som inport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).
 #define GPIO_IDR_LOW ((unsigned char *) 0x40020C10)							// Pekare till D-Portens IN-Data register, LÅGA BYTEN, används som inport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).
 #define GPIO_ODR_HIGH ((unsigned char *) 0x40020C15)						// Pekare till D-Portens UT-Data register, HÖGA BYTEN, används som utport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).
 #define GPIO_ODR_LOW ((unsigned char *) 0x40020C14)						// Pekare till D-Portens IN-Data register, LÅGA BYTEN, används som inport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).

 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

// -----------------------------------------------------------------------------

void init_GPIO_D_keypad_HIGH (void) {				// Initierar Keypaden på höga bytes
	* GPIO_D_MODER &= 0x0000FFFF;				// Nollställer de 2 höga bytesen i MODER-registret och har samtidigt kvar eventuella tidigare inskrivningar i de låga.
	* GPIO_D_MODER |= 0x55000000;					// 0101 0101 den högsta byten vilket gör porten till en utport och 0000 0000 den näst högsta porten vilket gör den till en inport. Har samtidigt kvar eventuella bitar på de låga bytesen.
	
	* GPIO_D_OTYPER &= 0x00FF;						// Nollställer den höga byten i OTYPER-registret och har samtidigt kvar eventuella tidigare inskrivningar i de låga.
	* GPIO_D_OTYPER |= 0x0000;							// 0000 0000 på den  höga byten i registrert vilket sätter dessa portpinnar till PUSH-PULL. Har samtidigt kvar eventuella bitar på de låga bytesen.		(onödig kanske?)
	
	* GPIO_D_PUPDR &= 0x0000FFFF;					// Nollställer de 2 höga bytesen i PUPDR-registret och har samtidigt kvar eventuella tidigare inskrivningar i de låga.
	* GPIO_D_PUPDR |= 0x00AA0000;					// 0000 0000 den högsta byten vilket konfigurerar motsvarande 4 port-pinnar till FLOATING och 0101 0101 den näst högsta porten konfigurerar motsvarande 4 port-pinnar till PULL-DOWN. Har samtidigt kvar eventuella bitar på de låga bytesen.
}

void init_GPIO_D_7SegmentDisplay_LOW (void) {
	* GPIO_D_MODER &= 0xFFFF0000;				// Nollställer de 2 lägsta bytesen i MODER-registret och har samtidigt kvar eventuella tidigare inskrivningar i de höga. 
	* GPIO_D_MODER |= 0x00005555;					// 0101 0101 de två lägsta byten vilket gör porten till en utport. Har samtidigt kvar eventuella bitar på de låga bytesen.
}

void app_init(void) {										// Kallar på de två ovantsående init funktionerna.
	init_GPIO_D_keypad_HIGH();
	init_GPIO_D_7SegmentDisplay_LOW();
}

// -------------------------------------------------------------------------

void keybActivate (unsigned int row) {
	// Kollar vilken rad som knappen är itryckt på och sparar detta i D-portens HÖGA BYTE (inget kopplat hit).				VARFÖR SPARAR MAN DET HÄR????
	switch(row) {
		case 1: * GPIO_ODR_HIGH = 0x10; break;
		case 2: * GPIO_ODR_HIGH = 0x20; break;
		case 3: * GPIO_ODR_HIGH = 0x40; break;
		case 4: * GPIO_ODR_HIGH = 0x80; break;
		default: * GPIO_ODR_HIGH = 0;
	}
}

int keybGetCol (void) {
	/* Om någon tangent (i aktiverad rad)
	 * är nedtryckt, returnera dess kolumnnummer,
	 * annars, returnera 0 */
	unsigned char rowFromRegister;
	rowFromRegister = * GPIO_IDR_HIGH;
	if (rowFromRegister & 8) return 4;
	if (rowFromRegister & 4) return 3;
	if (rowFromRegister & 2) return 2;
	if (rowFromRegister & 1) return 1;
	return 0;
}

void out7seg (unsigned char c) {
	/* Får in ett tal och sparar detta i D-portens LÅGA BYTE (7seg-display kopplad hit).
	 * Är talet 1,...,15 ?  ---> aktivera motsvarande bitar på 7segdisplay för att visa HEX talet.
	 * Om inte  ---> stäng av 7segdisplay*/
	char display_7seg_hex_to_decimal_array[] ={0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};			// Array med hex-motsvarigheter för nummer på 7segdisplay.
	if (c < 16) {
		* GPIO_ODR_LOW = display_7seg_hex_to_decimal_array[c];
	} else {
		* GPIO_ODR_LOW = 0x0;
	}
}

unsigned char keyb (void) {
	unsigned char key[4][4] = { {1, 2, 3, 10},			// Matris som representerar alla knappar på 16-tangenbord ( OBS i decimalt för att kunna använda som index i hexArrayen i metoden ovan.
											{4, 5, 6, 11},
											{7, 8, 9, 12},
											{14, 0, 15, 13} };
	unsigned char col = 0;
	for (unsigned char row = 1; row <= 4; row++) {		// Loopar igenom alla rader
		keybActivate(row);													// Om en knapp på raden är ifylld, spara dess RAD (se metoden ovan).
		col = keybGetCol();													// Om en knapp på raden (infon hämtas från HÖGA BYTEN i ODR) är ifylld, spara dess KOLUMN (se metoden ovan). 
		if ( col != 0 ) {															// Om kolumnen inte är 0, dvs en rad har hämtats från HÖGA BYTEN i ODR och kolumnen för knappen på raden har sparats....
			return key[row-1][col-1];										// retunera då knappens värde i matrisen, alltså det nummer som står på knappen.
		}
	}
	keybActivate(0);
	return 0xFF;							// Kommer att stänga av displayen då ( FF > 15 )
}


void main(void) {
	app_init();
	while(1) {
		out7seg(keyb());
	}
}



