

void activateRowHigh(int row){
	switch(row){
		case 0: * GPIOD_ODR_HIGH = 0x10; break;
		case 1: * GPIOD_ODR_HIGH = 0x20; break;
		case 2: * GPIOD_ODR_HIGH = 0x40; break;
		case 3: * GPIOD_ODR_HIGH = 0x80; break;
		default: * GPIOD_ODR_HIGH = 0x0;
	}
}

void activateRowLow(int row){
	switch(row){
		case 0: * GPIOD_ODR_LOW = 0x10; break;
		case 1: * GPIOD_ODR_LOW = 0x20; break;
		case 2: * GPIOD_ODR_LOW = 0x40; break;
		case 3: * GPIOD_ODR_LOW = 0x80; break;
		default: * GPIOD_ODR_LOW = 0x0;
	}
}

int readColumnHigh(){
	unsigned char c = * GPIOD_IDR_HIGH;
	if(c & 0x8){return 3;}
	if(c & 0x4){return 2;}
	if(c & 0x2){return 1;}
	if(c & 0x1){return 0;}
	return 4;
}

int readColumnLow(){
	unsigned char c = * GPIOD_IDR_LOW;
	if(c & 0x8){return 3;}
	if(c & 0x4){return 2;}
	if(c & 0x2){return 1;}
	if(c & 0x1){return 0;}
	return 4;
}

int getKeyValue(int row, int column){
	unsigned char keys[4][4] = { {1, 2, 3, 0xA}, {4, 5, 6, 0xB}, {7, 8, 9, 0xC}, {0xF, 0, 0xE, 0xD} };
	return keys[row][column];
}


unsigned char keybHigh(){
	char column;
	char key;
	for(int row = 0; row < 4; row++){
		activateRowHigh(row);
		column = readColumnHigh();
		
		if (column != 4){
			key = getKeyValue(row, column);
			return key;
		}
	}
	return -1;
}

unsigned char keybLow(){
	char column;
	char key;
	for(int row = 0; row < 4; row++){
		activateRowLow(row);
		column = readColumnLow();
		
		if (column != 4){
			key = getKeyValue(row, column);
			return key;
		}
	}
	return -1;
}