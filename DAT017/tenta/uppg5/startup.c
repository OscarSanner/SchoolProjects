/*
 * 	startup.c
 *
 */
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

typedef struct point{
	unsigned short x;
	unsigned short y;
	int delay;
	struct point * next;
}POINT;

typedef struct robot{
	volatile unsigned char ctrl;
	volatile unsigned char status;
	unsigned short dummy;
	volatile unsigned short dataY;
	volatile unsigned short dataX;
	volatile unsigned short posY;
	volatile unsigned short posX;
}ROBOT;

void init(ROBOT * p){
	p->ctrl = 1;
	p->ctrl = 8;
	move(p,0,0);
}

void move(ROBOT * p, int x, int y){
	while((p->posX != x) && (p->posY != y));
}

void trip(ROBOT* p, POINT* pt){
	while(pt->next){
		move(p,pt->x,pt->y);
		delay(pt->delay);
		pt = pt->next;
	}
}

void main(void){
	
}

