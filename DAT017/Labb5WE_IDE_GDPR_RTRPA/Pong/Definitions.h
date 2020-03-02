#define 	SIMULATOR
//#define 		USBDM
//#define		TEST

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

#define portportIdrLow ((volatile unsigned char*) PORT_BASE + 0x10)
#define portIdrHigh ((volatile unsigned char*) PORT_BASE + 0x11)
#define portOdrLow ((volatile unsigned char*) PORT_BASE + 0x14)
#define portOdrHigh ((volatile unsigned char*) PORT_BASE + 0x15)

// 					DEFINES FOR KEYPAD					//

#define GPIOD_BASE 0x40020C00
#define GPIOD_MODER ((volatile unsigned long*) GPIOD_BASE)
#define GPIOD_PUPDR ((volatile unsigned long*) GPIOD_BASE + 0x0C)
#define GPIOD_OTYPER ((volatile unsigned short*) GPIOD_BASE + 4)


#define KeypadIn ((volatile unsigned char*) GPIOD_BASE + 0x11)
#define KeypadOut ((volatile unsigned char*) GPIOD_BASE + 0x15)

#define GPIOD_IDR_HIGH ((volatile unsigned char*) GPIOD_BASE + 0x11)
#define GPIOD_ODR_HIGH ((volatile unsigned char*) GPIOD_BASE + 0x15)
#define GPIOD_IDR_LOW ((volatile unsigned char*) GPIOD_BASE + 0x10)
#define GPIOD_ODR_LOW ((volatile unsigned char*) GPIOD_BASE + 0x14)



#define MAX_POINTS 28 // Raised from 20
#define PADDLE_DISTANCE_FROM_GOAL 50

typedef unsigned char uint8_t;
