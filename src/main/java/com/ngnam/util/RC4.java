package com.ngnam.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RC4 {

    public static byte[] encrypt(BufferedImage bufferImage, String typeOfImage, int[] key) {
        // Lấy kích thước của ảnh
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

                // Lấy giá trị điểm ảnh tại tọa độ (i0, i1)
                Color c = new Color(bufferImage.getRGB(i0, i1), true);

                int[] k = new int[3];
                int t = 0, temp = 0;
                for (int count = 0; count < 3; count++) {
                    i = (i + 1) % 256;
                    j = (j + S[i]) % 256;
                    // Thực hiện hoán vị S[i] và S[j]
                    temp = S[i];
                    S[i] = S[j];
                    S[j] = temp;
                    t = (S[i] + S[j]) % 256;
                    k[count] = S[t];
                }

                // Với màu red
                int red = c.getRed();
                red = red ^ k[0];

                // Với màu green
                int green = c.getGreen();
                green = green ^ k[1];

                // Với màu blue
                int blue = c.getBlue();
                blue = blue ^ k[2];

                // Cập nhập lại điểm ảnh
                bufferImage.setRGB(i0, i1, new Color(red, green, blue).getRGB());
            }
        }

        // Chuyển BufferImage -> mảng byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferImage, typeOfImage, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
