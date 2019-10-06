import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader scroll = new BufferedReader(new FileReader(args[0]));
            Farm farm = new Farm();
            try {
                String line = scroll.readLine();
                while  (line != null) {                    
                    try {
                        farm.runInstruction(line);
                    } catch (IllegalInstructionException e) {
                        System.err.println(e.getMessage());
                    }
                    line = scroll.readLine();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
