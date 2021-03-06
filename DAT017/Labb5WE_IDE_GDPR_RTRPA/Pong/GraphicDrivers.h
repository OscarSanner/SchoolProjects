static uint8_t backBuffer[1024]; // 128 * 64 / 8


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
		uint8_t c = * portIdrHigh & LCD_BUSY;
		graphic_ctrl_bit_clear(B_E);
		delay_500ns();
		if(c == 0){
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

#ifdef OLD_PIXEL
void pixel(uint8_t x, uint8_t y, uint8_t set){
	if((x > 128)||(y > 64)||(x < 1)||(y < 1)){return;}
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
#endif

#ifndef OLD_PIXEL
void pixel(int x, int y, int set) {
	uint8_t mask;
	int index = 0;
	if( (x > 128 ) || (x < 1) || (y > 64) || (y < 1) ) return;
		mask = 1 << ((y-1)%8);
	if(x >= 64) {
		x -= 64;
		index = 512;
	}
	index += x + ((y-1)/8)*64;
	backBuffer[index] |= mask;
}

void graphic_draw_screen(void) {
	uint8_t i, j, controller, c;
	unsigned int k = 0;
	for(c = 0; c < 2; c++) {
		controller = (c == 0) ? B_CS1 : B_CS2;
		for(j = 0; j < 8; j++) {
			graphic_write_command(LCD_SET_PAGE | j, controller);
			graphic_write_command(LCD_SET_ADD | 0, controller);
			for(i = 0; i <= 63; i++, k++) {
				graphic_write_data(backBuffer[k], controller);
			}
		}
	}
}

#endif


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

void clear_backBuffer() {
int i;
for (i = 0; i < 1024; i++)
backBuffer[i] = 0;
}

typedef struct{
	unsigned char width;
	unsigned char height;
	unsigned char* data;
} sprite;

static void load_sprite(sprite* s, unsigned char* data, int width, int height){
	s->width = width;
	s->height = height;
	s->data = data;
}

void draw_sprite(sprite* s, int x, int y, int set) {
	int i,j,k, width_in_bytes;
	if (s->width % 8 == 0)
		width_in_bytes = s->width / 8;
	else
		width_in_bytes = s->width / 8 + 1;
	for (i = 0; i < s->height; i++){
		for (j = 0; j < width_in_bytes; j++) {
			unsigned char byte = s->data[i * width_in_bytes + j];
			for (k =0; k < 8; k++) {
				if (byte & (1 << k))
					pixel(8 * j + k + x + 1, i + y + 1, set);
			}
		}
	}
}