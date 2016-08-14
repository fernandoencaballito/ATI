package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.DCRAWOperation;
import org.im4java.core.DcrawCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class ImageUtils {
	private static final String auxFileName = "./aux.bmp";

	// devuelve la imagen en el formato que lo requiere la ventana de swing
	// width y height solo se usan para formato RAW
	public static Image loadImage(String fileName, int width, int height) throws IOException, InterruptedException {
		String extension = FilenameUtils.getExtension(fileName);
		extension = extension.toLowerCase();
		switch (extension) {
		case "pgm":
		case "ppm": {

			try {
				return convertedToBMP2(fileName);

			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

		}
		case "raw": {
			try {
				return convertFromRAW2(fileName, width, height);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		default: {
			InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
			if (stream == null) {
				return ImageIO.read(new File(fileName));
			} else {
				return ImageIO.read(stream);
			}
		}
		}

	}
	private static Image convertFromRAW2(String fileName, int width, int height) throws FileNotFoundException, IOException{
		
		byte[] pixels=IOUtils.toByteArray(new FileInputStream(fileName));
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		DataBufferByte buffer=(DataBufferByte)image.getRaster().getDataBuffer();
	    byte[] imgData = buffer.getData();
	    System.arraycopy(pixels, 0, imgData, 0, pixels.length); 
		
		
		return image;
		
	}
	
	
	//usa dcraw, falla(no reconoce el formato)
	private static Image convertFromRAW(String fileName, int width, int height)
			throws IOException, InterruptedException, IM4JavaException {
		// se convierte a bmp y se lo guarda en un archivo auxiliar
		DcrawCmd cmd = new DcrawCmd();
		DCRAWOperation op = new DCRAWOperation();
		op.addImage(fileName);
		op.addImage(auxFileName);
	

		cmd.run(op);
		BufferedImage ans = ImageIO.read(new File(auxFileName));
		File aux = new File(auxFileName);
		aux.delete();
		return ans;
	}

	// usando im4java
	public static BufferedImage convertedToBMP2(String fileName)
			throws IOException, InterruptedException, IM4JavaException {

		// se convierte a bmp y se lo guarda en un archivo auxiliar
		ConvertCmd cmd = new ConvertCmd();
		IMOperation op = new IMOperation();
		op.addImage(fileName);

		op.addImage(auxFileName);

		cmd.run(op);
		BufferedImage ans = ImageIO.read(new File(auxFileName));
		File aux = new File(auxFileName);
		aux.delete();
		return ans;

	}

	// usando jmagick, falla en ejecución(no encuentra una clase)
	public static BufferedImage convertedToBMP(String fileName) throws MagickException, IOException {
		// primero se abre con MagicImage
		ImageInfo info = new ImageInfo(fileName);
		MagickImage magick_converter = new MagickImage(info);

		// Se convierte a bmp y se graba en un archivo auxiliar
		String outputfileAux = "auxiliar_image.bmp"; // Output
		magick_converter.setFileName(outputfileAux); // output file format
		magick_converter.writeImage(info); // conversion

		// se lee del archivo auxiliar
		InputStream stream = ClassLoader.getSystemResourceAsStream(outputfileAux);

		// se retorna la imagen cargada en un Image
		return ImageIO.read(stream);

	}

	// tp viejo de lucas

	public static Image drawString(Image img, String text, Color color) {
		BufferedImage result = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) result.getGraphics();
		g.drawImage(img, 0, 0, null);

		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		g.setFont(font);
		g.setColor(color);
		Rectangle2D r = font.getStringBounds(text, g.getFontRenderContext());
		g.drawString(text, img.getWidth(null) - (int) r.getWidth() - 2, img.getHeight(null) - 2);
		return result;
	}

	public static Image overlap(Image image1, Image image2) {
		BufferedImage result = new BufferedImage(image1.getWidth(null), image1.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) result.getGraphics();
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, 0, 0, null);
		return result;
	}
}