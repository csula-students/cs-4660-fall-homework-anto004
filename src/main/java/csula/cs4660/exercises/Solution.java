package csula.cs4660.exercises;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        System.err.println(size);

        String [][] tiles = new String[size][size];
        int startRow = 0;
        int startCol = 0;

        for(int i=0; i< tiles.length; i++){
            String str = in.next();
            for(int j=0; j< tiles[i].length; j++){
                String t = String.valueOf(str.charAt(j));
                tiles[i][j] = t;
                if(t.equals("m")){
                    startRow = i;
                    startCol = j;
                }
            }
        }

        PathFinding(tiles, startRow, startCol).forEach(System.out:: println);
        debugPrint(tiles);

    }

    public static List<String> PathFinding(String [][] tiles, int startRow, int startCol){
        List<String> result = new ArrayList<>();
        // Implement BFS or DFS from the start position to end position
        Queue<String> queue = new LinkedList<String>();
        if (tiles == null)
            return null;

        return result;
    }
    public void bfs(String tile){
        Queue<String> queue = new LinkedList<String>();
        if (tile == null)
            return;

    }


    private Collection<String[][]> findPossibleNextMoves(String [][] state){
        // return the possible next moves
        Collection<String[][]> result = new ArrayList<>();
        return result;
    }

    private boolean isGoal(String [][] state){
        // check the current state is goal state or not
        return false;
    }

    private static void debugPrint(String [][] state){
        for(String [] row: state){
            for(String tile: row){
                System.err.print(tile + ' ');
            }
            System.err.println();
        }
    }
}