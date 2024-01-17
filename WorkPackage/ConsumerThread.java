package WorkPackage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PipedOutputStream;

import javax.imageio.ImageIO;

public class ConsumerThread extends LevelAdjuster implements Runnable {
  private Buffer buffer;
  private LevelAdjuster levelAdjuster;
  private PipedOutputStream pipedOutputStream;

  public ConsumerThread(Buffer buffer, LevelAdjuster levelAdjuster, PipedOutputStream pipedOutputStream) {
    this.buffer = buffer;
    this.levelAdjuster = levelAdjuster;
    this.pipedOutputStream = pipedOutputStream;
  }

  @Override
  public void run() {
    int i = 0;

    try {
      BufferedImage segment;
      while ((segment = buffer.get()) != null && i < 4) {
        levelAdjuster.adjustLevels(segment,50);
        System.out.println("Segmentul " + i + " a fost scos din buffer");

        ImageIO.write(segment, "bmp", pipedOutputStream);
        System.out.println("Segmentul " + i + " a fost scris in pipe");

        buffer.moveToNextSegment(); // Mută la următorul segment

        Thread.sleep(1000);
        
        i++;
      }
    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    } finally {
      try {
        pipedOutputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}