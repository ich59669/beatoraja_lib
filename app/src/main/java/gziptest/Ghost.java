package gziptest;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class Ghost {

    public static void main(String[] args) {
        var size = 4 * 150;
        var data = new byte[size];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)(Math.random() * 12 + 5);
        }
        System.out.println(GZIPandBase64encoding(data).length());
        System.out.println(GZIPcompressing(data).length);
    }
    
    public static String GZIPandBase64encoding(byte[] data) {
        var output = new ByteArrayOutputStream();
        var base64 = Base64.getUrlEncoder().wrap(output);
        try (var gzip = new GZIPOutputStream(base64)) {
            gzip.write(data);            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return output.toString();
    }

    public static byte[] GZIPcompressing(byte[] data) {
        var output = new ByteArrayOutputStream();
        try (var gzip = new GZIPOutputStream(output)) {
            gzip.write(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return output.toByteArray();
    }
}
