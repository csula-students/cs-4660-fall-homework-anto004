package csula.cs4660.exercises;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Introduction Java exercise to read file
 */
public class FileRead {
    private int[][] numbers;
    /**
     * Read the file and store the content to 2d array of int
     * @param file read file
     */
    public FileRead(File file) {
        // TODO: read the file content and store content into numbers
        InputStream is = null;
        int i;
        String c;
        String[] str = null;
        int j=0;

        try{
            // new input stream created
            is = new FileInputStream(file);

            System.out.println("Characters printed:");

            // reads till the end of the stream
            while((i=is.read())!=-1)
            {
                str[j++]= String.valueOf(i);

            }
            System.out.println(str);
        }catch(Exception e){

            // if any I/O error occurs
            e.printStackTrace();
        }

    }


    /**
     * Read the file assuming following by the format of split by space and next
     * line. Display the sum for each line and tell me
     * which line has the highest mean.
     *
     * lineNumber starts with 0 (programming friendly!)
     */
    public int mean(int lineNumber) {
        return 0;
    }

    public int max(int lineNumber) {
        return 0;
    }

    public int min(int lineNumber) {
        return 0;
    }

    public int sum(int lineNumber) {
        return 0;
    }

    public static void main(String [] args) {
        File file = new File("/Users/anto004/Desktop/AI/cs4660-fall-2016/src/main/resources/array.txt");
        FileRead fr = new FileRead(file);
    }

}
