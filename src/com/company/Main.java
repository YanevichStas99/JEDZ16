package com.company;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter data: ");
        String data=scanner.nextLine();
        try {

            String[] dates=data.split("\\.");
            int day=Integer.parseInt(dates[0]);
            int year=Integer.parseInt(dates[2]);
            if(year<2004 || year>2019){
            throw new Exception();
            }
            if (day<=0 || day>32){
                throw new  Exception();
            }
            long time = System.currentTimeMillis();

            String response = HttpUtil.sendRequest("https://api.privatbank.ua/p24api/exchange_rates?json=true&date="+data, null, null);

            time = System.currentTimeMillis() - time;

            System.out.println(response);
            Gson gson=new Gson();
            ExchangeRateResponse response1=gson.fromJson(response,ExchangeRateResponse.class);
            System.out.println(response1);
            for (ExchangeRates exchangeRate : response1.getExchangeRate()) {
                if(exchangeRate.getCurrency()==null){
                    continue;
                }
                if ( exchangeRate.getCurrency().equals("USD")) {
                    System.out.println("Курс доллара: " + exchangeRate.getSaleRateNB());
                    break;
                }
            }

            System.out.println("Request time: " + time / 1000.0 + "s");
        }catch (Exception e){
            System.out.println("Wrong data");
        }

    }
    public static class HttpUtil {



        public static String sendRequest(@NotNull String url, @Nullable Map<String, String> headers, @Nullable String body) {

            String result = null;

            HttpURLConnection urlConnection = null;

            try {

                URL requestUrl = new URL(url);

                urlConnection = (HttpURLConnection) requestUrl.openConnection();

                urlConnection.setReadTimeout(20000);

                urlConnection.setConnectTimeout(20000);

                urlConnection.setRequestMethod("GET"); // optional, GET already by default



                if (body != null && !body.isEmpty()) {

                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestMethod("POST"); // optional, setDoOutput(true) set value to POST

                    DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());

                    outputStream.writeBytes(body);

                    outputStream.flush();

                    outputStream.close();

                }



                if (headers != null) {

                    for (Map.Entry<String, String> entry : headers.entrySet()) {

                        urlConnection.addRequestProperty(entry.getKey(), entry.getValue());

                    }

                }



                int status = urlConnection.getResponseCode();

                System.out.println("Status code:" + status);



                if (status == HttpURLConnection.HTTP_OK) {

                    result = getStringFromStream(urlConnection.getInputStream());

                    Map<String, List<String>> responseHeaders = urlConnection.getHeaderFields();

                    System.out.println("Headers: " + responseHeaders);

                }
                if (status != HttpURLConnection.HTTP_OK){
                    throw new Exception();
                }

            } catch (Exception e) {

                System.out.println("sendRequest failed" + e);

            } finally {

                if (urlConnection != null) {

                    urlConnection.disconnect();

                }

            }

            return result;

        }



        public static String getStringFromStream(InputStream inputStream) throws IOException {

            final int BUFFER_SIZE = 4096;

            ByteArrayOutputStream resultStream = new ByteArrayOutputStream(BUFFER_SIZE);

            byte[] buffer = new byte[BUFFER_SIZE];

            int length;

            while ((length = inputStream.read(buffer)) != -1) {

                resultStream.write(buffer, 0, length);

            }

            return resultStream.toString("UTF-8");

        }



    }
}
