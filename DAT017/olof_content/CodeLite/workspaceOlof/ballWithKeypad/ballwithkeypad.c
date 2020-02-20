/*
 * 	startup.c
 *
 */




#define SIMULATOR




#define	STK_BAS 0xE000E010
#define	STK_CTRL ((volatile unsigned char *) (STK_BAS))
#define	STK_COUNTFLAG ((volatile unsigned char *) (STK_BAS + 0x2))
#define	STK_LOAD ((volatile unsigned int *) (STK_BAS + 0x4))
#define	STK_VAL ((volatile unsigned int *) (STK_BAS + 0x8))

#define	PORT_BASE 0x40021000
#define	portModer ((volatile unsigned long *) PORT_BASE)
#define	portOtyper ((volatile unsigned short *) PORT_BASE + 4)
#define	portOspeedr ((volatile unsigned long *) PORT_BASE + 8)
#define	portPupdr ((volatile unsigned long *) PORT_BASE + 0xC)

#define	portIdrLow ((volatile unsigned char *) PORT_BASE + 0x10)
#define	portIdrHigh ((volatile unsigned char *) PORT_BASE + 0x11)
#define	portOdrLow ((volatile unsigned char *) PORT_BASE + 0x14)
#define	portOdrHigh ((volatile unsigned char *) PORT_BASE + 0x15)

#define B_E 0x40
#define B_SELECT 4
#define B_RW 2
#define B_RS 1
 
 #define B_RST 0x20
 #define B_CS2 0x10
 #define B_CS1 0x8
 #define B_CS1_AND_B_CS2 0x18
 #define LCD_BUSY 
 
 #define LCD_ON 0x3F // Display on
 #define LCD_OFF 0x3E // Display off
 #define LCD_SET_ADD 0x40 // Set horizontal coordinate
 #define LCD_SET_PAGE 0xB8 // Set vertical coordinate
 #define LCD_DISP_START 0xC0 // Start address
 #define LCD_BUSY 0x80 // Read busy status

