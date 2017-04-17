
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
//splitted를 이용할 때
import java.util.ArrayList;
import java.util.List;


public class JsonDemo {

    public static void main(String[] args) {

        //JSONObject obj = new JSONObject();
        String clientId = "eYfQVQIpJreWyDSOFCK7";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "oIE89xguGC";//애플리케이션 클라이언트 시크릿값";

        String line = " ";

        try {

            /* splitted 를 사용할 때*/
            //List list = new ArrayList<>();

            //파일 로딩
            FileReader txtFr = new FileReader("src\\closer.txt");
            BufferedReader txtBr = new BufferedReader(txtFr);

            /* splitted 를 사용할 때 */
            /*String line = " ";

            while ((line = txtBr.readLine()) != null) {
                String[] splitted = line.split("\n");

                for (String e : splitted) {
                    list.add(e);
                }
            }*/

            /*readLine()을 이용할 때*/

            //while문을 끝까지 반복
            while ((line = txtBr.readLine()) != null) {
                line = txtBr.readLine();
                System.out.println("한 줄: " + line);
                if(txtBr.readLine().equals("")){
                    continue;
                }





                //테스트 하기 위한 출력
                //System.out.println(list);

                // splitted 이용할 때 사용하는 코드
                //String text = URLEncoder.encode(String.valueOf(list), "UTF-8");

                String text = URLEncoder.encode(line.toString(), "UTF-8");
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

                System.out.println(response.toString());

                String jsonData = response.toString();
                JSONObject obj = new JSONObject(jsonData);

                String result = obj.getJSONObject("message").getJSONObject("result").getString("translatedText");
                System.out.println(result);


                JSONObject msgObj = obj.getJSONObject("message");
                JSONObject type = msgObj.getJSONObject("result");
                String message = type.getString("translatedText");
                System.out.println(type);
                System.out.println(message);

                //System.out.println(response.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}