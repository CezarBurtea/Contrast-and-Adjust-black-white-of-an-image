package WorkPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;

//Nivelul 2: Clasa intermediara care implementeaza functionalitate comuna
abstract class BaseImageProcessor implements ImageProcessor {

  protected int avg;

  private int redShift;

  // Bloc de initializare (non-static) - pentru fiecare instanță a clasei
  {
    // Inițializarea pragului pentru calcularea bitii-lor ai canalului rosu
    redShift = 16;
  }

  private static int greenShift;

  // Bloc static de initializare
  static {
    // Inițializarea unui prag global comun pentru calcularea bitii-lor ai canalului verde    
    greenShift = 8;
  }

  protected int createGrayPixel(int avg) {

    return (avg << redShift) | (avg << greenShift) | avg;

    // (avg << 16) înseamnă deplasarea valorii avg la stânga cu 16 biți, 
    // astfel încât să ocupe primeii 8 biți ai canalului roșu (Red) din valoarea RGB.

    // (avg << 8) înseamnă deplasarea valorii avg la stânga cu 8 biți,
    // astfel încât să ocupe următorii 8 biți ai canalului verde (Green) din valoarea RGB.

    // avg rămâne nemodificată și ocupă ultimii 8 biți 
    // ai canalului albastru (Blue) din valoarea RGB.
  }

  // Metoda care folosește varargs pentru a accepta un număr variabil de constante de ajustare
  public void adjustLevels(BufferedImage image, int...adjustmentConstants) {

    int width = image.getWidth();
    int height = image.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Obține culoarea pixelului la coordonatele (x, y)
        Color color = new Color(image.getRGB(x, y));

        // Calculează valoarea medie a canalelor de culoare
        int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        if (adjustmentConstants.length > 0) {
          // Aplică ajustările în funcție de constantele furnizate
          for (int constant: adjustmentConstants) {
            avg += constant;
          }
        }

        // Aplică ajustări pentru alb-negru
        int newColor = createGrayPixel(avg);

        // Setează noul pixel în imagine
        image.setRGB(x, y, newColor);
      }
    }

  }

}