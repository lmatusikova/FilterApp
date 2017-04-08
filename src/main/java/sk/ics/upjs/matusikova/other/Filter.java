package sk.ics.upjs.matusikova.other;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import sk.ics.upjs.matusikova.filter.EdgeDetection;
import sk.ics.upjs.matusikova.filter.GrayScale;
import sk.ics.upjs.matusikova.filter.Threshold;

public class Filter {

	private int index;
	private List<BufferedImage> imageList;
	private String algorithm;
	private int min;
	private int max;
	private int radius;
	private int scale;
	
	private Threshold threshold;
	private EdgeDetection edge;
	private GrayScale gray;
	
	private BufferedImage newImage = null;
	private List<BufferedImage> newImages;
	
	public Filter(int index, List<BufferedImage> imageList, String algorithm, int min, int max, int radius, int scale) {
		this.index = index;
		this.imageList = imageList;
		this.algorithm = algorithm;
		this.min = min;
		this.max = max;
		this.radius = radius;
		this.scale = scale;
		
		threshold = new Threshold();
	}
	
	public Filter(int index, List<BufferedImage> imageList) {
		this.index = index;
		this.imageList = imageList;
		gray = new GrayScale();
	}
	
	public Filter(int index, List<BufferedImage> imageList, int daco) {
		this.index = index;
		this.imageList = imageList;
		edge = new EdgeDetection();
	}
	
	public BufferedImage grayscaleFilterImage() {
		newImage = gray.grayScale(imageList.get(index));
		return newImage;
	}
	
	public List<BufferedImage> grayscaleFilterImages() {
		newImages = new ArrayList<BufferedImage>();
		
		for (BufferedImage image : imageList) {
            newImage = gray.grayScale(image);
            newImages.add(newImage);
        }
		
		return newImages;
	}
	
	public BufferedImage thresholdFilterImage() {
		if("Compute otsu".equals(algorithm)) {
			newImage = threshold.computeOtsu(imageList.get(index), min, max);
        }
        
        else if("Compute entropy".equals(algorithm)) {
        	newImage= threshold.computeEntropy(imageList.get(index), min, max);
        }
        
        else if("Local Gaussian".equals(algorithm)) {
        	newImage = threshold.localGaussian(imageList.get(index), radius, scale);
        }
        
        else if("Local Sauvola".equals(algorithm)) {
        	newImage = threshold.localSauvola(imageList.get(index), radius, scale);
        }
		
		return newImage;
	}
	
	public List<BufferedImage> thresholdFilterImages() {
		newImages = new ArrayList<BufferedImage>();
		
		if("Compute otsu".equals(algorithm)) {
            for (BufferedImage image : imageList) {
                newImage = threshold.computeOtsu(image, min, max);
                newImages.add(newImage);
            }
         }
        
         else if("Compute entropy".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	newImage = threshold.computeEntropy(image, min, max);
            	newImages.add(newImage);
            }
         }
         
         else if("Local Gaussian".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	 newImage = threshold.localGaussian(image, radius, scale);
            	 newImages.add(newImage);
            }
         }
         
         else if("Local Sauvola".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	 newImage = threshold.localSauvola(image, radius, scale);
            	 newImages.add(newImage);
            }
        }
		
		return newImages;
	}
	
	public BufferedImage edgeFilterImage() {
		return newImage;
	}
	
	public List<BufferedImage> edgeFilterImages() {
		newImages = new ArrayList<BufferedImage>();
		
		return newImages;
	}
	
	
}
