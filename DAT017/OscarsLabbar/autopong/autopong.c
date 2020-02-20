/*
 * 	startup.c
 *
 */
 
#define 	SIMULATOR
#define		STK_BAS 		0xE000E010
#define		STK_CTRL		((volatile unsigned char *) (STK_BAS))
#define		STK_COUNTFLAG	((volatile unsigned char *) (STK_BAS + 0x2))
#define		STK_LOAD		((volatile unsigned int *) (STK_BAS + 0x4))
#define		STK_VAL			((volatile unsigned int *) (STK_BAS + 0x8))

#define PORT_BASE 0x40021000
#define portModer ((volatile unsigned long*) PORT_BASE)
#define portOtyper ((volatile unsigned short)* PORT_BASE + 4)
#define portOspeedr ((volatile unsigned long)* PORT_BASE + 8)
#define portPupdr ((volatile unsigned long)* PORT_BASE + 0xC)

#define portportIdrLow ((volatile unsigned char*) PORT_BASE + 0x10)
#define portIdrHigh ((volatile unsigned char*) PORT_BASE + 0x11)
#define portOdrLow ((volatile unsigned char*) PORT_BASE + 0x14)
#define portOdrHigh ((volatile unsigned char*) PORT_BASE + 0x15)

#define B_E 0x40
#define B_SELECT 4
#define B_RW 2
#define B_RS 1
#define B_RST 0x20
#define B_CS2 0x10
#define B_CS1 8
#define B_CS1_AND_B_CS2 0x18

#define LCD_ON 0x3F // Display on
#define LCD_OFF 0x3E // Display off
#define LCD_SET_ADD 0x40 // Set horizontal coordinate
#define LCD_SET_PAGE 0xB8 // Set vertical coordinate
#define LCD_DISP_START 0xC0 // Start address
#define LCD_BUSY 0x80 // Read busy status

#define MAX_POINTS 20

// 					STRUCTS					//
typedef unsigned char uint8_t;


typedef struct tPoint{
	uint8_t x;
	uint8_t y;
}POINT;

typedef struct tGeometry{
	int numpoints;		//Amount of points composing the object (max)
	int	sizex;			//Width
	int sizey;			//Height
	POINT px[MAX_POINTS];
} GEOMETRY, *PGEOMETRY; 

typedef struct tObj{
	PGEOMETRY geo;
	int dx, dy;
	int posx,posy;
	void (* draw)(struct tObj *);
	void (* clear)(struct tObj *);
	void (* move)(struct tObj *);
	void (* set_speed)(struct tObj *, int, int);
} OBJECT, *POBJECT;

	

__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void ){
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}


void init_app(void){
	* portModer = 0x55555555;
}
	// 					DELAYS					//
