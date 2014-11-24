import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
public class DBScan {
	static int[][][] imageRGB;
	static int height;
	static int width;
	static int Radius;
	static int MinPts;
	static int CountColor=0;
	static int R=0;
	static int G=1;
	static int B=2;
	private static Scanner scanner;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        scanner = new Scanner(System.in);
        System.out.print("搜尋的半徑=");
        Radius=Integer.parseInt(scanner.next());   
        
        scanner = new Scanner(System.in);
        System.out.print("MinPts=");
        MinPts=Integer.parseInt(scanner.next());   
        
		long Startime, Endtime;
		Startime = System.currentTimeMillis();
		getImageGRB("./Input/HandsomeLin.jpg");
		/*for(int i=0;i<80;i++){
			for(int j=0;j<80;j++){
				//System.out.println("x="+i+"	y="+j+"	color="+imageRGB[i][j][3]);
				//System.out.println("color="+imageRGB[i][j][3]);
			}
		}*/
		
		Output();
		
		
		Endtime = System.currentTimeMillis();
		System.out.println("耗時="+(float)(Endtime-Startime)/1000 + "秒");
	}

	public static void getImageGRB(String filePath) {	//擷取圖片資訊儲存陣列
		//int[][][] imageRGB;
        File file  = new File(filePath);
        if (!file.exists()) {
        	System.out.println("路徑錯誤");
        }
        try {
             BufferedImage bufImg = ImageIO.read(file);
             height = bufImg.getHeight();
             width = bufImg.getWidth();
             imageRGB=new int[width][height][4];
             for (int i = 0; i < width; i++) {
                  for (int j = 0; j < height; j++) {
                	  imageRGB[i][j][R]=((bufImg.getRGB(i, j)>>16) & 0xFF);
                	  imageRGB[i][j][G]=((bufImg.getRGB(i, j)>>8) & 0xFF);
                	  imageRGB[i][j][B]=((bufImg.getRGB(i, j)& 0xFF));
                	  
                	  imageRGB[i][j][3]=0;
                      //System.out.println("Alpha="+((bufImg.getRGB(i, j)>>24) & 0xFF)+"	Red="+((bufImg.getRGB(i, j)>>16) & 0xFF)+"		Green="+((bufImg.getRGB(i, j)>>8) & 0xFF)+"	Blue="+(bufImg.getRGB(i, j)& 0xFF));
                  }
             }
             System.out.println("height="+height+"	width"+width+"	size="+height*width);
             
             Scan();
        } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        }

  }
	
	
	public static void Scan() {
	
		for(int i=0;i<imageRGB.length;i++){
			for(int j=0;j<imageRGB[i].length;j++){
				ArrayList<Integer> xpoint = new ArrayList<Integer>();
				ArrayList<Integer> ypoint = new ArrayList<Integer>();
				int color=0;
				for(int x=i-Radius;x<i+Radius+1;x++){
					for(int y=j-Radius;y<j+Radius+1;y++){
						if(x-Radius+1<0 || y-Radius+1<0 ||x+Radius>imageRGB.length ||y+Radius>imageRGB[i].length ||(x-Radius+1==i && y-Radius+1==j)){
							continue;
						}else{
							if(Distance(imageRGB[i][j],imageRGB[x][y])){
								xpoint.add(x);
								ypoint.add(y);
								if(imageRGB[x][y][3]!=0){
									color=imageRGB[x][y][3];
								}
								
							}
						}
					}
				}
				if(xpoint.size()>=MinPts){
					if(color==0){
						CountColor++;
						color=CountColor;
						System.out.println("color="+color);
					}
					for(int z=0;z<xpoint.size();z++){
						imageRGB[xpoint.get(z)][ypoint.get(z)][3]=color;
						
					}
					imageRGB[i][j][3]=color;
				}
				
			}	
		}
		
	}
	public static boolean Distance(int[] MainPoint,int[] ComparePoint) {
		Double Distan;
		Distan=Math.sqrt(((MainPoint[R]-ComparePoint[R])*(MainPoint[R]-ComparePoint[R]))+((MainPoint[G]-ComparePoint[G])*(MainPoint[G]-ComparePoint[G]))+((MainPoint[B]-ComparePoint[B])*(MainPoint[B]-ComparePoint[B])));
		//距離
		if(Distan<10){
			//System.out.println("Distan="+Distan);
			return true;
		}
		
		return false;
	}
	public static void Output() throws IOException{
		
		BufferedImage outputimage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<imageRGB.length;i++){
			for(int j=0;j<imageRGB[i].length;j++){
				int R=0;
				int G=0;
				int B=0;
				if(imageRGB[i][j][3]==1){
					R=255;
					G=255;
					B=255;
				}
				if(imageRGB[i][j][3]>32){
					imageRGB[i][j][3]=imageRGB[i][j][3]-32;
					R=R+125;
				}
				if(imageRGB[i][j][3]>16){
					imageRGB[i][j][3]=imageRGB[i][j][3]-16;
					G=G+125;
				}
				if(imageRGB[i][j][3]>8){
					imageRGB[i][j][3]=imageRGB[i][j][3]-8;
					B=B+125;
				}
				if(imageRGB[i][j][3]>4){
					imageRGB[i][j][3]=imageRGB[i][j][3]-4;
					R=R+125;
				}
				if(imageRGB[i][j][3]>2){
					imageRGB[i][j][3]=imageRGB[i][j][3]-2;
					G=G+125;
				}
				if(imageRGB[i][j][3]>1){
					imageRGB[i][j][3]=imageRGB[i][j][3]-1;
					B=B+125;
				}

				outputimage.setRGB(i, j,ARGB(255,R,G,B));
				/*if(imageRGB[i][j][3]==10){
					outputimage.setRGB(i, j,ARGB(255,255,0,255));
				}else{
					outputimage.setRGB(i, j,ARGB(255,0,0,0));
				}*/
				
			}
		}

		ImageIO.write(outputimage,"jpeg",new File("./Output/outputimage.jpg"));
	}

	public static int ARGB(int A, int R, int G, int B) {
		  return (A << 24) | ((R << 16) & (255 << 16)) | ((G << 8) & (255 << 8)) | (B & 255);
	}
}
