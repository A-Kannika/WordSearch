import java.io.*;
/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 27, 2021
 * Assignment 3 - Word Search (Benchmark class)
 * Professor. Christopher Paul Marriott
 */

public class Benchmarker {

    private String inputFileName = "WarAndPeace.txt"; //testing default input file name
    //private String inputFileName = "Text2Test.txt"; //text file to test the program
    private String text; // the text loaded in from the input file
    private MyLinkedList<String> listOfWords = new MyLinkedList<>(); // the linked list of words
    private MyBinarySearchTree<String> treeOfWords = new MyBinarySearchTree<>(false); //the binary search tree of words
    private MyBinarySearchTree<String> balancedTreeOfWords = new MyBinarySearchTree<>(true); //he self-balancing search tree of words
    int count = 0; //use to count total word
    /**
     * List of String: we will define a “word” as any string of consecutive alphanumeric characters
     * or apostrophes. Specifically, this means a word is any string of characters from this set:
     */
//    String[] Numerals = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
//    String[] Lowercase = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
//            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
//    String[] Uppercase = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
//            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
//    String[] Apostrophe = {"\'"};
//    String[] listOfString = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
//            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
//            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
//            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
//            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
//            "\'"};
    char[] listOfString = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '\''};


    /**
     * Using BufferedReader to read the input and loaded into text
     */
    private void readInputFile() {
        int count = 0;
        long start = System.currentTimeMillis();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(inputFileName))) {
            int singleCharInt;
            StringBuilder s = new StringBuilder();
            while ((singleCharInt = bufferedInputStream.read()) != -1) {
                count++;
                s.append((char) singleCharInt);
            }
            bufferedInputStream.close();
            text = s.toString();
        } catch (Exception e) {
            System.out.println("could not find the file!!");
        }
        long end = System.currentTimeMillis();
        System.out.println("======================== Read Input File ========================");
        System.out.println("Reading input file \"" + inputFileName + "\" that has "
                + count + " characters in " + (end - start) + " milliseconds.");
        System.out.println();
    }


    /**
     * Loads each unique word into listOfWords
     */
    private void loadList() {
        MyLinkedList<Character> list = new MyLinkedList<>();
        for (int i = 0; i < listOfString.length; i++) {
            list.add(listOfString[i]);
        }
 //       System.out.println(list.size());
        String temp = text;
        int length = temp.length();

        long start = System.currentTimeMillis();
        while(length != 0){
            StringBuilder word = new StringBuilder();
            for (int i = 0; i < temp.length(); i++) {
                length--;
                if (list.contain(temp.charAt(i))) {
                    word.append(temp.charAt(i));
                } else {
                    if (word.toString().length() != 0){
                        count++;
                    }
                    if (word.toString().length() != 0 && !(listOfWords.contain(word.toString()))) {
                        listOfWords.add(word.toString());
                        word = new StringBuilder();
                    } else {
                        word = new StringBuilder();
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("======================== Load List ========================");
        System.out.println("Add unique words to a linked list: running time = " + (end - start) + " milliseconds.\n"
                + "The linked list made " + listOfWords.count + " comparisons.\n"
                + "The linked list has a length of " + listOfWords.size());
        System.out.println();
    }

    /**
     * Loads each unique word into treeOfWords --- Binary Search Tree
     */
    private void loadTree() {
        MyBinarySearchTree<Character> list = new MyBinarySearchTree<>();
        for (int i = 0; i < listOfString.length; i++) {
            list.add(listOfString[i]);
        }

        String temp = text;
        int length = temp.length();

        long start = System.currentTimeMillis();
        while(length != 0){
            StringBuilder word = new StringBuilder();
            for (int i = 0; i < temp.length(); i++) {
                length--;
                if (list.contain(temp.charAt(i))) {
                    word.append(temp.charAt(i));
                } else {
                    if (word.toString().length() != 0 && !(treeOfWords.contain(word.toString()))) {
                        treeOfWords.add(word.toString());
                        word = new StringBuilder();
                    } else {
                        word = new StringBuilder();
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("======================== Binary Search Tree ========================");
        System.out.println("Adding unique words to a binary search tree: running time = " + (end - start) + " milliseconds.\n"
                + "The binary search tree made " + treeOfWords.count + " comparisons.\n"
                + "The binary search tree has a length of " + treeOfWords.size()
                + "\nThe binary search tree has a depth of " + treeOfWords.height());
        System.out.println();
    }

    /**
     * loads each unique word into balancedTreeOfWords --- AVL Tree
     */
    private void loadBalancedTree() {
        MyBinarySearchTree<Character> list = new MyBinarySearchTree<>();
        for (int i = 0; i < listOfString.length; i++) {
            list.add(listOfString[i]);
        }

        String temp = text;
        int length = temp.length();

        long start = System.currentTimeMillis();
        while(length != 0){
            StringBuilder word = new StringBuilder();
            for (int i = 0; i < temp.length(); i++) {
                length--;
                if (list.contain(temp.charAt(i))) {
                    word.append(temp.charAt(i));
                } else {
                    if (word.toString().length() != 0 && !(balancedTreeOfWords.contain(word.toString()))) {
                        balancedTreeOfWords.add(word.toString());
                        word = new StringBuilder();
                    } else {
                        word = new StringBuilder();
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("======================== AVL Tree ========================");
        System.out.println("Adding unique words to an AVL tree: running time = " + (end - start) + " milliseconds.\n"
                + "The AVL tree made " + balancedTreeOfWords.count + " comparisons.\n"
                + "The AVL tree has a length of " + balancedTreeOfWords.size()
                + "\nThe AVL tree has a depth of " + balancedTreeOfWords.height()
                + "\nThe AVL tree made " + balancedTreeOfWords.rotateCount + " rotations.");
        System.out.println();
    }

    public String toString() {
        System.out.println("======================== Number of unique words and total words ========================");
        return "\"War and Peace\" by Leo Tolstoy has : " + listOfWords.size() + " unique words and " + count + " total words";
    }

    public void runProgram(){
        Benchmarker b = new Benchmarker();
        b.readInputFile();
        b.loadList();
        b.loadTree();
        b.loadBalancedTree();
        System.out.println(b.toString());
    }
}
