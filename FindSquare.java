//Runs search
//Need to input 9 spaced out letters on command line when running main method

import java.util.ArrayList;

public class FindSquare{
    
    public static void main(String[] args){
    
        ArrayList<Character> letters = new ArrayList<Character>();
        for(int i=0; i<args.length; i++){
            letters.add(args[i].charAt(0));
        }
    
        Search findSquare = new Search(letters);
        ArrayList<Node> squareOrder = findSquare.findPath();
    
        if(squareOrder == null){
            System.out.println("There is no valid square!");
        }
        else{
            System.out.println("Here is your square:");
            System.out.println();
        
            char one = squareOrder.get(1).getData();
            char two = squareOrder.get(2).getData();
            char three = squareOrder.get(3).getData();
            char four = squareOrder.get(4).getData();
            char five = squareOrder.get(5).getData();
            char six = squareOrder.get(6).getData();
            char seven = squareOrder.get(7).getData();
            char eight = squareOrder.get(8).getData();
            char nine = squareOrder.get(9).getData();
            
            String row1 = Character.toString(one)+Character.toString(two)+Character.toString(three);
            String row2 = Character.toString(four)+Character.toString(five)+Character.toString(six);
            String row3 = Character.toString(seven)+Character.toString(eight)+Character.toString(nine);
            
            System.out.println(row1);
            System.out.println(row2);
            System.out.println(row3);
        }
    
    }
    
}
