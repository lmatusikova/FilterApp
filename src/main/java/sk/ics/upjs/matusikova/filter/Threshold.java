package sk.ics.upjs.matusikova.filter;

import java.awt.image.BufferedImage;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;

public class Threshold {

	public Threshold() {
	}
	
	public BufferedImage computeOtsu(BufferedImage image, int idx1, int idx2) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        GThresholdImageOps.threshold(in, bin, GThresholdImageOps.computeOtsu(in, idx1, idx2), true);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage computeEntropy(BufferedImage image, int idx1, int idx2) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        GThresholdImageOps.threshold(in, bin, GThresholdImageOps.computeEntropy(in, idx1, idx2), true);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage localGaussian(BufferedImage image, int radius, int scale) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
       // GThresholdImageOps.localGaussian(in, bin, radius, scale, true, null, null);
        GThresholdImageOps.localGaussian(in, bin, 42, 1.0, true, null, null);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
	
	public BufferedImage localSauvola(BufferedImage image, int radius, int scale) {
		GrayF32 in = ConvertBufferedImage.convertFromSingle(image, null, GrayF32.class);
        GrayU8 bin = new GrayU8(in.width, in.height);
        // GThresholdImageOps.localSauvola(in, bin, radius, scale, true);
        GThresholdImageOps.localSauvola(in, bin, 5, 0.30f, true);
        BufferedImage bufferedImage = VisualizeBinaryData.renderBinary(bin, false, null);
        
        return bufferedImage; 
	}
}
