import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Math.*;


public class Main {
    public static int swap(String[][] multiLines, int rowIndex, int colIndex, int newRowIndex,int newColIndex,ArrayList<String> isGameOver){
        //first paramater represents the multiarray to be processed. The other two following gives us ball that wants to swap.
        //penultimate couple stands for the place that will be swapped and the last one checks if the ball fall into the hole or not.
        String targetGrid = multiLines[newRowIndex][newColIndex];
        int total = 0;
        if(targetGrid.equals("H")){
            //If ball wants to swap with a hole the game ends.
            multiLines[rowIndex][colIndex] = " ";
            isGameOver.add("Game over");
        } else if (targetGrid.equals("R") || targetGrid.equals("Y") || targetGrid.equals("B")) {
            //The score varies depending on the colors.
            if (targetGrid.equals("R")) {
                total += 10;
            } else if (targetGrid.equals("Y")) {
                total += 5;
            } else if (targetGrid.equals("B")) {
                total -= 5;
            }// no matter the score, the ball will be replaced by the "X" at the end.
            multiLines[newRowIndex][newColIndex] = "*";
            multiLines[rowIndex][colIndex] = "X";
        } else if (targetGrid.equals("W")) {
            //If the ball hits "W" it should bounce. I prefered the evaulate the position that the ball will bounce.
            int depopRow = rowIndex;
            int depopCol = colIndex;
            rowIndex = 2*rowIndex - newRowIndex;
            colIndex = 2*colIndex - newColIndex;
            //If the "W" is within the limits, I subtracted a number equal to the length of the board so that it does not go extra.
            //I took the help of absolute value in case it is greater or less than zero.
            if (rowIndex<0 || rowIndex>4) {
                rowIndex = Math.abs(Math.abs(rowIndex) -5);
            } else if (colIndex<0 || colIndex > 4) {
                colIndex = Math.abs(Math.abs(colIndex)-5);
            }//I called the function again inside the function, since the location where it is moved may also have properties.
            total += swap(multiLines,depopRow,depopCol,rowIndex,colIndex,isGameOver);
        }else{
            //If everything is as it should be, I followed the standard procedures.
            String depo = multiLines[newRowIndex][newColIndex];
            multiLines[rowIndex][colIndex] = depo ;
            multiLines[newRowIndex][newColIndex] = "*";
        }
        return total;
    }
    public static void main(String[] args) {
        String file_board = args[0];
        String file_moves = args[1];
        String[] lines = ReadFromFile.readFile(file_board);
        String[] moves = ReadFromFile.readFile(file_moves);
        String output = "Game board:\n";
        //To write the board as it asked for.
        for(String line : lines){
            output += line + "\n";
        }
        String[][] multiLines = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            multiLines[i] = lines[i].split(" ");
        }
        //System.out.println(Arrays.deepToString(multiLines));
        String move = moves[0];
        String strippedMove = move.replaceAll(" ", "");
        char[] finalMove = new char[strippedMove.length()];
        for (int k = 0; k < strippedMove.length(); k++) {
            finalMove[k] = strippedMove.charAt(k);
        }
        ArrayList<String> isGameOver = new ArrayList<>();
        int total = 0;
        // It stores the played moves.
        String strike = "";
        for(char fMove : finalMove){
            if(isGameOver.size() == 1){
                break;
            }else{
                Index targetIndex = new Index(multiLines,'*');
                int rowIndex = targetIndex.getRow();
                int colIndex = targetIndex.getCol();
                strike += fMove + " ";
                switch (fMove){
                    case 'L':
                        if (colIndex == 0) {
                            int newColIndex = multiLines.length - 1;
                            total += swap(multiLines,rowIndex,colIndex,rowIndex,newColIndex,isGameOver);
                        }else {
                            int newColIndex = colIndex -1;
                            total += swap(multiLines, rowIndex,colIndex,rowIndex,newColIndex,isGameOver);
                        }
                        break;
                    case 'R':
                        if (colIndex == multiLines[rowIndex].length-1) {
                            int newColIndex = 0;
                            total += swap(multiLines, rowIndex, colIndex, rowIndex, newColIndex, isGameOver);
                        }else {
                            int newColIndex = colIndex + 1;
                            total += swap(multiLines, rowIndex, colIndex, rowIndex, newColIndex, isGameOver);
                        }
                        break;
                    case 'U':
                        if (rowIndex == 0) {
                            int newRowIndex = multiLines.length - 1;
                            total += swap(multiLines, rowIndex, colIndex, newRowIndex, colIndex, isGameOver);
                        }else {
                            int newRowIndex = rowIndex - 1;
                            total += swap(multiLines, rowIndex, colIndex, newRowIndex, colIndex, isGameOver);
                        }
                        break;
                    case  'D':
                        if (rowIndex == multiLines.length - 1) {
                            int newRowIndex = 0;
                            total += swap(multiLines, rowIndex, colIndex, newRowIndex, colIndex, isGameOver);
                        }else {
                            int newRowIndex = rowIndex + 1;
                            total += swap(multiLines, rowIndex, colIndex, newRowIndex, colIndex, isGameOver);
                        }
                        break;
                    default:
                        System.out.println("Invalid move");
                        break;
                }
            }
        }
        output += "\nYour movement is:\n" + strike + "\n" ;
        output += "\nYour output is:\n";
        for (String[] row : multiLines) {
            for (String cell : row) {
                output += cell + " ";
            }
            output += "\n";
        }
        if(isGameOver.size() == 1){
            output += "\nGame Over!";
        }
        output += "\nScore: " + total;
        FileOutput.writeToFile("output.txt",output ,false,false);
    }
}