

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class CloserAutoTranslator {

    public static void main(String[] args) {

        String clientId = "eYfQVQIpJreWyDSOFCK7";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "oIE89xguGC";//애플리케이션 클라이언트 시크릿값";

    public static String searchAndReturnJson(String keyword) {

        try {
            String text = URLEncoder.encode("그린팩토리", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }


    //todo: 1. file에서 한 line씩 읽어온다.
        try

    {
        BufferedReader fbr = new BufferedReader(new FileReader("src\\closer.txt"));
        String line = " ";

        while ((line = fbr.readLine()) != null) {

            // 공백없애는 메소드 trim()
            if (line.trim().equals("")) {
                continue;
            }

            //todo: 2. line을 파파고 번역 서비스를 요청한다.

            String text = URLEncoder.encode(line, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=en&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            //todo: 3. 결과 json을 파싱하여 콘솔에 출력한다.
            JSONObject obj = new JSONObject(response.toString());
            //System.out.println(obj.toString());

            String result = obj.getJSONObject("message").getJSONObject("result").getString("translatedText");
            System.out.println(result);
                /*String result = obj.getJSONObject("message")
                        .getJSONObject("result").getString("translatedText");
                System.out.println(result);*/

        }

    } catch(
    FileNotFoundException e)

    {
        e.printStackTrace();
    } catch(
    IOException e)

    {
        e.printStackTrace();
    } catch(
    Exception e)

    {
        System.out.println(e);
    }
}
}
