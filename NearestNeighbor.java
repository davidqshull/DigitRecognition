/*
  David Shull
  CSC380: Artificial Intelligence
  Project 3: Handwritten Digit Recognition
*/

import java.util.*;
import java.io.*;

public class NearestNeighbor {

    static int[] labels = new LabelList("train_images/labels.bin").labels;
    static File[] imageFiles = new File("train_images").listFiles();
    int imageCount = 11000;
    int pixelCount = 784;
    double[] distances;
    int[] neighbors;
    double[][] images;

    public void processInput(int checkCount) {
        images = new double[imageCount][pixelCount];
        for(int i = 0; i < 11000; i++) {
            if(i % 1000 == 0 && i > 0)
                System.out.println("\t" + i + " images processed");
            if(checkCount != 10000 && i == checkCount) {
                i = 10000;
                continue;
            }
            SillyImage currentImage = new SillyImage("train_images/" + imageFiles[i].getName());
            for(int j = 0; j < currentImage.image.length; j++) {
                double pixelValue = (double) currentImage.image[j] / 255;
                images[i][j] = pixelValue;
            }
        }
        System.out.println("Image processing complete");
    }

    public void distance(double[] pixels) {
        distances = new double[images.length - 1000];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < pixels.length; j++) {
                double difference = Math.abs(images[i][j] - pixels[j]);
                distances[i] += (difference * difference);
            }
        }
    }

    public void neighbors() {
        neighbors = new int[3];
        double[] minValues = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < distances.length; j++) {
                double distance = distances[j];
                if (i == 0 && distance < minValues[i]) {
                    minValues[i] = distance;
                    neighbors[i] = labels[j];
                }
                else if (distance < minValues[i] && distance > minValues[i-1]){
                    minValues[i] = distance;
                    neighbors[i] = labels[j];
                }
            }
        }
    }

    public int bestNeighbor() {
        if (neighbors[1] == neighbors[2] && neighbors[1] != neighbors[0])
            return neighbors[1];
        else
            return neighbors[0];
    }

    public boolean check(int digit, int index) {
        if (labels[index] == digit)
            return true;
        return false;
    }
}