#define MAX_POINTS 20





 #define GPIO_D 0x40020C00																		// Pekare till D-Porten (börjar på MODER registret).
 #define GPIO_D_MODER ((volatile unsigned long *) 0x40020C00)						// Pekare till D-Portens MODER register, används för att bestämma vilka portpinnar som ska vara in och ut (se quick-guide sida 20).
 #define GPIO_D_OTYPER ((volatile unsigned short *) 0x40020C04)						// Pekare till D-Portens Output-TYPE register, används för konfigurera portpinnar enligt push-pull/open-drain, OBS: endast 16-BITAR (se quick-guide sida 20 och arbetsbok sida 86).
 #define GPIO_D_PUPDR ((volatile unsigned long *) 0x40020C0C)						// Pekare till D-Portens Pull-Up/Pull-Down register, används för bestämma vilka portpinnar som ska vara floating/pull-up/-pull-down (se quick-guide sida 20 och arbetsbok sida 86).
 #define GPIO_IDR_HIGH ((volatile unsigned char *) 0x40020C11)							// Pekare till D-Portens IN-Data register, HÖGA BYTEN, används som inport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).
 #define GPIO_IDR_LOW ((volatile unsigned char *) 0x40020C10)							// Pekare till D-Portens IN-Data register, LÅGA BYTEN, används som inport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).
 #define GPIO_ODR_HIGH ((volatile unsigned char *) 0x40020C15)						// Pekare till D-Portens UT-Data register, HÖGA BYTEN, används som utport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).
 #define GPIO_ODR_LOW ((volatile unsigned char *) 0x40020C14)						// Pekare till D-Portens IN-Data register, LÅGA BYTEN, används som inport för externa prylar, OBS: endast 8-BITAR (se quick-guide sida 20 och arbetsbok sida 85).


 
  // --------------------------- TYPEDEF --------------------------------
 
 typedef unsigned char uint_8t;
 
 typedef  struct tPoint {
	 uint_8t x;
	 uint_8t y;
 } POINT;
 
 typedef struct tGeometry {
	 int numpoints;						//  hur många punkter av max_points som används för att bygga objektet
	 int sizex;									//  hur brett objektet är
	 int sizey;									//  hur högt objektet är
	 POINT px[MAX_POINTS];
 } GEOMETRY, *PGEOMETRY;
 
 typedef struct tObj {
	 PGEOMETRY geo;
	 int dx, dy;
	 int posx, posy;
	 void (* draw) (struct tObj *);
	 void (* clear) (struct tObj *);
	 void (* move) (struct tObj *);
	 void (* set_speed) (struct tObj *, int, int);
 } OBJECT, *POBJECT;

  // --------------------------- STARTUP --------------------------------
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void ) {
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void init_app(void){
	* portModer = 0x55555555;
	* GPIO_D_MODER &= 0x0000FFFF;				// Nollställer de 2 höga bytesen i MODER-registret och har samtidigt kvar eventuella tidigare inskrivningar i de låga.
	* GPIO_D_MODER |= 0x55000000;					// 0101 0101 den högsta byten vilket gör porten till en utport och 0000 0000 den näst högsta porten vilket gör den till en inport. Har samtidigt kvar eventuella bitar på de låga bytesen.
	
	* GPIO_D_OTYPER &= 0x00FF;						// Nollställer den höga byten i OTYPER-registret och har samtidigt kvar eventuella tidigare inskrivningar i de låga.
	* GPIO_D_OTYPER |= 0x0000;							// 0000 0000 på den  höga byten i registrert vilket sätter dessa portpinnar till PUSH-PULL. Har samtidigt kvar eventuella bitar på de låga bytesen.		(onödig kanske?)
	
	* GPIO_D_PUPDR &= 0x0000FFFF;					// Nollställer de 2 höga bytesen i PUPDR-registret och har samtidigt kvar eventuella tidigare inskrivningar i de låga.
	* GPIO_D_PUPDR |= 0x00AA0000;					// 0000 0000 den högsta byten vilket konfigurerar motsvarande 4 port-pinnar till FLOATING och 0101 0101 den näst högsta porten konfigurerar motsvarande 4 port-pinnar till PULL-DOWN. Har samtidigt kvar eventuella bitar på de låga bytesen.
	
	#ifdef USBDM
		* ((unsigned long *) 0x40023830) = 0x18;
		__asm__ volatile(" LDR R0, =0x08000209\n");
		__asm__ volatile(" BLX R0 \n");
	#endif
}

 // --------------------------- DELAY --------------------------------

void delay_250ns(void){
	*STK_CTRL = 0;
	*STK_LOAD = 168/4 -1;
	*STK_VAL = 0;
	*STK_CTRL = 5;
	while( (*STK_COUNTFLAG & 0x1) != 0){
	}
	*STK_CTRL = 0;
}

void delay_500ns(void){
	delay_250ns();
	delay_250ns();
	}

void delay_mikro(unsigned int us){	
	#ifdef SIMULATOR 
		us = us/1000;
		us ++;
	#endif
	for(int i = 0; i < 4*us; i++){
		delay_250ns();
	}
}

void delay_milli(unsigned int ms){
	#ifdef SIMULATOR
		ms = ms/1000;
		ms ++;
	#endif
	for(int i = 0; i < ms; i++){
		delay_mikro(1000);
	}
}

// ------------------------------ DISPLAY ----------------------------

void graphic_ctrl_bit_set (uint_8t x) {
	* portOdrLow |= ( x & ~B_SELECT );
	* portOdrLow &= ~B_SELECT;
	}
	
void graphic_ctrl_bit_clear (uint_8t x) {
	* portOdrLow &= ~( x | B_SELECT );
			//	* portOdrLow &= ~x;
			//	* portOdrLow &= ~B_SELECT;
	}

void select_controller (uint_8t controller ) {
	switch (controller) {
		case 0:  graphic_ctrl_bit_clear( B_CS1 | B_CS2 ); break;
		case B_CS1: graphic_ctrl_bit_clear( B_CS2 ); 
								graphic_ctrl_bit_set( B_CS1 ); break;
		case B_CS2: graphic_ctrl_bit_clear( B_CS1 ); 
								graphic_ctrl_bit_set( B_CS2 ); break;
		case B_CS1_AND_B_CS2: graphic_ctrl_bit_set( B_CS1 | B_CS2); break;
		default: return 0;
	}
}

void graphic_wait_ready (void) {
	graphic_ctrl_bit_clear(B_E);
	* portModer = 0x00005555;
	graphic_ctrl_bit_set(B_RW);
	graphic_ctrl_bit_clear(B_RS);
	delay_500ns();
	while (1) {
		graphic_ctrl_bit_set(B_E);
		delay_500ns();
		graphic_ctrl_bit_clear(B_E);
		delay_500ns();
		if ( (* portIdrHigh & LCD_BUSY) == 0 ) {
			break;
		}
	}
	graphic_ctrl_bit_set(B_E);
	* portModer = 0x55555555;
}

uint_8t graphic_read (uint_8t controller) {
	graphic_ctrl_bit_clear (B_E);
	* portModer = 0x00005555;
	graphic_ctrl_bit_set (B_RS | B_RW);
	select_controller (controller);
	delay_500ns();
	graphic_ctrl_bit_set(B_E);
	delay_500ns();
	uint_8t returnValue = * portIdrHigh;
	graphic_ctrl_bit_clear(B_E);
	* portModer = 0x55555555;
	if (controller == B_CS1) {
		select_controller(B_CS1);
		graphic_wait_ready();
	}
	if (controller == B_CS2) {
		select_controller(B_CS2);
		graphic_wait_ready();
	}
	return returnValue;
}

void graphic_write (uint_8t value, uint_8t controller) {
	* portOdrHigh = value;
	select_controller(controller);
	delay_500ns();
	graphic_ctrl_bit_set(B_E);
	delay_500ns();
	graphic_ctrl_bit_clear(B_E);
	if (controller & B_CS1) {
		select_controller(B_CS1);
		graphic_wait_ready();
	}
	if (controller & B_CS2) {
		select_controller(B_CS2);
		graphic_wait_ready();
	}
	* portOdrHigh = 0;
	graphic_ctrl_bit_set(B_E);
	select_controller(0);
}

void graphic_write_command (uint_8t command, uint_8t controller) {
	graphic_ctrl_bit_clear(B_E);
	select_controller(controller);
	graphic_ctrl_bit_clear(B_RS | B_RW);
	graphic_write(command, controller);
	}
	
void graphic_write_data (uint_8t data, uint_8t controller) {
	graphic_ctrl_bit_clear(B_E);
	select_controller(controller);
	graphic_ctrl_bit_set(B_RS);
	graphic_ctrl_bit_clear(B_RW);
	graphic_write(data, controller);
	}
	
uint_8t graphic_read_data (uint_8t controller) {
	graphic_read(controller);
	return graphic_read(controller);
	}

void graphic_initalize (void) {
	graphic_ctrl_bit_set(B_E);
	delay_mikro(10);
	graphic_ctrl_bit_clear(B_CS1 | B_CS2 | B_RST | B_E);
	delay_milli(30);
	graphic_ctrl_bit_set(B_RST);
	graphic_write_command(LCD_OFF, B_CS1 | B_CS2);
	graphic_write_command(LCD_ON, B_CS1 | B_CS2);
	graphic_write_command(LCD_DISP_START, B_CS1 | B_CS2);
	graphic_write_command(LCD_SET_ADD, B_CS1 | B_CS2);
	graphic_write_command(LCD_SET_PAGE, B_CS1 | B_CS2);
	select_controller(0);
}

void graphic_clear_screen (void) {
	for (uint_8t page = 0; page < 8; page++) {
		graphic_write_command (LCD_SET_PAGE | page, B_CS1 | B_CS2);
		graphic_write_command (LCD_SET_ADD | 0, B_CS1 | B_CS2);
		for (uint_8t address = 0; address < 64; address++) {
			graphic_write_data(0, B_CS1 | B_CS2);
		}
	}
}

void pixel (uint_8t x, uint_8t y, uint_8t set) {
	if ((x > 128) || (y > 64)) {return;}
	uint_8t mask, controller, x_real, data_holder;
	uint_8t index = (y - 1) / 8;

	switch ((y-1) % 8) {
		case 0: mask = 1; break;
		case 1: mask = 2; break;
		case 2: mask = 4; break;
		case 3: mask = 8; break;
		case 4: mask = 0x10; break;
		case 5: mask = 0x20; break;
		case 6: mask = 0x40; break;
		case 7: mask = 0x80; break;
		default: break;
	}
	if (set == 0) {
		mask = ~mask;
	}
	
	if (x > 64) {
		controller = B_CS2;
		x_real = x - 65;
	} else {
		controller = B_CS1;
		x_real = x - 1;
	}
	graphic_write_command(LCD_SET_ADD | x_real, controller);
	graphic_write_command(LCD_SET_PAGE | index, controller);
	data_holder = graphic_read_data(controller);
	graphic_write_command(LCD_SET_ADD | x_real, controller);
	if (set == 1) {
		mask |= data_holder;
	} else {
		mask &= data_holder;
	}
	graphic_write_data(mask, controller);
}

// ------------------------------ OBJECT_FUNCTIONS ----------------------------

void set_object_speed (POBJECT o, int speedx, int speedy) {
	o->dx = speedx;
	o->dy = speedy;	
}

void draw_object (POBJECT o) {
	for (int t = 0; t < o->geo->numpoints; t++) {
		pixel((o->geo->px[t].x + o->posx), (o->geo->px[t].y + o->posy), 1);
	}
}

void clear_object (POBJECT o) {
	for (int t = 0; t < o->geo->numpoints; t++) {
		pixel((o->geo->px[t].x + o->posx), (o->geo->px[t].y + o->posy), 0);
	}
}

void move_object (POBJECT o) {
	clear_object(o);
	int newx = o->posx + o->dx;
	int newy = o->posy + o->dy;
	
	if (newx < 1) {
		o->dx =(o->dx)*(-1);
		o->posx = 1;
	}
	else if ((newx + o->geo->sizex)  > 128) {
		o->dx = (o->dx)*(-1);
		o->posx = 128 - o->geo->sizex;
	} else {
		o->posx = newx;
	}
	if (newy < 1) {
		o->dy = (o->dy)*(-1);
		o->posy = 1;
	}
	else if ((newy + o->geo->sizey) > 64) {
		o->dy = (o->dy)*(-1);
		o->posy = 64 - o->geo->sizey;
	} else {
		o->posy = newy;
	}

	draw_object(o);
}

// ------------------------------ KEYPAD ----------------------------

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

// ------------------------------ MAIN ----------------------------


static GEOMETRY ball_geometry = {12, 4, 4, {{0,1}, {0,2}, {1,0}, {1,1}, {1,2}, {1,3}, {2,0}, {2,1}, {2,2}, {2,3}, {3,1}, {3,2} }};

static OBJECT ball = {&ball_geometry, 0,0, 0,0, draw_object, clear_object, move_object, set_object_speed};


void main(void) {
	POBJECT p = &ball;
	init_app();
	graphic_initalize();
	
	#ifndef SIMULATOR
		graphic_clear_screen();
	#endif
	
	while(1) {
		p->move(p);
		delay_milli(40);
		uint_8t c = keyb();
		switch (c) {
			case 6: p->set_speed (p, 2, 0); break;
			case 4: p->set_speed (p, -2, 0); break;
			case 2: p->set_speed (p, 2, -2); break;
			case 8: p->set_speed (p, 2, 2); break;
		}
	}
}