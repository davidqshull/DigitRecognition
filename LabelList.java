/*
  David Shull
  CSC380: Artificial Intelligence
  Project 3: Handwritten Digit Recognition
*/

import java.io.*;

public class LabelList {
    public int nlabels;

    int labels [];

    LabelList(String fname) {
	// Read in a list of labels from a file.
	try {
	    FileInputStream fis = new FileInputStream(fname);
	    DataInputStream dis = new DataInputStream(fis);
	    int height = dis.readInt();
	    int width = dis.readInt();
	    nlabels = width*height;
	    if (width != 1) {
		System.err.println("Doesn't look like a list " + fname);
	    }
	    labels = new int[nlabels];
	    for (int i=0; i<width*height; i++) {
		labels[i] = dis.readInt();
	    }
	    fis.close();
	    dis.close();
	} catch(IOException e) {
	    System.err.println("Error reading file " + fname);
	}
    }


    // An ugly method for printing a list of labels.
    public void printList() {
	System.out.print("\n");
	for (int i=0; i<nlabels; i++) {
	    System.out.print(labels[i]+ " ");
	}
	System.out.print("\n\n");
    }
}
