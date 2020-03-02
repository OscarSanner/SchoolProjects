unsigned char keybHigh(){
	char column;
	char key;
	for(int row = 0; row < 4; row++){
		activateRowHigh(row);
		column = readColumn();
		
		if (column != 4){
			key = getKeyValue(row, column);
			return key;
		}
	}
	return -1;
}

void activateRowHigh(int row){
	switch(row){
		case 0: * KeypadOut = 0x10; break;
		case 1: * KeypadOut = 0x20; break;
		case 2: * KeypadOut = 0x40; break;
		case 3: * KeypadOut = 0x80; break;
		default: * KeypadOut = 0x0;
	}
}

void activateRowLow(int row){
	switch(row){
		case 0: * KeypadOut = 0x1; break;
		case 1: * KeypadOut = 0x2; break;
		case 2: * KeypadOut = 0x4; break;
		case 3: * KeypadOut = 0x8; break;
		default: * KeypadOut = 0x0;
	}
}

int getKeyValue(int row, int column){
	unsigned char keys[4][4] = { {1, 2, 3, 0xA}, {4, 5, 6, 0xB}, {7, 8, 9, 0xC}, {0xF, 0, 0xE, 0xD} };
	return keys[row][column];
}

int readColumn(){
	unsigned char c = * KeypadIn;
	if(c & 0x8){return 3;}
	if(c & 0x4){return 2;}
	if(c & 0x2){return 1;}
	if(c & 0x1){return 0;}
	return 4;
}