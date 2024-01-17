package WorkPackage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Buffer {
  private List < BufferedImage > segments;
  private int currentSegmentIndex;

  public Buffer(int numSegments) {
    this.segments = new ArrayList < > (numSegments);
    this.currentSegmentIndex = 0;
  }

  public synchronized BufferedImage get() throws InterruptedException {
    while (segments.isEmpty() && currentSegmentIndex < segments.size()) {
      wait();
    }
    return segments.isEmpty() ? null : segments.get(currentSegmentIndex);
  }

  public synchronized void put(BufferedImage image) {
    segments.add(image);
    notifyAll();
  }

  public int getCurrentSegmentIndex() {
    return currentSegmentIndex;
  }

  public void moveToNextSegment() {
    currentSegmentIndex++;
  }

  public int getNumSegments() {
    return segments.size();
  }
}