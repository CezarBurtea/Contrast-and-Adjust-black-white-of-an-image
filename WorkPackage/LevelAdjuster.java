package WorkPackage;



import java.awt.image.BufferedImage;

// Clasa care efectueaza ajustarile specifice nivelului
public class LevelAdjuster extends BaseImageProcessor {
		
	 BufferedImage image;
	
	 // constructor fara parametru
	public LevelAdjuster() {
	
	}
	
	// constructor cu parametru
	public LevelAdjuster(BufferedImage image) {
		super();
		this.image = image;
	}


	// getter
	public BufferedImage getImage() {
		return image;
	}


	// setter
	public void setImage(BufferedImage image) {
		this.image = image; 
	}





}