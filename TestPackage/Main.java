package TestPackage;

import WorkPackage.LevelAdjuster;
import WorkPackage.ProducerThread;
import WorkPackage.WriterResult;
import WorkPackage.ConsumerThread;
import WorkPackage.Buffer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Main{
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);

      System.out.print("Introduceți calea către fișierul de intrare BMP: ");
      String inputFilePath = scanner.nextLine();

      System.out.print("Introduceți calea către fișierul de ieșire BMP: ");
      String outputFilePath = scanner.nextLine();

      // Citeste imaginea BMP de la calea specificată de la tastatură
      BufferedImage inputImage = readBMPImage(inputFilePath);

      // Am creat o instanță pentru Buffer
      Buffer buffer = new Buffer(4);

      LevelAdjuster processor = new LevelAdjuster();

      // Am creat o instanță pentru ProducerThread și una pentru ConsumerThread,
      // ambele partajând aceeași instanță de Buffer
      ProducerThread producerThread = new ProducerThread(inputImage, buffer, 4); // Numărul de segmente
      //            ConsumerThread consumerThread = new ConsumerThread(buffer, processor, inputImage);

      PipedOutputStream pipedOutputStream = new PipedOutputStream();
      PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

      ConsumerThread consumerThread = new ConsumerThread(buffer, processor, pipedOutputStream);
      Thread consumerThreadInstance = new Thread(consumerThread);
      WriterResult writerResult = new WriterResult(pipedInputStream, outputFilePath, inputImage);

      // Pornirea thread-ului producător
      producerThread.start();

      // Așteptarea terminării thread-ului producător
      producerThread.join();

   // Pornirea thread-ului consumator
      consumerThreadInstance.start();

      // Pornirea thread-ului scriitor
      writerResult.start();

      // Așteptarea terminării thread-ului consumator și scriitor
      consumerThreadInstance.join();
      writerResult.join();


      System.out.println("Imaginea a fost modificată și salvată cu succes.");
      saveBMPImage(inputImage, outputFilePath);

      scanner.close();

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  // Metoda pentru a citi o imagine BMP dintr-un fișier
  private static BufferedImage readBMPImage(String filePath) throws IOException {
    return ImageIO.read(new File(filePath));
  }

  // Metoda pentru a salva o imagine BMP într-un fișier
  private static void saveBMPImage(BufferedImage image, String filePath) throws IOException {
    ImageIO.write(image, "bmp", new File(filePath));
  }

}