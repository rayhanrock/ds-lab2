import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class VowelCount {

    public static int countWordsStartingWithVowel(String sentence) {
        int count = 0;
        String[] words = sentence.split("\\s+");
        for (String word : words) {
            char firstChar = word.toLowerCase().charAt(0);
            if (firstChar == 'a' || firstChar == 'e' || firstChar == 'i' || firstChar == 'o' || firstChar == 'u') {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Client Started....");

        Socket socket = new Socket("127.0.0.1", 333);
        System.out.println("Client Connected....");

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        while (true) {

            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();

            int numberOfvowel = countWordsStartingWithVowel(text);

            oos.writeObject("Text = "+ text +"\n"+
                    "Number Of word that started with Vowel = "+ numberOfvowel);


            if (text.equals("exit")) break;
            String serverReply = (String) ois.readObject();


            System.out.println("From Server: " + serverReply);

        }
        socket.close();
    }
}