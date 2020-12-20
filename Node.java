import java.lang.StringBuilder;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;

public class Node implements Comparable<Node>{
    
    //Input char needs to be lowercase
    private char data;
    private int depth;
    public Node parent;
    public ArrayList<Node> Children;
    private float heuristic;
    private ArrayList<Character> remainingLetters;
    
    public Node(char data, Node parent, ArrayList<Character> remainingLetters){
        this.data = data;
        this.parent = parent;
        if (this.parent == null){
            this.depth = 0;
        }
        else{
            this.depth = parent.getDepth() + 1;
        }
        this.Children = null;
        this.heuristic = findHeuristic();
        this.remainingLetters = remainingLetters;
    }
    
    //Evaluating heuristic
    
    public float findHeuristic(){
        //Want heuristic to go from big to small as you get closer to goal
        if (isValid() == false){
            //Farthest from goal
            return 1;
        }
        
        else if (depth == 1){
            return 1-getFrequency();
        }
        else if (depth == 2 || depth == 3){
            return 1-getFrequency(parent);
        }
        else if (depth == 4 || depth == 7){
            //Independent probability => P(A^B) = P(A)*P(B)
            return 1-getFrequency()*getFrequency(parent.parent.parent);
        }
        else if (depth == 5 || depth == 6 || depth == 8){
            return 1-getFrequency(parent)*getFrequency(parent.parent.parent);
        }
        else if (depth == 9){
            if (isValid()){
                //At goal so heuristic = 0
                return 0;
            }
            else{
                return 1;
            }
        }
        return 1;
    }
    
    private float getFrequency(Node a){
        
        //Get character number in alphabet (i.e. 'a' => 1)
        int aValue = Character.getNumericValue(a.data) - 9;
        int bValue = Character.getNumericValue(data) - 9;
        
        int lineNumber = 24 + 24*aValue + bValue;
        
        Scanner sc;
        try{
            File frequencies = new File("Frequencies.txt");
            sc = new Scanner(frequencies);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
            return 0;
        }
        
        for(int i=0; i<lineNumber-1; i++){
            if (sc.hasNextLine()){
                sc.nextLine();
            }
        }
        
        return sc.nextFloat();
        
    }
    
    private float getFrequency(){
        
        int lineNumber = Character.getNumericValue(data) - 9;
        
        Scanner sc;
        try{
            File frequencies = new File("Frequencies.txt");
            sc = new Scanner(frequencies);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
            return 0;
        }
        
        for(int i=0; i<lineNumber-1; i++){
            if (sc.hasNextLine()){
                sc.nextLine();
            }
        }
        
        return sc.nextFloat();
        
    }
    
    //Expanding a node
    
    public ArrayList<Node> setSuccessors(){
        if (depth == 9){
            return null;
        }
        
        Node nextNode;
        ArrayList<Character> newLetters;
        for (int i=0; i<remainingLetters.size(); i++){
            newLetters = remainingLetters;
            newLetters.remove(i);
            nextNode = new Node(remainingLetters.get(i), this, newLetters);
            Children.add(nextNode);
        }
        return Children;
    }
    
    //Checking validity of a node
    
    private boolean isValid(){
        if (depth == 3){
            return checkWord(parent.parent, parent);
        }
        else if (depth == 6){
            return checkWord(parent.parent, parent);
        }
        else if (depth == 7){
            return checkWord(parent.parent.parent.parent.parent.parent, parent.parent.parent);
        }
        else if (depth == 8){
            return checkWord(parent.parent.parent.parent.parent.parent, parent.parent.parent);
        }
        else if (depth == 9){
            if (checkWord(parent.parent, parent)){
                if (checkWord(parent.parent.parent.parent.parent.parent, parent.parent.parent)){
                    if (checkWord(parent.parent.parent.parent.parent.parent.parent.parent, parent.parent.parent.parent)){
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            return true;
        }
    }
    
    private boolean checkWord(Node first, Node second){
        
        StringBuilder sb = new StringBuilder();
        sb.append(first.data);
        sb.append(second.data);
        sb.append(data);
        
        String word = sb.toString();
        
        //Check if word is in words document
        Scanner scan;
        try{
            File words = new File("Words.txt");
            scan = new Scanner(words);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
            return false;
        }
        
        while (scan.hasNextLine()){
            if (scan.nextLine().equals(word)){
                return true;
            }
        }
        
        return false;
    }
    
    //Accessor methods and compareTo
    
    public int getDepth(){
        return depth;
    }
    
    public float getHeuristic(){
        return heuristic;
    }
    
    @Override
    public int compareTo(Node a){
        if (heuristic > a.heuristic){
            return 1;
        }
        else if (heuristic < a.heuristic){
            return -1;
        }
        else{
            return 0;
        }
    }
        
}