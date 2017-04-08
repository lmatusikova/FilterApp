package sk.ics.upjs.matusikova.filter;

import java.awt.image.BufferedImage;

import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.PointFilter;

public class GrayScale {

	private BufferedImage filteredImage;
	
	public GrayScale() {
		
	}
	
	public BufferedImage grayScale(BufferedImage image) {
		PointFilter gray = new GrayscaleFilter();
		filteredImage = gray.filter(image, filteredImage);
		
		return filteredImage;
	}
	
	
}
