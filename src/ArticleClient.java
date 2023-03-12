import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ArticleClient {

    public static int countArticles(String sentence) {
        String[] words = sentence.toLowerCase().split("\\s+");
        int count = 0;
        for (String word : words) {
            String wd = word.toLowerCase();
            if (wd.equals("a") || wd.equals("an") || wd.equals("the")) {
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

            int numberOfartical = countArticles(text);

            oos.writeObject("Text = "+ text +"\n"+
                    "Number Of article present in the sentence= "+ numberOfartical);


            if (text.equals("exit")) break;
            String serverReply = (String) ois.readObject();


            System.out.println("From Server: " + serverReply);

        }
        socket.close();
    }
}