/*
  David Shull
  CSC380: Artificial Intelligence
  Project 3: Handwritten Digit Recognition
*/

import java.util.*;
import java.io.*;

public class Driver2 {

    public static void main(String[] args) {

        System.out.println("Testing images...");
        for(int j = 1000; j < 11000; j+=1000) {
            System.out.println("Processing images...");
            NearestNeighbor n = new NearestNeighbor();
            int decision = 0;
            int correctCount = 0 ;
            n.processInput(j);

            for (int i = 10000; i < 11000; i++) {
                if(i % 100 == 0 && i > 10000)
                    System.out.println("\t" + (i - 10000) + " images tested");
                double[] pixels = n.images[i];
                n.distance(pixels);
                n.neighbors();

                if (n.check(n.bestNeighbor(), i))
                    correctCount++;
            }
            System.out.println("Image testing complete");
            System.out.println("Accuracy: " + (double) correctCount/1000 * 100 + "%\n");
        }
    }
}
