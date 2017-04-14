import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danawacomputer on 2017-04-14.
 */
public class URLDemo {
    public static void main(String[] args) {
        String address = "https://api.github.com/users/soongon";

        //문자배열 담을 List생성


        try {

            URL url = new URL(address);

            //url connection이라는 객체를 생성
            URLConnection conn = url.openConnection();

            InputStream is = url.openStream();
            //16bit만 read해서 아래의
            //InputStreamReader, BufferedReader 메소드를 이용하여서 불러옴
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            List<String> list = new ArrayList<>();
            int count = 0;
            int wordCount = 0;

            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" ");

                for (String e : splitted) {
                    list.add(e);
                }

                for (String e : list) {
                    if (!e.trim().equals("")) {
                        count++;
                    }
                }
                wordCount += splitted.length;
            }

            System.out.println(list);
//            System.out.println("count: "+count);
//            System.out.println("list.size(): "+list.size());
//            System.out.println("wordCount: "+wordCount);

            /*
            System.out.println(conn.getContentType());
            System.out.println(conn.getContentEncoding());

            // 프로토콜이 잘 못 입력되었을 때만 예외를 출력함.
            // 도메인이 잘못 된 경우에도 404 not found도 값을 받으므로 접속이 잘 된다는 메세지가 출력 됨.
            System.out.println(url.getProtocol());
            // 출력결과: address가 https://~~~~~.~~~ 일때
            // url.getProtocol() 은 https 가 출력됨.
            System.out.println("접속이 잘 됩니다.");
            */

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
