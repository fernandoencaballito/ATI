package border_detectors;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.sun.jdi.connect.Connector.SelectedArgument;
import com.sun.tools.javadoc.ToolOption;

import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class ActiveContours {

	private static int[][] neighbours_directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
	private ImagePanel original, modified;
	private Phi_value[][] phi_values;
	private Vector3D background_average, object_average;// vector3d(r,g,b);
	int rows, cols;

	public Phi_value[][] getPhi_values() {
		return phi_values;
	}

	public ActiveContours(ImagePanel original, ImagePanel modified) {
		this.original = original;
		this.modified = modified;
	}

	public void mark_contour() {

		// paso 1
		initialise();

		// paso 2
		BufferedImage img = original.getImage();
		int color;
		double fd;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				if (phi_values[row][col] == Phi_value.L_OUT) {
					color = img.getRGB(col, row);
					fd = f_d(color);

					if (fd > 0) {
						phi_values[row][col] = Phi_value.L_IN;
						setNeighbours(row, col, Phi_value.BACKGROUD, Phi_value.L_OUT);
					}
				}
			}
		}
		
		//paso 3: pixeles que pasaron a ser interiores
		Phi_value current_phi_value;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				current_phi_value=phi_values[row][col];
				
				if( current_phi_value== Phi_value.L_IN
					&& !isLin(row, col)){
					phi_values[row][col]=Phi_value.OBJECT;
				}
				
			}
		}
		
		//paso4: se aplica Fd a pixeles en Lin

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				current_phi_value=phi_values[row][col];
		
				if(current_phi_value== Phi_value.L_IN && f_d(row,col)<0){
					
					phi_values[row][col]=Phi_value.L_OUT;
					
					setNeighbours(row, col, Phi_value.OBJECT, Phi_value.L_IN);
					
					
				}
			}
		}
		
		
		//paso 5: pixeles que se convirtieron en exteriores
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				current_phi_value=phi_values[row][col];
				
				if( current_phi_value== Phi_value.L_OUT
					&& !isLout(row, col)){
					phi_values[row][col]=Phi_value.BACKGROUD;
				}
				
			}
		}
		
		
		
	}

	// se fija los vecinos de la posicion (current_row, current_col) que tengan
	// el valor esperado y le setea el nuevo valor
	private void setNeighbours(int current_row, int current_col, Phi_value expected_neighbour, Phi_value newValue) {

		int next_row, next_col;
		for(int[] direction: neighbours_directions){
			next_row=current_row+ direction[0];
			next_col=current_col+ direction[1];
			
			if(next_row<0 || next_col<0 || next_row>rows || next_col>cols)
				continue;
			
			if(phi_values[next_row][next_col]==expected_neighbour)
				phi_values[next_row][next_col]=newValue;
			
			
		}
		
	}

	public void initialise() {
		Rectangle selectionRectangle = original.getSelectionRectangle();
		rows = original.getImageHeight();
		cols = original.getImageWidth();
		phi_values = new Phi_value[rows][cols];

		initialise_phi_values(selectionRectangle);

		compute_background_average();
		compute_object_average();

	}

	private double f_d(int row,int col){
		return f_d(original.getImage().getRGB(col, row));
		
		
	}
	
	private double f_d(int colorRGB) {
		Color color = new Color(colorRGB);
		Vector3D color_vec = new Vector3D(color.getRed(), color.getGreen(), color.getBlue());

		return f_d(color_vec);
	}

	private double f_d(Vector3D currentPixel) {
		Vector3D numerator = background_average.subtract(currentPixel);
		Vector3D denominator = object_average.subtract(currentPixel);

		double numerator_norm = numerator.getNorm();
		double denominator_norm = denominator.getNorm();

		return Math.log(numerator_norm / denominator_norm);

	}

	private Vector3D compute_average(Phi_value phi_value) {
		int red_acum = 0;
		int green_acum = 0;
		int blue_acum = 0;

		int total_elements = 0;

		int color_int;
		Color color;

		BufferedImage img = original.getImage();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (phi_values[row][col] == phi_value) {
					color_int = img.getRGB(col, row);
					color = new Color(color_int);

					red_acum += color.getRed();
					green_acum += color.getGreen();
					blue_acum += color.getBlue();

					total_elements++;

				}

			}
		}
		return new Vector3D(red_acum / total_elements, green_acum / total_elements, blue_acum / total_elements);
	}

	private void compute_object_average() {
		// TODO Auto-generated method stub
		object_average = compute_average(Phi_value.OBJECT);

	}

	private void compute_background_average() {
		// TODO Auto-generated method stub

		background_average = compute_average(Phi_value.BACKGROUD);

	}

	public void initialise_phi_values(Rectangle selectionRectangle) {

		int rows = original.getImageHeight();
		int cols = original.getImageWidth();

		for (int row = 0; row < rows; row++) {

			for (int col = 0; col < cols; col++) {
				// [fondo] pixeles fuera del rectangulo
				if (!selectionRectangle.contains(col, row)) {
					phi_values[row][col] = Phi_value.BACKGROUD;
					continue;
				} else {

					// [ Lout]pixeles del borde del rectangulo
					if ((row == selectionRectangle.y && col >= selectionRectangle.x
							&& col < (selectionRectangle.x + selectionRectangle.width))
							|| ((col == selectionRectangle.x
									|| col == (selectionRectangle.x + selectionRectangle.width - 1))
									&& row > selectionRectangle.y
									&& row < (selectionRectangle.y + selectionRectangle.height - 1))
							|| (row == (selectionRectangle.y + selectionRectangle.height - 1)

									&& col >= selectionRectangle.x
									&& col < (selectionRectangle.x + selectionRectangle.width))

					) {

						phi_values[row][col] = Phi_value.L_OUT;

					}
					// [Lin]pixeles adyacentes interiores al borde del
					// rectangulo marcado
					else if ((row == (selectionRectangle.y + 1) && col > selectionRectangle.x
							&& col < (selectionRectangle.x + selectionRectangle.width - 1))
							|| ((col == (selectionRectangle.x + 1)
									|| col == (selectionRectangle.x + selectionRectangle.width - 2))
									&& row > selectionRectangle.y
									&& row < (selectionRectangle.y + selectionRectangle.height - 1))
							|| (row == (selectionRectangle.y + selectionRectangle.height - 2)

									&& col > selectionRectangle.x
									&& col < (selectionRectangle.x + selectionRectangle.width - 1))

					) {
						phi_values[row][col] = Phi_value.L_IN;
					}

					// [objeto] pixeles interiores del rectangulo que no estan
					// en Lin ni en Lout
					else {
						phi_values[row][col] = Phi_value.OBJECT;

					}

				}

			}
		}

	}

	private boolean isLin(int row,int col){
		return isL(row,col,1);
	}
	private boolean isLout(int row,int col){
		return isL(row,col,-1);
	}
	private boolean isL(int row, int col,int positive){
		Phi_value current_value=phi_values[row][col];
		
		if((positive* current_value.getValue())>0)
			return false;
		
		boolean positiveNeighbour=false;

		int next_row, next_col;
		Phi_value neighbourValue;
		for(int[] neighbourDirection: neighbours_directions){
			
			next_row=row+ neighbourDirection[0];
			next_col=col+neighbourDirection[1];
			neighbourValue=phi_values[next_row][next_col];
			if((positive* neighbourValue.getValue() )>0){
				positiveNeighbour=true;
				break;
			}
			
		}
		
		return positiveNeighbour;
		
	}
	
}
