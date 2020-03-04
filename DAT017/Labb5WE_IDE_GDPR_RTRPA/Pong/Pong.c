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
#include "AsciiDrivers.h"

#include "PlayerLeftWins.xbm"
#include "PlayerLeftWinsWithText.xbm"
#include "PlayerRightWins.xbm"
#include "PlayerRightWinsWithText.xbm"
#include "StartmenuWithText.xbm"
#include "Startmenu.xbm"


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

void win_state(){
	clear_backBuffer();
	while(1){
			if(player_left_points == 5){
			for (int i = 0; i < 1024; i++ ){
					backBuffer[i] |= PlayerLeftWinsWithText_bits[i];
			}
			graphic_draw_screen();
			clear_backBuffer();
			delay_mikro(10);
			for (int i = 0; i < 1024; i++ ){
					backBuffer[i] |= PlayerLeftWins_bits[i];
			}
			graphic_draw_screen();
			clear_backBuffer();
		}else{
			for (int i = 0; i < 1024; i++ ){
					backBuffer[i] |= PlayerRightWinsWithText_bits[i];
			}
			graphic_draw_screen();
			clear_backBuffer();
			delay_mikro(10);
			for (int i = 0; i < 1024; i++ ){
					backBuffer[i] |= PlayerRightWins_bits[i];
			}
			graphic_draw_screen();
			clear_backBuffer();
		}
		signed char check1 = keybHigh();
		signed char check2 = keybLow();
		
		if ( ( check1 != -1) || ( check2 != -1) ){
			break;
		} else {continue;}
	}
}



#ifdef OLD_PIXEL
void main(void){
	POBJECT b = &ball;
	POBJECT b2 = &ball;
	POBJECT p_left = &paddle_left;
	POBJECT p_right = &paddle_right;
	init_app();
	ascii_playerscore_init();
	graphic_initialize();
	b->dx = 15;
	b->dy = 15;
	//p_left->dy = 1;
	//p_right->dy = 1;
	

#ifndef SIMULATOR
	graphic_clear_screen();
#endif
	while(1){
		b->move(b, b);
		p_left->move(p_left, b);
		p_right->move(p_right, b);
		delay_milli(40);
		char p_l_key = keybHigh();
		char p_r_key = keybLow();
    	switch(p_l_key){																				//PD8-15
			case 2: p_left->set_speed(p_left,0,-20);break;
			case 5: p_left->set_speed(p_left,0,20);break;
			default: p_left->set_speed(p_left,0,0);break;
		}
		switch(p_r_key){																				//PD0-7
			case 2: p_right->set_speed(p_right,0,-20);break;
			case 5: p_right->set_speed(p_right,0,20);break;
			default: p_right->set_speed(p_right,0,0);break;
		}
	}
}

#endif

#ifndef OLD_PIXEL
void main(void){
	POBJECT b = &ball;
	POBJECT b2 = &ball;
	POBJECT p_left = &paddle_left;
	POBJECT p_right = &paddle_right;
	init_app();
	ascii_playerscore_init();
	graphic_initialize();
	b->dx = 15;
	b->dy = 15;
	//p_left->dy = 1;
	//p_right->dy = 1;
	

#ifndef SIMULATOR
	graphic_clear_screen();
#endif
	while(1){
		clear_backBuffer();
		b->move(b, b);
		p_left->move(p_left, b);
		p_right->move(p_right, b);
		char p_l_key = keybHigh();
		char p_r_key = keybLow();
    	switch(p_l_key){																				//PD8-15
			case 2: p_left->set_speed(p_left,0,-20);break;
			case 5: p_left->set_speed(p_left,0,20);break;
			default: p_left->set_speed(p_left,0,0);break;
		}
		switch(p_r_key){																				//PD0-7
			case 2: p_right->set_speed(p_right,0,-20);break;
			case 5: p_right->set_speed(p_right,0,20);break;
			default: p_right->set_speed(p_right,0,0);break;
		}
		graphic_draw_screen();
		delay_milli(40);
		if(player_left_points == 5 || player_right_points == 5){
			win_state();
			player_left_points = 0;
			player_right_points = 0;
		}
	}
}

#endif

