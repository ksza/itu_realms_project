package dk.itu.realms.web;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

@Named("appUtils")
@Scope("application")
public class AppUtils {

	private static final int THUMB_SIZE = 48;
	
	private String picturesRepo;
	
	@PostConstruct
	public void loadPicturesRepo() {
		File root = new File("eab_pictures");
		if(! root.exists()) {
			root.mkdir();
		}
		
		picturesRepo = root.getAbsolutePath();
	}
	
	public String getPicturesRepo() {
		return picturesRepo;
	}
	
	@SuppressWarnings("deprecation")
	public Blob createProfileThumb(final UploadedFile file) {
		String saveFileName = getPicturesRepo() + File.separator + UUID.randomUUID() + "." + "png";
		
		BufferedImage img = new BufferedImage(THUMB_SIZE, THUMB_SIZE, BufferedImage.TYPE_INT_RGB);
		try {
			final File thumbImgFile = new File(saveFileName);
			
			img.createGraphics().drawImage(ImageIO.read(file.getInputstream()).getScaledInstance(THUMB_SIZE, THUMB_SIZE, Image.SCALE_SMOOTH), 0, 0, null);
			ImageIO.write(img, "png", thumbImgFile);
			
			/* save to DB */
			final InputStream is = new FileInputStream(thumbImgFile);
			final Blob resultBlob = Hibernate.createBlob(is);
			is.close();
			
			/* remove from disk */
			thumbImgFile.delete();
			
			return resultBlob;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
}
