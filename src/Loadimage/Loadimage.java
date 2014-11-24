package Loadimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Loadimage {
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		long time1, time2;
		time1 = System.currentTimeMillis();
		String Imagepath=("./Input/HandsomeLin.jpg");
		getImageGRB(Imagepath);
		time2 = System.currentTimeMillis();
		System.out.println("¯Ó®É="+(float)(time2-time1)/1000 + "¬í");
	}*/

	public int[][] getImageGRB(String filePath) {
        File file  = new File(filePath);
        int[][] result = null;
        if (!file.exists()) {
             return result;
        }
        try {
             BufferedImage bufImg = ImageIO.read(file);
             int height = bufImg.getHeight();
             int width = bufImg.getWidth();
             result = new int[width][height];
             for (int i = 0; i < width; i++) {
                  for (int j = 0; j < height; j++) {
                        result[i][j] = bufImg.getRGB(i, j) & 0xFFFFFF;
                        //System.out.println(bufImg.getRGB(i, j) & 0xFFFFFF);
                        //System.out.println("Alpha="+((bufImg.getRGB(i, j)>>24) & 0xFF)+"	Red="+((bufImg.getRGB(i, j)>>16) & 0xFF)+"		Green="+((bufImg.getRGB(i, j)>>8) & 0xFF)+"	Blue="+(bufImg.getRGB(i, j)& 0xFF));
             
                  }
             }
             System.out.println("height="+height+"	width"+width+"	size="+height*width);
             
        } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        }
        
        return result;
  }
}
