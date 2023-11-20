package net.scorgister.img;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) throws IOException {
		String file = read("path/to/my/file");
		ImageIO.write(Crypt.crypt(file), "png", new File("output.png"));
		System.out.println(Crypt.decrypt(ImageIO.read(new File("output.png"))));
	}
	
	public static String read(String filePath) throws IOException {
		Path path = Paths.get(filePath);
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, StandardCharsets.UTF_8);
	}

}
