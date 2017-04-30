/*
  David Shull
  CSC380: Artificial Intelligence
  Project 3: Handwritten Digit Recognition
*/

import java.io.*;

public class SillyImage {
    public int height,width;

    int image [];

    SillyImage(String fname) {
	// Read in a new image from a file.
        try {
            FileInputStream fis = new FileInputStream(fname);
            DataInputStream dis = new DataInputStream(fis);
            height = dis.readInt();
            width = dis.readInt();
            image = new int[width*height];
            for (int i=0; i<width*height; i++) {
                image[i] = dis.readInt();
            }
            fis.close();
            dis.close();
        }
        catch(IOException e) {
            System.err.println("Error reading file " + fname);
        }
    }


    // An ugly method for printing an image.
    public void printImage() {
	       System.out.print("\n");
           for (int i=0; i<width*height; i++) {
               if (i % width==0) System.out.print("\n");
               System.out.print(image[i]+ " ");
           }
           System.out.print("\n\n");
    }
}
