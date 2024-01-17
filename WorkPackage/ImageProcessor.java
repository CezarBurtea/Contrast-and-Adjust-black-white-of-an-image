package WorkPackage;

import java.awt.image.BufferedImage;

// Interfata de baza abstracta pentru procesarea imaginilor
public interface ImageProcessor {
  void adjustLevels(BufferedImage image, int...adjustmentConstants);
}