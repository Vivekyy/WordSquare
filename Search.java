//Runs greedy search for 3x3 puzzle

import java.util.Collections;
import java.util.ArrayList;

public class Search{
    
    Node root;
    ArrayList<Node> open;
    ArrayList<Node> closed;
    
    public Search(ArrayList<Character> letters){
        root = new Node('1', null, letters);
        open = new ArrayList<Node>();
        closed = new ArrayList<Node>();
        open.add(root);
    }
    
    public Node findGoal(){
        while(!open.isEmpty()){
            
            Collections.sort(open);
            
            Node current = open.remove(0);
            closed.add(current);
            
            ArrayList<Node> successors = current.setSuccessors();
            
            for(int i=0; i<successors.size(); i++){
                if (successors.get(i).getHeuristic() == 0){
                    return successors.get(i);
                }
            }
            
            boolean present;
            //For each successor
            for (int a=0; a<successors.size(); a++){
                //Find if successor is in open or closed
                present = false;
                for(int b=0; b<open.size(); b++){
                    if (successors.get(a) == open.get(b)){
                        present = true;
                        break;
                    }
                }
                if (present == false){
                    for(int c=0; c<closed.size(); c++){
                        if (successors.get(a) == closed.get(c)){
                            present = true;
                            break;
                        }
                    }
                }
                //Then add it if it isn't in open or closed
                if (present == false){
                    open.add(successors.get(a));
                }
            }
        }
        //If goal is not found, return null for failure
        return null;
    }
    
    public ArrayList<Node> findPath(){
        
        Node goal = findGoal();
        
        //In case goal is not found, still return null
        if (goal==null){
            return null;
        }
        else{
            ArrayList<Node> path = new ArrayList<Node>();
            Node current = goal;
            while (current != root){
                path.add(current);
                current = current.parent;
            }
            return path;
        }
    }
    
}