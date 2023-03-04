#include <FastLED.h>

#define NUM_LEDS 64
CRGB leds[NUM_LEDS];

volatile char mode = 'b';

void setup() {
  Serial.begin(9600);

  pinMode(MISO, OUTPUT);

  FastLED.addLeds<NEOPIXEL, 6>(leds, NUM_LEDS);
  FastLED.setBrightness(255);
  
  // limit my draw to 1A at 5v of power draw
  FastLED.setMaxPowerInVoltsAndMilliamps(5, 500); 
  
  // turn on SPI in slave mode
  SPCR |= _BV(SPE);
  
  // turn on interrupts
  SPCR |= _BV(SPIE);

}

// SPI interrupt routine
ISR (SPI_STC_vect)
{
  mode = SPDR;
  Serial.println(mode);
}

constexpr unsigned int getIndex(unsigned int col, unsigned int row)
{
  return ((row & 1 == 1) ? 7 - col : col) + row * 8;
}

void setAll(CRGB color)
{
  for (int i = 0; i < NUM_LEDS; i++)
  {
    leds[i] = color;
  }
}

void clear()
{
  setAll(CRGB::Black);
}

void showCube(CRGB color)
{
  for (int i = 0; i <= 7; i++)
  {
    leds[getIndex(i, 0)] = color;
    leds[getIndex(i, 7)] = color;
    leds[getIndex(0, i)] = color;
    leds[getIndex(7, i)] = color;
  }
}

void showRainbow()
{
  fill_rainbow(leds, NUM_LEDS, 0, NUM_LEDS/8);
}

void showCone()
{
  leds[getIndex(3, 0)] = CRGB::Yellow;
  leds[getIndex(4, 0)] = CRGB::Yellow;
  
  leds[getIndex(3, 1)] = CRGB::Yellow;
  leds[getIndex(4, 1)] = CRGB::Yellow;

  leds[getIndex(3, 2)] = CRGB::Yellow;
  leds[getIndex(4, 2)] = CRGB::Yellow;
  
  leds[getIndex(2, 3)] = CRGB::Yellow;
  leds[getIndex(3, 3)] = CRGB::Yellow;
  leds[getIndex(4, 3)] = CRGB::Yellow;
  leds[getIndex(5, 3)] = CRGB::Yellow;
  
  leds[getIndex(2, 4)] = CRGB::Yellow;
  leds[getIndex(3, 4)] = CRGB::Yellow;
  leds[getIndex(4, 4)] = CRGB::Yellow;
  leds[getIndex(5, 4)] = CRGB::Yellow;
  
  leds[getIndex(2, 5)] = CRGB::Yellow;
  leds[getIndex(3, 5)] = CRGB::Yellow;
  leds[getIndex(4, 5)] = CRGB::Yellow;
  leds[getIndex(5, 5)] = CRGB::Yellow;
  
  leds[getIndex(2, 6)] = CRGB::Yellow;
  leds[getIndex(3, 6)] = CRGB::Yellow;
  leds[getIndex(4, 6)] = CRGB::Yellow;
  leds[getIndex(5, 6)] = CRGB::Yellow;
  
  for (int i = 0; i <= 7; i++)
  {
    leds[getIndex(i, 7)] = CRGB::Yellow;
  }
}

void loop() {
  static unsigned long lastFrameMillis = millis();

  if ('c' == mode)
  {
    // Cycle
    static int i = 0;

    if (millis() - lastFrameMillis > 2000) {
      lastFrameMillis = millis();
      i++;
      if (i > 2) {
        i = 0;
      }
    }
    
    clear();
    if (i == 0)
    {
      showCone();
    } else if (i == 1)
    {
      showCube(CRGB::Red);
    } else if (i == 2)
    {
      showCube(CRGB::Blue);
    }
    FastLED.show();
  } else if ('R' == mode) {
    // Red Cube
    clear();
    showCube(CRGB::Red);
    FastLED.show();
  } else if ('B' == mode) {
    // Blue Cube
    clear();
    showCube(CRGB::Blue);
    FastLED.show();
  } else if ('C' == mode) {
    // Cone
    clear();
    showCone();
    FastLED.show();
  } else if ('G' == mode) {
    // Green
    setAll(CRGB::Green);
    FastLED.show();
  } else if ('b' == mode) {
    clear();
    FastLED.show();
  } else if ('r' == mode) {
    showRainbow();
    FastLED.show();
  }

  if (Serial.available())
  {
    char c;
    c = Serial.read();
    if (c != '\n' && c != '\r')
    {
      mode = c;
      Serial.println(mode);
    }
  }
}
