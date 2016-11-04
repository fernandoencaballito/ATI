package border_detectors.active_contours;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.sun.jdi.connect.Connector.SelectedArgument;
import com.sun.tools.javadoc.ToolOption;

import masks.GaussianMask;
import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class ActiveContours {

	private static final Color L_IN_COLOR=Color.red;
	private static final Color L_OUT_COLOR=Color.BLUE;
	private static int[][] neighbours_directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
	private ImagePanel original, modified;
	private Phi_value[][] phi_values;
	private Vector3D background_average, object_average;// vector3d(r,g,b);
	int rows, cols;

	//variables segund ciclo
	double [][] gaussianOfPhi;
	GaussianMask gaussianFilterMask = new GaussianMask(3, 0,1);
	
	
	
	public Phi_value[][] getPhi_values() {
		return phi_values;
	}

	public ActiveContours(ImagePanel original, ImagePanel modified) {
		this.original = original;
		this.modified = modified;
	}

	public void mark_contour(int times){
		
		firstCycle(times);
	
		secondCycle(times);
		mark_Lin_Lout();
		
//		for(Phi_value[] row: phi_values){
//			System.out.println(Arrays.toString(row));
//			
//		}
	}
	
	public void firstCycle(int times){
		
		// paso 1
		initialise();
		
		genericCycle(times, F_d,Refresh_fd,1);
		
	
		
	}
	
	public void secondCycle(int times){
		genericCycle(times, F_s, Refresh_fs,-1);
	}
	
	
	public void genericCycle(int times,Function fun, FunctionValueRefresh valueRefresh,int switchInPositive) {

		
		for (int time = 0; time < times; time++) {
			// paso 2
			BufferedImage img = modified.getImage();
			int color;
			
			double f_result;
			//compute_Fd_Averages();
			valueRefresh.refresh();
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {

					if (phi_values[row][col] == Phi_value.L_OUT) {
						//color = img.getRGB(col, row);
						f_result = fun.apply(row, col);

						if ((switchInPositive * f_result) > 0) {
							phi_values[row][col] = Phi_value.L_IN;
							setNeighbours(row, col, Phi_value.BACKGROUD, Phi_value.L_OUT);
						}
					}
				}
			}

			// paso 3: pixeles que pasaron a ser interiores
			Phi_value current_phi_value;
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					current_phi_value = phi_values[row][col];

					if (current_phi_value == Phi_value.L_IN && !isLin(row, col)) {
						phi_values[row][col] = Phi_value.OBJECT;
					}

				}
			}

			// paso4: se aplica F a pixeles en Lin

			//compute_Fd_Averages();
			valueRefresh.refresh();
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					current_phi_value = phi_values[row][col];

					if (current_phi_value == Phi_value.L_IN && (fun.apply(row, col) *switchInPositive) < 0) {

						phi_values[row][col] = Phi_value.L_OUT;

						setNeighbours(row, col, Phi_value.OBJECT, Phi_value.L_IN);

					}
				}
			}

			// paso 5: pixeles que se convirtieron en exteriores

			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					current_phi_value = phi_values[row][col];

					if (current_phi_value == Phi_value.L_OUT && !isLout(row, col)) {
						phi_values[row][col] = Phi_value.BACKGROUD;
					}

				}
			}

		}
		
		
		

	}

	// se fija los vecinos de la posicion (current_row, current_col) que tengan
	// el valor esperado y le setea el nuevo valor
	private void setNeighbours(int current_row, int current_col, Phi_value expected_neighbour, Phi_value newValue) {

		int next_row, next_col;
		for (int[] direction : neighbours_directions) {
			next_row = current_row + direction[0];
			next_col = current_col + direction[1];

			if (next_row < 0 || next_col < 0 || next_row >= rows || next_col >= cols)
				continue;

			try{
			if (phi_values[next_row][next_col] == expected_neighbour)
				phi_values[next_row][next_col] = newValue;

			}catch (Exception e){
				System.out.println("Check");
			}
		}

	}

	public void initialise() {
		Rectangle selectionRectangle = original.getSelectionRectangle();
		rows = modified.getImageHeight();
		cols = modified.getImageWidth();
		phi_values = new Phi_value[rows][cols];

		initialise_phi_values(selectionRectangle);

	
	}

	
	
	//Importante de usar antes de llamar Fd
	//se calculan los promedios de fondo y de objecto
	private void compute_Fd_Averages(){
		compute_background_average();
		compute_object_average();

	}
	private double f_d(int row, int col) {
		return f_d(modified.getImage().getRGB(col, row));

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

		BufferedImage img = modified.getImage();
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
		
		if(total_elements==0)
			return new Vector3D(0, 0, 0);
		
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

	private boolean isLin(int row, int col) {
		return isL(row, col, 1);
	}

	private boolean isLout(int row, int col) {
		return isL(row, col, -1);
	}

	private boolean isL(int row, int col, int positive) {
		Phi_value current_value = phi_values[row][col];

		if ((positive * current_value.getValue()) > 0)
			return false;

		boolean positiveNeighbour = false;

		int next_row, next_col;
		Phi_value neighbourValue;
		for (int[] neighbourDirection : neighbours_directions) {

			next_row = row + neighbourDirection[0];
			next_col = col + neighbourDirection[1];
			
			if (next_row < 0 || next_col < 0 || next_row >= rows || next_col >= cols)
				continue;

			neighbourValue = phi_values[next_row][next_col];
			if ((positive * neighbourValue.getValue()) > 0) {
				positiveNeighbour = true;
				break;
			}

		}

		return positiveNeighbour;

	}

	//modifica la imagen para mostrar Lin y Lout	
	private void mark_Lin_Lout(){
		
		Phi_value current_phi_value;
		
		BufferedImage modifiedBuffer=modified.getImage();
		Graphics2D l_in_graphics=modifiedBuffer.createGraphics();
		l_in_graphics.setPaint(L_IN_COLOR);
		
		
		Graphics2D l_out_graphics=modifiedBuffer.createGraphics();
		l_out_graphics.setPaint(L_OUT_COLOR);
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				current_phi_value = phi_values[row][col];
				if(current_phi_value==Phi_value.L_IN){
				 l_in_graphics.fillRect(col, row, 1, 1);
				}else if(current_phi_value== Phi_value.L_OUT){
					l_out_graphics.fillRect(col, row, 1, 1);
					
				}
				
				
			}
		}

		modified.repaint();
	}
	
	
	public interface Function{
		public  double apply( int row,int col);
		
	}
	
	public interface FunctionValueRefresh {
		public void refresh();
	}
	
	Function F_d=(int row, int col) ->f_d(	row	, col);
	FunctionValueRefresh Refresh_fd=()->compute_Fd_Averages();
	
	Function F_s=(int row,int col)-> f_s(row,col);
	FunctionValueRefresh Refresh_fs=()->computeGaussian();
	
	
	private double f_s(int row, int col) {
		//return f_s(original.getImage().getRGB(col, row));

		
		return gaussianOfPhi[row][col];
	}
	
	private void computeGaussian(){
		
		gaussianOfPhi =new double[rows][cols];
		
		for(int r=0;r<rows;r++){
			for(int c=0;c<cols;c++){
				gaussianOfPhi[r][c]=(double)(phi_values[r][c]).getValue();
			}
		}
		
		
		gaussianOfPhi = gaussianFilterMask.filterImage(gaussianOfPhi);
		
		
	}

	
		
}
