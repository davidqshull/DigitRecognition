/*
  David Shull
  CSC380: Artificial Intelligence
  Project 3: Handwritten Digit Recognition
*/

import java.util.*;
import java.io.*;

public class NeuralNetwork {

    double alpha;
    double[][] weights;
    double[] input;
    double[] output;
    int count;
    int[] actual;
    int[] expected;
    int maxIndex;
    double[] errors;
    int digit;

    public NeuralNetwork() {
        alpha = .25;
        count = 0;
        weights = new double[784][10];
        output = new double[10];
        actual = new int[10];
        expected = new int[10];
        maxIndex = 0;
        errors = new double[10];
        input = new double[784];
    }

    public void processInput(SillyImage img) {
        for(int i = 0; i < img.image.length; i++) {
            double pixelValue = (double) img.image[i] / 255;
            input[i] = pixelValue;
        }

        double maxValue = Integer.MIN_VALUE;

        for(int i = 0; i < output.length; i++) {
            double temp = 0.0;

            for(int j = 0; j < input.length; j++)
                temp += weights[j][i] * input[j];

            double sigWeight = 1 / (1 + Math.exp(-1 * temp));
            output[i] = sigWeight;
            double currentValue = output[i];
            if(currentValue > maxValue) {
                maxValue = currentValue;
                maxIndex = i;
            }
        }
        actual[maxIndex] = 1;
    }

    public void error(int expectedDigit) {
        expected[expectedDigit] = 1;
        for(int i = 0; i < output.length; i++) {
            double error = expected[i] - output[i];
            errors[i] = error;
        }
    }

    public void updateWeights(SillyImage img, int expectedDigit) {
        processInput(img);
        error(expectedDigit);
        for(int i = 0; i < output.length; i++) {
            for(int j = 0; j < input.length; j++) {
                double temp = weights[j][i];
                temp += alpha * errors[i] * output[i] * (1 - output[i]) * input[j];
                weights[j][i] = temp;
            }
        }
    }

    public void readWeights() {
        try {
            File temp = new File("weights.txt");
            Scanner sc = new Scanner(temp);
            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < input.length; j++)
                    weights[j][i] = sc.nextDouble();
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    public void writeWeights() {
        try {
            File temp = new File("weights.txt");
            FileWriter fw = new FileWriter(temp);

            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < input.length; j++) {
                    fw.write(weights[j][i] + " ");
                }
                fw.write("\n");
            }

            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.err.println("Error!");
        }
    }

    public double getAlpha() {
        return alpha;
    }

    public double getEpochs() {
        return count;
    }

    public void incrementEpochs() {
        count++;
    }

    public int getDigit() {
        return maxIndex;
    }
}
