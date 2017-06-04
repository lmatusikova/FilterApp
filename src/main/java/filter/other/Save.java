package filter.other;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author Lucia Class for save image file.
 */
public class Save {

	private List<BufferedImage> imageList;
	private String path;
	private List<String> nameList;
	private int index;
	private BufferedImage image;

	private File imageFile;

	/**
	 * Constructor
	 * 
	 * @param index
	 *            index of selected image
	 * @param image
	 *            selected image
	 * @param imageList
	 *            list of given images
	 * @param nameList
	 *            list of given image names
	 * @param path
	 *            path to directory, that contains selected image files
	 */
	public Save(int index, BufferedImage image, List<BufferedImage> imageList, List<String> nameList, String path) {
		this.index = index;
		this.image = image;
		this.imageList = imageList;
		this.nameList = nameList;
		this.path = path;
	}

	/**
	 * Save pre-processed image to the given directory.
	 */
	public void saveImage() {
		imageFile = new File(path + File.separator + nameList.get(index));

		try {
			ImageIO.write(image,
					nameList.get(index).substring(nameList.get(index).length() - 3, nameList.get(index).length()),
					imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save all pre-processed images to the given directory.
	 */
	public void saveImages() {
		int i = 0;

		for (BufferedImage image : imageList) {
			imageFile = new File(path + File.separator + nameList.get(i));
			try {
				ImageIO.write(image, nameList.get(i).substring(nameList.get(i).length() - 3, nameList.get(i).length()),
						imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}
