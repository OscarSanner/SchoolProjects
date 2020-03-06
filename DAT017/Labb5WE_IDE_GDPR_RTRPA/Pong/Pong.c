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
#include "copyright.xbm"


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

/*
void player_left_up(POBJECT paddle){
	int column;
	activateRowHigh(0);
	column = readColumnHigh();
	if (column == 0){
		paddle->dy = 10;
	}else{
		return 0;
	}
}
*/

void player_left_movement(POBJECT paddle){
	int column;
	activateRowHigh(1);
	column = readColumnHigh();
	if (column == 0){
		paddle->dy = 3;
		return;
	}	
	activateRowHigh(0);
	column = readColumnHigh();
	if (column == 0){
		paddle->dy = -3;
	}else{
		paddle->dy = 0;
	}
}

/*void player_right_up(POBJECT paddle){
	int column;
	activateRowHigh(2);
	column = readColumnHigh();
	if (column == 3){
		paddle->dy = 10;
	}else{
		return 0;
	}
}
 */
 
void player_right_movement(POBJECT paddle){
	int column;
	activateRowHigh(3);
	column = readColumnHigh();
	if (column == 3){
		paddle->dy = 3;
		return;
	}
	activateRowHigh(2);
	column = readColumnHigh();
	if (column == 3){
		paddle->dy = -3;
	}else{
		paddle->dy = 0;
	}
}


void disp_copyright(){
	sprite copyright;
	load_sprite(&copyright, copyright_bits, copyright_width, copyright_height);
	
	draw_sprite(&copyright, 1,1,1);
	graphic_draw_screen();
	clear_backBuffer();
	delay_milli(3000);
}

void intro_state(){
	sprite noText;
	sprite text;
	
	load_sprite(&noText, Startmenu_bits, Startmenu_width, Startmenu_height);
	load_sprite(&text, StartmenuWithText_bits, StartmenuWithText_width, StartmenuWithText_height);
	
	int keyboardHighInput;
	int keyboardLowInput;
	
	while(1){
		clear_backBuffer();
		
		draw_sprite(&noText, 0, 0, 1);
		graphic_draw_screen();
		clear_backBuffer();
		delay_milli(1000);
			
		keyboardHighInput = keybHigh();
		
		if(keyboardHighInput != 255){
			break;
		}
						
		draw_sprite(&text, 0, 0, 1);
		graphic_draw_screen();
		delay_milli(1000);
		
		keyboardHighInput = keybHigh();
		
		if(keyboardHighInput != 255){
			break;
		}
	}
}

void win_state(){
	sprite pRightWin;
	sprite pRightWinText;
	sprite pLeftWin;
	sprite pLeftWinText;
	
	load_sprite(&pRightWin, PlayerRightWins_bits, PlayerRightWins_width, PlayerRightWins_height);
	load_sprite(&pRightWinText, PlayerRightWinsWithText_bits, PlayerRightWinsWithText_width, PlayerRightWinsWithText_height);
	load_sprite(&pLeftWin, PlayerLeftWins_bits, PlayerLeftWins_width, PlayerLeftWins_height);
	load_sprite(&pLeftWinText, PlayerLeftWinsWithText_bits, PlayerLeftWinsWithText_width, PlayerLeftWinsWithText_height);
	
	int keyboardHighInput;
	int keyboardLowInput;
		
	while(1){
		clear_backBuffer();

		if(player_left_points == 5){
			draw_sprite(&pLeftWin, 0, 0, 1);
			graphic_draw_screen();
			clear_backBuffer();
			delay_milli(1000);
			
			keyboardHighInput = keybHigh();
		
			if(keyboardHighInput != 255){
				break;
			}
			
			draw_sprite(&pLeftWinText, 0, 0, 1);
			graphic_draw_screen();
			delay_milli(1000);
		}
	
		if(player_right_points == 5){
			draw_sprite(&pRightWin, 0, 0, 1);
			graphic_draw_screen();
			clear_backBuffer();
			delay_milli(1000);
			
			keyboardHighInput = keybHigh();
		
			if(keyboardHighInput != 255){
				break;
			}
			
			draw_sprite(&pRightWinText, 0, 0, 1);
			graphic_draw_screen();
			delay_milli(1000);
		}
		
		keyboardHighInput = keybHigh();
		
		if(keyboardHighInput != 255){
			break;
		}
	}
}

int boolean_input(){
	uint8_t keyboardHighInput = keybHigh();
	uint8_t keyboardLowInput = keybLow();
		
	if((keyboardHighInput != -1) || (keyboardLowInput != -1)){
		return 1;
	}
	return 0;
}


#ifdef OLD_PIXEL
void main(void){
	POBJECT b = &ball;
	POBJECT p_left = &paddle_left;
	POBJECT p_right = &paddle_right;
	
	init_app();
	ascii_playerscore_init();
	graphic_initialize();
	
	b->dx = 3;
	b->dy = 3;
	
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
	POBJECT p_left = &paddle_left;
	POBJECT p_right = &paddle_right;

	init_app();
	graphic_initialize();
	ascii_playerscore_init();
	

	b->dx = 5;
	b->dy = 5;

#ifndef SIMULATOR
	graphic_clear_screen();
#endif
	disp_copyright();
	intro_state();
	
	while(1){
		
		clear_backBuffer();
		
		b->move(b, b);
		p_left->move(p_left, b);
		p_right->move(p_right, b);
		
		/*
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
		*/
		
		player_left_movement(p_left);
		player_right_movement(p_right);
		
		graphic_draw_screen();
		delay_milli(40);
		
		uint8_t leftPoints = player_left_points;
		uint8_t rightPoints = player_right_points;
		
		if(leftPoints == 5 || rightPoints == 5){
			win_state();
			player_left_points = 0;
			player_right_points = 0;
		}
	}
}

#endif