void delay_250ns(void){
	*STK_CTRL = 0;
	*STK_LOAD = 168/4 -1;
	*STK_VAL = 0;
	*STK_CTRL = 5;
	while( (*STK_COUNTFLAG & 0x01) != 0){
	}
	*STK_CTRL = 0;
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

void delay_500ns(){
	delay_250ns();
	delay_250ns();
}

//						DISPLAY						//

void graphic_ctrl_bit_set(uint8_t x){
	* portOdrLow |= (x & ~B_SELECT);
	* portOdrLow &= ~B_SELECT;
}

void graphic_ctrl_bit_clear(uint8_t x){
	* portOdrLow &= ~(x | B_SELECT);
}

void select_controller(uint8_t controller){
	uint8_t both = (int)(B_CS1 & B_CS2);
	switch(controller){
		case 0: graphic_ctrl_bit_clear(B_CS1 | B_CS2); break;
		case B_CS1: graphic_ctrl_bit_clear(B_CS2); 
					graphic_ctrl_bit_set(B_CS1); break;
		case B_CS2: graphic_ctrl_bit_clear(B_CS1); 
					graphic_ctrl_bit_set(B_CS2); break;
		case (B_CS1_AND_B_CS2): graphic_ctrl_bit_set(B_CS1 | B_CS2); break;
		default: return;
	}
}

void graphic_wait_ready(void){
	graphic_ctrl_bit_clear(B_E);
	*portModer = 0x00005555;
	graphic_ctrl_bit_set(B_RW);
	graphic_ctrl_bit_clear(B_RS);
	delay_500ns();
	while(1){
		graphic_ctrl_bit_set(B_E);
		delay_500ns();
		graphic_ctrl_bit_clear(B_E);
		delay_500ns();
		if((* portIdrHigh & LCD_BUSY) == 0){
			break;
		}
	}
	graphic_ctrl_bit_set(B_E);
	* portModer = 0x55555555;
}

uint8_t graphic_read(uint8_t controller){
	graphic_ctrl_bit_clear(B_E);
	*portModer = 0x00005555;
	graphic_ctrl_bit_set(B_RS | B_RW);
	select_controller(controller);
	delay_500ns();
	graphic_ctrl_bit_set(B_E);
	delay_500ns();
	uint8_t rv = * portIdrHigh;
	graphic_ctrl_bit_clear(B_E);
	* portModer = 0x55555555;				//VARFÖR ÄR DETTA VIKTIGT???
	if(controller == B_CS1){
		select_controller(B_CS1);
		graphic_wait_ready();
	}
	if(controller == B_CS2){
		select_controller(B_CS2);
		graphic_wait_ready();
	}
	return rv;
}

void graphic_write(uint8_t value, uint8_t controller){
	* portOdrHigh = value;
	select_controller(controller);
	delay_500ns();
	graphic_ctrl_bit_set(B_E);
	delay_500ns();
	graphic_ctrl_bit_clear(B_E);
	
	if(controller & B_CS1){
		select_controller(B_CS1);
		graphic_wait_ready();
	}
	if(controller & B_CS2){
		select_controller(B_CS2);
		graphic_wait_ready();
	}
	*portOdrHigh = 0;
	graphic_ctrl_bit_set(B_E);
	select_controller(0);
}

void graphic_write_command(uint8_t command, uint8_t controller){
	graphic_ctrl_bit_clear(B_E);
	select_controller(controller);
	graphic_ctrl_bit_clear(B_RS | B_RW);
	graphic_write(command, controller);
}

void graphic_write_data(uint8_t data, uint8_t controller){
	graphic_ctrl_bit_clear(B_E);
	select_controller(controller);
	graphic_ctrl_bit_set(B_RS);
	graphic_ctrl_bit_clear(B_RW);
	graphic_write(data, controller);
}

uint8_t graphic_read_data(uint8_t controller){
	graphic_read(controller);
	return graphic_read(controller);
}

void graphic_initialize(){
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

void graphic_clear_screen(){
	for (uint8_t page = 0; page < 8; page++){
		graphic_write_command(LCD_SET_PAGE | page, B_CS1 | B_CS2);
		graphic_write_command(LCD_SET_ADD | 0, B_CS1 | B_CS2);
		for(uint8_t add = 0; add < 64; add++){
			graphic_write_data(0, B_CS1 | B_CS2);
		}
	}
}

void pixel(uint8_t x, uint8_t y, uint8_t set){
	if((x > 128)||(y > 64)){return;}
	uint8_t mask, controller, x_real, data_holder;
	uint8_t index = (y-1) / 8;
	
	switch((y-1) % 8){
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
	if(set == 0){
		mask = ~mask;
	}
		
	if(x > 64){
		controller = B_CS2;
		x_real = x-65;
	}else{
		controller = B_CS1;
		x_real = x-1;
	}
	graphic_write_command(LCD_SET_ADD | x_real, controller);
	graphic_write_command(LCD_SET_PAGE | index, controller);
	data_holder = graphic_read_data(controller);
	graphic_write_command(LCD_SET_ADD | x_real, controller);
	if(set==1){
		mask |= data_holder;
	}else{
		mask &= data_holder;
	}
	graphic_write_data(mask, controller);
}

// 					OBJECT FUNCTIONS					//

void set_object_speed(POBJECT o, int speedx, int speedy){
	o->dx = speedx;
	o->dy = speedy;
}

void draw_object(POBJECT o){
	for(int t = 0; t < o->geo->numpoints +0; t++){
		pixel(o->geo->px[t].x + o->posx, o->geo->px[t].y + o->posy, 1);
	}
}

void clear_object(POBJECT o){
	for(int t = 0; t < o->geo->numpoints; t++){
		pixel(o->geo->px[t].x + o->posx, o->geo->px[t].y + o->posy, 0);
	}
}

void move_object(POBJECT o){
	clear_object(o);
	int newx = o->posx + o->dx;
	int newy = o->posy + o->dy;
	
	if(newx < 1){
		o->dx = -o->dx;
		o->posx = 1;
	}
	else if((newx + o->geo->sizex) > 128){
		o->dx = -o->dx;
		o->posx = 128 - o->geo->sizex;
	}
	else if(newy < 1){
		o->dy = -o->dy;
		o->posy = 1;
	}
	else if(newy > 64 - o->geo->sizey){
		o->dy = -o->dy;
		o->posy = 64 - o->geo->sizey;
	}else{
		o->posx = newx;
		o->posy = newy;
	}
	draw_object(o);
}

static GEOMETRY ball_geometry = { 12,4,4,{{0,1},{0,2},{1,0},{1,1},{1,2},{1,3},{2,0},{2,1},{2,2},{2,3},{3,1},{3,2}} };
static OBJECT ball = { &ball_geometry, 0, 0, 1, 1, draw_object, clear_object, move_object, set_object_speed};

void main(void){
	POBJECT p = &ball;
	init_app();
	graphic_initialize();
#ifndef SIMULATOR
	graphic_clear_screen();
#endif
	p->set_speed(p,4,1);
	p->posx = 25;
	p->posy = 25;
	while(1){
		p->move(p);
	}
}

