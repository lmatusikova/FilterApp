package sk.ics.upjs.matusikova.other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Save {
	
	private List<BufferedImage> imageList;
	private String path;
	private List<String> nameList;
	private int index;
	private BufferedImage image;
	
	private File imageFile;
	
	public Save(int index, BufferedImage image, List<BufferedImage> imageList, List<String> nameList, String path) {
		this.index = index;
		this.image = image;
		this.imageList = imageList;
		this.nameList = nameList;
		this.path = path;
	}
	
	public void saveImage() {
		imageFile = new File(path + File.separator + nameList.get(index));
		
		try {
			ImageIO.write(image, nameList.get(index).substring(nameList.get(index).length() - 3, nameList.get(index).length()), imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveImages() {
		int i = 0;
		
		for(BufferedImage image : imageList) {
			imageFile = new File(path+File.separator+nameList.get(i));
			try {
				ImageIO.write(image, nameList.get(i).substring(nameList.get(i).length() - 3, nameList.get(i).length()), imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}	
			i++;
		}
	}
}
