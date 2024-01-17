package WorkPackage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.File;

import javax.imageio.ImageIO;

public class WriterResult extends Thread {
  private PipedInputStream pipedInputStream;
  private String outputFilePath;
  private BufferedImage inputImage;
  BufferedImage result;

  public WriterResult(PipedInputStream pipedInputStream, String outputFilePath, BufferedImage inputImage) {
    this.pipedInputStream = pipedInputStream;
    this.outputFilePath = outputFilePath;
    this.inputImage = inputImage;
  }

  @Override
  public void run() {

    try {

      for (int i = 0; i < 4; i++) {

        BufferedImage segment = ImageIO.read(pipedInputStream);
        System.out.println("A fost citit segmentul" + i + " din Pipe");
        BufferedImage result = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), segment.getType());

        for (int y = 0; y < segment.getHeight(); y++) {
          for (int x = 0; x < segment.getWidth(); x++) {
            int rgb = segment.getRGB(x, y);
            if (x < result.getWidth() && (i * segment.getHeight() + y) < result.getHeight())
              result.setRGB(x, i * segment.getHeight() + y, rgb); // introduc noii pixeli in variabila result
          }
        }

        System.out.println("A fost scris segmentul in rezultat");
      }

      // Închid PipedInputStream după ce s-au citit toate segmentele
      pipedInputStream.close();

      saveBMPImage(result, outputFilePath);

      System.out.println("Rezultatul final a fost salvat în fișier: " + outputFilePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void saveBMPImage(BufferedImage image, String filePath) throws IOException {
    if (image != null) ImageIO.write(image, "bmp", new File(filePath));
  }
}