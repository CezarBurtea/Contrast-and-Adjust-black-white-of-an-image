package WorkPackage;

import java.awt.image.BufferedImage;

public class ProducerThread extends Thread {
    private BufferedImage inputImage;
    private Buffer buffer;
    private int numSegments;

    public ProducerThread(BufferedImage inputImage, Buffer buffer, int numSegments) {
        this.inputImage = inputImage;
        this.buffer = buffer;
        this.numSegments = numSegments;
    }

    @Override
    public void run() {
        try {
        	
        	// Extrag dimensiunile imaginii
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            int segmentHeight = height / numSegments;

            for (int i = 0; i < numSegments; i++) {
                int startY = i * segmentHeight;

                System.out.println("Segmentheight = " + startY);

                BufferedImage segment = inputImage.getSubimage(0, startY, width, segmentHeight);

                synchronized (buffer) {
                    buffer.put(segment);
                    System.out.println("Segmentul " + i + " a fost introdus in buffer");
                    buffer.notify();
                }

                Thread.sleep(1000);
            }

            // Notificarea finală pentru a asigura că producerul a terminat
            synchronized (buffer) {
                buffer.put(null); // Semnalizează consumatorului că s-a terminat
                buffer.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}