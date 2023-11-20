package net.scorgister.img;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Crypt {
	
	public static BufferedImage crypt(String data) {
		byte[] bs = data.getBytes();
		int len = bs.length;
		len += len / 255 + 2;
		
		
		int w = 1, h = 1;
		do {
			w = len / h;
			h += 1;
		}while(h < w);

		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int x = 0;
		int y = 0;
		
		Random rand = new Random();

		for(int i = 0; i < bs.length / 255; i++) {
			int red = 0, green = 0, blue = 0;
			switch(i%3) {
				case 0:
					red = 255;
					green = rand.nextInt(255);
					blue = rand.nextInt(255);
					break;
				case 1:
					red = rand.nextInt(255);
					green = 255;
					blue = rand.nextInt(255);
					break;
				case 2:
					red = rand.nextInt(255);
					green = rand.nextInt(255);
					blue = 255;
					break;
			}
			
			int rgb = (red << 16) | (green << 8) | blue;
			
			img.setRGB(x, y, rgb);
			x += 1;
			if(x == w) {
				x = 0;
				y += 1;
			}
		}

		int red = bs.length % 255, green = rand.nextInt(255), blue = rand.nextInt(255);
		int rgb = (red << 16) | (green << 8) | blue;
		
		img.setRGB(x, y, rgb);
		x += 1;
		if(x == w) {
			x = 0;
			y += 1;
		}

		red = 4;
		green = 1;
		blue = 8;
		rgb = (red << 16) | (green << 8) | blue;
		
		img.setRGB(x, y, rgb);
		x += 1;
		if(x == w) {
			x = 0;
			y += 1;
		}
		
		for(int i = 0; i < w * h - bs.length / 255 - 2 ; i++) {
			red = 0;
			green = 0;
			blue = 0;
			if(i < bs.length) {
				switch(i%3) {
					case 0:
						red = bs[i];
						green = rand.nextInt(255);
						blue = rand.nextInt(255);
						break;
					case 1:
						red = rand.nextInt(255);
						green = bs[i];
						blue = rand.nextInt(255);
						break;
					case 2:
						red = rand.nextInt(255);
						green = rand.nextInt(255);
						blue = bs[i];
						break;
				}
			}else {
				red = rand.nextInt(255);
				green = rand.nextInt(255);
				blue = rand.nextInt(255);
			}

			rgb = (red << 16) | (green << 8) | blue;
			
			img.setRGB(x, y, rgb);
			x += 1;
			if(x == w) {
				x = 0;
				y += 1;
			}
		}
		
		return img;
	}
	
	public static String decrypt(BufferedImage img) {
		int w = img.getWidth(), h = img.getHeight();
		int x = 0;
		int y = 0;
		
		int len = 0;
		
		for(int i = 0; i < w * h; i++) {	
			int rgb = img.getRGB(x, y);
			int red = (rgb >> 16) & 0xFF, green = (rgb >> 8) & 0xFF, blue = rgb & 0xFF;
			if(red == 4 && green == 1 && blue == 8) {
				x += 1;
				if(x == w) {
					x = 0;
					y += 1;
				}
				break;
			}
			switch(i%3) {
				case 0:
					if(red == 255)
						len += red;
					else
						len += red;
					break;
				case 1:
					if(green == 255)
						len += green;
					else
						len += red;
					break;
				case 2:
					if(blue == 255)
						len += blue;
					else
						len += red;
					break;
			}
			
			x += 1;
			if(x == w) {
				x = 0;
				y += 1;
			}

		}
		byte[] bs = new byte[len];
		for(int i = 0; i < len; i++) {
			int rgb = img.getRGB(x, y);
			int red = (rgb >> 16) & 0xFF, green = (rgb >> 8) & 0xFF, blue = rgb & 0xFF;

			switch(i%3) {
				case 0:
					bs[i] = (byte) red;
					break;
				case 1:
					bs[i] = (byte) green;
					break;
				case 2:
					bs[i] = (byte) blue;
					break;
			}
			
			x += 1;
			if(x == w) {
				x = 0;
				y += 1;
			}
		}

		return new String(bs);
	}

}
