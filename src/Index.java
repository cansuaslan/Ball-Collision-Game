public class Index {
    // Two private instance variables to store the row and column indices
    private int row;
    private int col;
    // Constructor that takes a 2D array of strings and a character as input
    public Index(String[][] arr, char target) {
        // Initialize the row and column indices to -1 which means not found yet.
        row = -1;
        col = -1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                // If the element is equal to the target character, store its row and column indices.
                if (arr[i][j].charAt(0) == target) {
                    row = i;
                    col = j;
                    // Exit the loop since we've found the first occurrence of the target character
                    break;
                }
            }
            // If we've already found the target character, exit the outer loop as well
            if (row != -1 && col != -1) {
                break;
            }
        }
    }
    //Method to get to row index and column index.
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}


