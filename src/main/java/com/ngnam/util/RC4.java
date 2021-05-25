package com.ngnam.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RC4 {

    public static byte[] encrypt(BufferedImage bufferImage, String typeOfImage, int[] key) {
        int width = bufferImage.getWidth();
        int height = bufferImage.getHeight();
        int lengthKey = key.length;


        // Thực hiện mã hóa
        // Giai đoạn khởi tạo
        int[] S = new int[256];
        int[] T = new int[256];
        int j = 0, i = 0;
        for (i = 0; i < 256; i++) {
            S[i] = i;
            T[i] = key[i % lengthKey];
        }

        // Thuật toán lập lịch khóa (KSA)
        for (i = 0; i < 256; i++){
            j = (j + S[i] + T[i]) % 256;
            // Thực hiện hoán vị S[i] và S[j]
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }

        // Thuật toán sinh số giả ngẫu nhiên PRGA và giải mã
        i = 0; j = 0;
        for (int i0 = 0; i0 < width; i0++) {
            for (int i1 = 0; i1 < height; i1++) {
                int t = 0, k = 0, temp = 0;
                Color c = new Color(bufferImage.getRGB(i0, i1), true);

                // Với màu red
                int red = c.getRed();
                i = (i + 1) % 256;
                j = (j + S[i]) % 256;
                // Thực hiện hoán vị S[i] và S[j]
                temp = S[i];
                S[i] = S[j];
                S[j] = temp;
                t = (S[i] + S[j]) % 256;
                k = S[t];
                red = red ^ k;

                // Với màu green
                int green = c.getGreen();
                i = (i + 1) % 256;
                j = (j + S[i]) % 256;
                // Thực hiện hoán vị S[i] và S[j]
                temp = S[i];
                S[i] = S[j];
                S[j] = temp;
                t = (S[i] + S[j]) % 256;
                k = S[t];
                green = green ^ k;

                // Với màu blue
                int blue = c.getBlue();
                i = (i + 1) % 256;
                j = (j + S[i]) % 256;
                // Thực hiện hoán vị S[i] và S[j]
                temp = S[i];
                S[i] = S[j];
                S[j] = temp;
                t = (S[i] + S[j]) % 256;
                k = S[t];
                blue = blue ^ k;

                bufferImage.setRGB(i0, i1, new Color(red, green, blue).getRGB());
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferImage, typeOfImage, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
