package filter.filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import com.jhlabs.image.EdgeFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.LaplaceFilter;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;

public class Filter {

	private int index;
	private List<BufferedImage> imageList;
	private String algorithm;
	EdgeFilter edge;
	GrayscaleFilter gray;
	private BufferedImage newImage;
	private BufferedImage filteredImage;
	private List<BufferedImage> newImages = new ArrayList<BufferedImage>();
	
	//constructor for threshold filter
	public Filter(int index, List<BufferedImage> imageList, String algorithm, int filter) {
		this.index = index;
		this.imageList = imageList;
		this.algorithm = algorithm;
		if(filter == 1) {
			edge = new EdgeFilter();
		} 
	}
	
	//constructor for grayscale filter
	public Filter(int index, List<BufferedImage> imageList) {
		this.index = index;
		this.imageList = imageList;
		gray = new GrayscaleFilter();
	}
	
	public BufferedImage grayscaleFilterImage() {
		newImage = grayScale(imageList.get(index));
		return newImage;
	}
	
	public List<BufferedImage> grayscaleFilterImages() {
		newImages = new ArrayList<BufferedImage>();
		
		for (BufferedImage image : imageList) {
            newImage = grayScale(image);
            newImages.add(newImage);
        }
		
		return newImages;
	}
	
	public BufferedImage thresholdFilterImage() {
		if("Compute otsu".equals(algorithm)) {
			newImage = computeOtsu(imageList.get(index));
        }
        
        else if("Compute entropy".equals(algorithm)) {
        	newImage= computeEntropy(imageList.get(index));
        }
        
        else if("Local Gaussian".equals(algorithm)) {
        	newImage = localGaussian(imageList.get(index));
        }
        
        else if("Local Sauvola".equals(algorithm)) {
        	newImage = localSauvola(imageList.get(index));
        }
		
		return newImage;
	}
	
	public List<BufferedImage> thresholdFilterImages() {
		newImages = new ArrayList<BufferedImage>();
		
		if("Compute otsu".equals(algorithm)) {
            for (BufferedImage image : imageList) {
                newImage = computeOtsu(image);
                newImages.add(newImage);
            }
         }
        
         else if("Compute entropy".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	newImage = computeEntropy(image);
            	newImages.add(newImage);
            }
         }
         
         else if("Local Gaussian".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	 newImage = localGaussian(image);
            	 newImages.add(newImage);
            }
         }
         
         else if("Local Sauvola".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	 newImage = localSauvola(image);
            	 newImages.add(newImage);
            }
        }
		
		return newImages;
	}
	
	public BufferedImage edgeFilterImage() {
		BufferedImage img = imageList.get(index);
		
		if("Prewitt operator".equals(algorithm)) {
			newImage = prewittFilter(img);
        }
        
        else if("Sobel operator".equals(algorithm)) {
        	newImage= sobelFilter(img);
        }
        
        else if("Roberts operator".equals(algorithm)) {
        	newImage = robertsFilter(img);
        }
        
        else if("Laplace operator".equals(algorithm)) {
        	newImage = laplaceFilter(img);
        }
		
		return newImage;
	}
	
	public List<BufferedImage> edgeFilterImages() {
		newImages = new ArrayList<BufferedImage>();
		
		if("Prewitt operator".equals(algorithm)) {
            for (BufferedImage image : imageList) {
                newImage = prewittFilter(image);
                newImages.add(newImage);
            }
         }
        
         else if("Sobel operator".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	newImage = sobelFilter(image);
            	newImages.add(newImage);
            }
         }
         
         else if("Roberts operator".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	 newImage = robertsFilter(image);
            	 newImages.add(newImage);
            }
         }
         
         else if("Laplace operator".equals(algorithm)) {
            for (BufferedImage image : imageList) {
            	 newImage = laplaceFilter(image);
            	 newImages.add(newImage);
            }
        }
		
		return newImages;
	}
	
	public BufferedImage computeOtsu(BufferedImage image) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        GThresholdImageOps.threshold(in, bin, GThresholdImageOps.computeOtsu(in, 0, 255), true);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage computeEntropy(BufferedImage image) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        GThresholdImageOps.threshold(in, bin, GThresholdImageOps.computeEntropy(in, 0, 255), true);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage localGaussian(BufferedImage image) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        GThresholdImageOps.localGaussian(in, bin, 42, 1.0, true, null, null);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage localSauvola(BufferedImage image) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        GThresholdImageOps.localSauvola(in, bin, 5, 0.30f, true);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage sobelFilter(BufferedImage image) {
		edge.setVEdgeMatrix(EdgeFilter.SOBEL_V);
		edge.setHEdgeMatrix(EdgeFilter.SOBEL_H);
		newImage = edge.filter(image, filteredImage);
		return newImage;
	}
	
	public BufferedImage prewittFilter(BufferedImage image) {
		edge.setVEdgeMatrix(EdgeFilter.PREWITT_V);
		edge.setHEdgeMatrix(EdgeFilter.ROBERTS_H);
		newImage = edge.filter(image, filteredImage);
		return newImage;
	}

	public BufferedImage robertsFilter(BufferedImage image) {
		edge.setVEdgeMatrix(EdgeFilter.ROBERTS_V);
		edge.setHEdgeMatrix(EdgeFilter.ROBERTS_H);
		newImage = edge.filter(image, filteredImage);
		return newImage;
	}
	
	public BufferedImage laplaceFilter(BufferedImage image) {
		LaplaceFilter laplace = new LaplaceFilter();
		newImage = laplace.filter(image, filteredImage);
		return newImage;
	}
	
	public BufferedImage grayScale(BufferedImage image) {
		newImage = gray.filter(image, filteredImage);
		return newImage;
	}
	
	
	
	
}
