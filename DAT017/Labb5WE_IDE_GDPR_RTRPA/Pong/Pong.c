/*
 * 	Pong.c
 *
 */

#include "Definitions.h"
#include "Structures.h"
#include "Delays.h"
#include "GraphicDrivers.h"
#include "KeybDrivers.h"
#include "GameLogic.h"


__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void ){
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}	

static GEOMETRY paddle_geometry = {28, 2, 14, {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},{0,9},{0,10},{0,11},{0,12},{0,13},{1,0},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},{1,8},{1,9},{1,10},{1,11},{1,12},{1,13}}	};
static OBJECT paddle_left = {&paddle_geometry, 0, 0, PADDLE_DISTANCE_FROM_GOAL, (32-7), draw_object, clear_object, move_paddle, set_object_speed};
static OBJECT paddle_right = {&paddle_geometry, 0, 0, (128 - PADDLE_DISTANCE_FROM_GOAL), (32-7), draw_object, clear_object, move_paddle, set_object_speed};

static GEOMETRY ball_geometry = { 12,4,4,{{0,1},{0,2},{1,0},{1,1},{1,2},{1,3},{2,0},{2,1},{2,2},{2,3},{3,1},{3,2}} };
static OBJECT ball = { &ball_geometry, 0, 0, 64, 32, draw_object, clear_object, move_ball, set_object_speed};

void init_app(void){
#ifdef USBDM
	* ((unsigned long *) 0x40023830) = 0x18;
	__asm__ volatile(" LDR R0, =0x08000209\n");
	__asm__ volatile(" BLX R0 \n");
#endif
	
	* portModer = 0x55555555;
	* GPIOD_MODER = 0x55005500;
	* GPIOD_PUPDR = 0x00AA00AA;
	* GPIOD_OTYPER = 0x0;	
}

#ifndef TEST
void main(void){
	POBJECT b = &ball;
	POBJECT b2 = &ball;
	POBJECT p_left = &paddle_left;
	POBJECT p_right = &paddle_right;
	init_app();
	graphic_initialize();
	b->dx = 1;
	b->dy = 1;
	p_left->dy = 1;
	p_right->dy = 1;
	

#ifndef SIMULATOR
	graphic_clear_screen();
#endif
	while(1){
		b->move(b, 0);
		p_left->move(p_left, b);
		p_right->move(p_right, b);
		delay_milli(40);
		uint8_t c = keybHigh();
		switch(c){
			case 6: b->set_speed(b,2,0);break;
			case 4: b->set_speed(b,-2,0);break;
			case 2: b->set_speed(b,0,-2);break;
			case 8: b->set_speed(b,0,2);break;
		}
	}
}

#endif

#ifdef TEST
void main(){
	delay_250ns();
}
#endif

