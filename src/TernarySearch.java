import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class TernarySearch {

    /* functionality to add
        TernarySearch constructor from file
        Search word using a single input and return all possible matches (Add all keys encountered to queue)
        Inorder traversal from prefix until null link is reached
        Run inorder traversal on root to return all words on menu in alphabetical order
        Return full stop information for all prefix matches

        how search algorithm works:
        - run normal search and find if prefix is there
        - run inorder traversal on the node returned from normal search
    */

    // public static void main(String[] args) {
    //     TernarySearch TST = new TernarySearch("stops.txt");
    //     printArrayList( TST.search("hi") );
    //     printArrayList( TST.search("h") );
    //     printArrayList( TST.search("j") );
    //     printArrayList( TST.search("jack") );
    //     printArrayList( TST.search("himbo") );
    //     printArrayList( TST.search("MARINE WAY") );
    //     printArrayList( TST.search("ELW") );
    //     printArrayList( TST.search("UNGLESS WAY FS GUILDFORD WAY WB") );

    // }

    public static void printArrayList(ArrayList<String> array) {

        for (int i = 0; i < array.size(); i++) 
        {
            System.out.println( array.get(i) );
        }
    }
    
    TernarySearch(String filename) {
        
        root = null;
        File file = new File(filename);
        try 
        {
            Scanner scanner = new Scanner(file);

            String currentLine = "";

            while ( scanner.hasNextLine() )
            {
                currentLine = scanner.nextLine();
                Scanner line_scanner = new Scanner(currentLine);
                line_scanner.useDelimiter( "," );
    
                line_scanner.next();
                line_scanner.next();
                
                String stopName = line_scanner.next();
                String prefix = stopName.substring(0,2);
                if (prefix.equals("NB") || prefix.equals("WB") || prefix.equals("SB") || prefix.equals("EB") )
                {
                    stopName = stopName.substring(3).concat(" " +prefix);
                }

                add( stopName.toCharArray() );
                //add( line_scanner.nextLine().toCharArray() ); // you can uncomment this like when working with simple inputs
                line_scanner.close();
            }
    
            scanner.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Node root;
    private boolean finishedAdd;
    
    private void add (char[] stopName) {

        if (stopName.length != 0)
        {
            if (root == null)
                root = new Node( stopName[0], -1 );
            finishedAdd = false;
            add( stopName, 0, root );    
        }    
    }

    private Node add (char[] stopName, int i, Node node) {
        
        if (node == null) 
            node = new Node( stopName[i], -1 );

        if (node.key < stopName[i])
            node.left = add( stopName, i, node.left );
        else if (node.key > stopName[i])
            node.right = add( stopName, i, node.right );
        else if (i < stopName.length - 1)
            node.centre = add( stopName, ++i, node.centre );

        if (i == stopName.length - 1 && !finishedAdd)
        {
            node.setValue(i);
            finishedAdd = true;
        }
        
        return node;
    }

    public ArrayList<String> search (String input) {

        foundWord = false;
        ArrayList<String> search_hits = new ArrayList<String>();
        Node origin = search(input.toCharArray(), 0, root);

        if (foundWord)
        {
            System.out.println( "Found an exact match for: " + input  );
            search_hits.add(input);
        }
        if (origin != null)
        {
            match( origin.centre, search_hits, "");
            System.out.println( "Found " + search_hits.size() + " possible matches." );
            for (int i = (foundWord?1:0); i < search_hits.size(); i++) 
            {
                // NB: Currently, the function returns the search result cut off at the end of the input string.
                search_hits.set( i, input + search_hits.get(i) );
            }
        }
        else // will return a null variable!
        {
            System.out.println( "No matches found." );
        }

        return search_hits;
    }

    private boolean foundWord;

    private Node search (char[] stopName, int i, Node node) {

        if (node != null)
        {
            if (node.key < stopName[i])
                node = search( stopName, i, node.left );
            else if (node.key > stopName[i])
                node = search( stopName, i, node.right );
            else if (i < stopName.length - 1) // if node.key == stopName[i]
                node = search(stopName, ++i, node.centre );

            if (node != null && i == stopName.length - 1 && node.value != -1)
                foundWord = true;

            return node;
        }
        return null;

    }

    private void match(Node node, ArrayList<String> matches, String prefix) {

        if (node != null)
        {
                match( node.left, matches, prefix );
                match( node.centre, matches, prefix + node.key );
                match( node.right, matches, prefix );

                if ( node.value != -1 )
                {
                    prefix += node.key;
                    matches.add(prefix);
                }
        }
    }

    // Node class slightly altered from Sedgewick & Wayne (Assignment 2 in CSU22011)
    private class Node {
        private char key;           // sorted by key
        private int value;
        private Node centre, left, right;  // left and right subtrees

        public Node(char key, int value) {
            this.key = key;
            this.value = value;
            this.centre = null;
            this.left = null;
            this.right = null;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


}
