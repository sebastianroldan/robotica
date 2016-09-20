
#include "Wire.h"
#include "I2Cdev.h"
#include "HMC5883L.h"

HMC5883L mag;

int16_t mx, my, mz;

void setup() {

    Wire.begin();
    Serial.begin(9600);
    mag.initialize();
}

void loop() 
{
    mag.getHeading(&mx, &my, &mz);
    float heading = atan2(my, mx);
    if(heading < 0)
      heading += 2 * M_PI;
    float angulo = heading * 180/M_PI;
    Serial.println(angulo);
}
