package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class ImageUtils {

	public static Image loadImage(String fileName) throws IOException {
		String extension = FilenameUtils.getExtension(fileName);

		switch (extension) {
		case "ppm": {

			try {
				return convertedToBMP(fileName);

			} catch (Exception e) {

				e.printStackTrace();
			}

			return null;

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