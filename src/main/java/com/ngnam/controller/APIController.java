package com.ngnam.controller;

import com.ngnam.util.RC4;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

@RestController
@RequestMapping(path="/api")
public class APIController {

    // API lấy hình ảnh từ server trả về cho giao diện
    @GetMapping(path="/getplains/{photo}")
    public ResponseEntity<ByteArrayResource> getPlainImage(
            @PathVariable("photo") String photo) {
        // Lấy định dạng của ảnh
        String[] typeOfImage = photo.split("\\.");
        String contentType = "image/" + typeOfImage[1];

        if (!photo.isEmpty() || photo != null) {
            try {
                Path fileName = Paths.get("uploads/", photo);
                byte[] buffer = Files.readAllBytes(fileName);

                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(byteArrayResource);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    // API lấy hình ảnh từ server trả về cho giao diện
    @GetMapping(path="/getciphers/{photo}/{key}")
    public ResponseEntity<ByteArrayResource> getCipherImage(
            @PathVariable("photo") String photo,
            @PathVariable("key") String key) {
        // Tách key từ chuỗi
        String[] keyStr = key.split("\\s+");
        int[] keyArr = new int[keyStr.length];
        for (int i = 0; i < keyStr.length; i++) {
            keyArr[i] = Integer.parseInt(keyStr[i]);
        }

        // Lấy định dạng của ảnh
        String[] typeOfImage = photo.split("\\.");
        String contentType = "image/" + typeOfImage[1];

        if (!photo.isEmpty() || photo != null) {
            try {
                Path fileName = Paths.get("uploads/", photo);
                byte[] buffer = Files.readAllBytes(fileName);

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                BufferedImage bufferImage = null;

                try {
                    bufferImage = ImageIO.read(bais);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                buffer = RC4.encrypt(bufferImage, typeOfImage[1], keyArr);

                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(byteArrayResource);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    // API upload file image to server
    @PostMapping(path="/uploadfile")
    public String saveBrand(MultipartHttpServletRequest form) {
        // Lấy ra file Multipart
        Iterator<String> listNamePaths = form.getFileNames();
        MultipartFile multipartFile = form.getFile(listNamePaths.next());

        if (multipartFile.isEmpty()) {
            return "";
        }
        // Đưa dữ liệu vào file uploads/ của project
        Path path = Paths.get("uploads/");
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Files.copy(inputStream,
                    path.resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return multipartFile.getOriginalFilename();
    }
}
