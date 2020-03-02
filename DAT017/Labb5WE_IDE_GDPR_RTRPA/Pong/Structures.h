typedef struct tPoint{
	uint8_t x;
	uint8_t y;
}POINT;

typedef struct tGeometry{
	int numpoints;		//Amount of points composing the object (max)
	int sizex;			//Width
	int sizey;			//Height
	POINT px[MAX_POINTS];
} GEOMETRY, *PGEOMETRY; 

typedef struct tObj{
	PGEOMETRY geo;
	int dx, dy;
	int posx,posy;
	void (* draw)(struct tObj *);
	void (* clear)(struct tObj *);
	void (* move)(struct tObj *, struct tObj *);
	void (* set_speed)(struct tObj *, int, int);
} OBJECT, *POBJECT;
