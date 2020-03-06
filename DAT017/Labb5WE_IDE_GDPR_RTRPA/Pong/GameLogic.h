static uint8_t player_right_points = 0;
static uint8_t player_left_points = 0; 

void set_object_speed(POBJECT o, int speedx, int speedy){
	o->dx = speedx;
	o->dy = speedy;
}

void move_ball(POBJECT o, POBJECT dummy){
#ifdef OLD_PIXEL
	clear_object(o);
#endif
	int newx = o->posx + o->dx;
	int newy = o->posy + o->dy;
	
	if(newx < 1){
		o->posx = 64;
		o->posy = 32;
		player_right_points++;
		ascii_gotoxy(15,2);
		ascii_write_char(player_right_points + 48);
	}
	else if((newx + o->geo->sizex) > 128){
		o->posx = 64;
		o->posy = 32;
		player_left_points++;
		ascii_gotoxy(14,1);
		ascii_write_char(player_left_points + 48);
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

uint8_t collision_with_ball(POBJECT o, int newY, POBJECT ball){
	
	if (  ((ball->posx + ball->geo->sizex) >= o->posx &&
		  ball->posx < (o->posx + o->geo->sizex)) && 
		  ((ball->posy + ball->geo->sizey) >= newY &&
		   ball->posy < (newY + o->geo->sizey))   ) {
			clear_object(ball);
			   return 1;
		   }
	return 0;
}

void move_paddle(POBJECT o, POBJECT ball){
#ifdef OLD_PIXEL
	clear_object(o);
#endif
	
	int newy = o->posy + o->dy;
	
	if(collision_with_ball(o, newy, ball)){
		if(o->posx < 64){
			ball->dx = -(ball->dx);
			ball->posx = PADDLE_DISTANCE_FROM_GOAL + 2;
		}else{
			ball->dx = (ball->dx)*(-1);
			ball->posx = 128 - (PADDLE_DISTANCE_FROM_GOAL + 4);
		}	
		draw_object(ball);
	}

	else if(newy < 1){
		o->dy = -o->dy;
		o->posy = 1;
	}
	else if(newy > 64 - o->geo->sizey){
		o->dy = -o->dy;
		o->posy = 64 - o->geo->sizey;
	}else{
		o->posy = newy;
	}
	draw_object(o);
}
