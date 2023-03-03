#include <FastLED.h>

#define NUM_LEDS 64
CRGB leds[NUM_LEDS];
void setup() {

  FastLED.addLeds<NEOPIXEL, 6>(leds, NUM_LEDS);
  FastLED.setBrightness(20);

}

constexpr unsigned int getIndex(unsigned int col, unsigned int row)
{
  return ((row & 1 == 1) ? 7 - col : col) + row * 8;
}

void clear()
{
  for (int i = 0; i < NUM_LEDS; i++)
  {
    leds[i] = CRGB::Black;
  }
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
  FastLED.show();
}

void loop() {
  clear();
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
  FastLED.show();

  delay(2000);

  clear();
  showCube(CRGB::Red);
  delay(2000);

  clear();
  showCube(CRGB::Blue);
  delay(2000);
}
