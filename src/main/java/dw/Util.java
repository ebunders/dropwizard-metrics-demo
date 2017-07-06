package dw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static java.lang.String.format;

/**
 * Created by ernst on 6-7-17.
 */
public class Util {
    public static boolean readUrl(String url){
        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(new URL(url).openStream()))){
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

            return true;
        } catch (Throwable e){
            System.out.println(format("Can not load url [%s], reason: %s", url, e.getMessage()));
            return false;
        }
    }
}
