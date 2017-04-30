/*
  David Shull
  CSC380: Artificial Intelligence
  Project 3: Handwritten Digit Recognition
*/

import java.util.*;
import java.io.*;

public class Driver1 {

    static int[] labels = new LabelList("train_images/labels.bin").labels;
    static File[] imageFiles = new File("train_images").listFiles();

    static int trainingErrors, testingErrors;
    static double trainingAccuracy, testingAccuracy;

    public static void main(String[] args) {
        for(int i = 1000; i < 11000; i += 1000) {
            train(i);
            test();
        }
    }

    /**
     * Script to train the network and write weights to file
     */
    public static void train(int numImages) {
        // Create new network
        NeuralNetwork myNet = new NeuralNetwork();

        // Train network
        System.out.println("=== Training Begin ===");
        System.out.println(" - Training Samples: " + numImages);
        System.out.println(" - Learning Coefficient: " + myNet.getAlpha());
        System.out.println();

        // Keep training until network is trained enough or has passed the max epochs
        while ((trainingAccuracy < .90) && (myNet.getEpochs() < 1)) {
            trainingErrors = 0;
            myNet.incrementEpochs();
            System.out.println(" - Epoch " + myNet.getEpochs() + ":");

            // Train network
            for (int i = 0; i < numImages; i++) {
                // Provide progress bar for training

                // Get image and expected value
                SillyImage currentImage = new SillyImage("train_images/" + imageFiles[i].getName());
                int expectedDigit = labels[i];

                // Train network on data
                myNet.updateWeights(currentImage, expectedDigit);

                // Record if guess was incorrect
                if (myNet.getDigit() != expectedDigit) {
                    trainingErrors++;
                }
            }

            // Compute training accuracy for this epoch
            trainingAccuracy = 1 - (double)trainingErrors/(double)numImages;
            System.out.println("\r   Accuracy: " + (int)(100*trainingAccuracy) + "%     ");
        }

        // Write weights to file
        myNet.writeWeights();

        System.out.println("=== Training Complete ===");
    }

    /**
     * Script to test the network and read weights from file
     */
    public static void test() {
        System.out.println("=== Testing Begin ===");
        System.out.println(" - Testing Samples: " + 1000);

        NeuralNetwork myNet = new NeuralNetwork();
        myNet.readWeights();
        // Load network with weights
        // Test on last 1000 images
        testingErrors = 0;
        for (int i = 10000; i < 11000; i++) {

            // Get image and expected value
            SillyImage currentImage = new SillyImage("train_images/" + imageFiles[i].getName());
            int expectedDigit = labels[i];

            myNet.processInput(currentImage);
            // Check if output is correct
            if (myNet.getDigit() != expectedDigit) {
                testingErrors++;
            }
        }
        System.out.println("\r - Total Errors: " + testingErrors + "     ");

        testingAccuracy = 1 - (double)testingErrors/(double)1000;
        System.out.println(" - Error Rate: " + (int)(100*(1-testingAccuracy)) + "%");
        System.out.println("=== Testing Complete ===");
    }
}
