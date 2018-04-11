package com.vv.test.automation.utilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
public class CaptureScreenShot {

	List<ArrayList<BufferedImage>> imageslist;

	/**
	 * @author Cigniti
	 * @description: It takes complete page screenshot.
	 * @param driver
	 * @param ImgPath
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void seleniumCaptureBrowserScreenShot(EventFiringWebDriver driver,String ImgPath) throws InterruptedException, IOException {
		int height_covered=0,width_covered=0,pageHeightLeft=0,pageWidthLeft=0;
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//scrolling to top before taking the full screenshot.
		js.executeScript("window.scrollTo(0,0)");

		Long pageheight1=(Long)js.executeScript("return window.innerHeight");
		Long maxPageHeight1=(Long)js.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight," +
				"document.documentElement.clientHeight, window.innerHeight)");
		float sections=(float)maxPageHeight1/pageheight1;
		int numberOfRows=(int)Math.ceil(sections);						//denotes the number of rows.

		imageslist=new ArrayList<ArrayList<BufferedImage>>(numberOfRows);

		Long pageWidth1=(Long)js.executeScript("return window.innerWidth");
		Long maxPageWidth1=(Long)js.executeScript("return Math.max(document.documentElement.scrollWidth, document.body.scrollWidth," +
				"document.documentElement.clientWidth, window.innerWidth)");
		sections=(float)maxPageWidth1/pageWidth1;
		int numberOfCols=(int)Math.ceil(sections);							//denotes the number of columns.

		int pageheight=pageheight1.intValue();
		int maxPageHeight=maxPageHeight1.intValue();
		int pageWidth=pageWidth1.intValue();
		int maxPageWidth=maxPageWidth1.intValue();

		System.out.println("HTML Page height="+pageheight+"   Max Page Height="+maxPageHeight+"   Rows="+numberOfRows);
		System.out.println("HTML Page Width="+pageWidth+"   Max Page Width="+maxPageWidth+"   Columns="+numberOfCols);

		for (int row=0; row<numberOfRows ; row++) {

			ArrayList<BufferedImage> rowArr=new ArrayList<BufferedImage>(numberOfCols);
			imageslist.add(rowArr);
			width_covered=0;
			pageHeightLeft=maxPageHeight-height_covered;

			for(int col=0; col<numberOfCols;col++){
				pageWidthLeft=maxPageWidth-width_covered;
				takeScreenshot(driver,row,col,ImgPath);
				if((pageHeightLeft < pageheight) && (pageWidthLeft < pageWidth )){
					System.out.println("Page Width and Height both are at extreme!");
				}
				else if(pageHeightLeft < pageheight ){
					/*Crops the Image*/
					BufferedImage lastScreenShot=rowArr.get(rowArr.size()-1);
					int lastScreenShotHeight=lastScreenShot.getHeight();
					double pagePercent=((double)(pageheight-pageHeightLeft)/pageheight)*100;
					double imageToBeCropped=Math.round(((double)(pagePercent/100))*lastScreenShotHeight);
					BufferedImage subImage=lastScreenShot.getSubimage(0, (int)imageToBeCropped, lastScreenShot.getWidth(), (int) (lastScreenShotHeight-imageToBeCropped));
					rowArr.set(rowArr.size()-1, subImage);

				}
				else if(pageWidthLeft < pageWidth ){
					BufferedImage lastScreenShot=rowArr.get(rowArr.size()-1);
					int lastScreenShotWidth=lastScreenShot.getWidth();
					double pagePercent=((double)(pageWidth-pageWidthLeft)/pageWidth)*100;
					double imageToBeCropped=Math.round(((double)(pagePercent/100))*lastScreenShotWidth);
					BufferedImage subImage=lastScreenShot.getSubimage((int)imageToBeCropped, 0, (int) (lastScreenShotWidth-imageToBeCropped),lastScreenShot.getHeight());
					rowArr.set(rowArr.size()-1, subImage);

				}else{
					if(maxPageWidth==pageWidth)	{	//No action on width has to be taken.
						break;
					}
					js.executeScript("window.scrollBy("+pageWidth+",0)");
					width_covered=width_covered+pageWidth;
				}
				Thread.sleep(300);
			}
			height_covered=height_covered+pageheight;
			js.executeScript("window.scrollTo(0,"+height_covered+")");			
		}
		try {
			joinImages(ImgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Cigniti
	 * @param driver
	 * @param row
	 * @param col
	 * @param ImgPath
	 */
	private void takeScreenshot(EventFiringWebDriver driver, int row, int col,String ImgPath) {
		File screenshot = null;
		if(ConfigManager.getProperties().getProperty("seleniumGrid").trim().equalsIgnoreCase("true")){
			//org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(driver.getWrappedDriver());
			try {
				screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			} catch (WebDriverException e) {
				e.printStackTrace();
			}
		}
		else if(ConfigManager.getProperties().getProperty("cloudExecution").trim().equalsIgnoreCase("true")){
			//org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(driver.getWrappedDriver());
			try {
				screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			} catch (WebDriverException e) {
				e.printStackTrace();
			}
		}
		else if(driver.getWrappedDriver().getClass().toString().toLowerCase().contains("chromedriver")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("safari")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("firefox")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("edge")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("Microsoft")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("opera")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("PhantomJS")
				||driver.getWrappedDriver().getClass().toString().toLowerCase().contains("remotewebdriver")){
			screenshot = ((TakesScreenshot) driver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
		}
		if(screenshot!=null)
			try {
				BufferedImage image=ImageIO.read(screenshot);
				imageslist.get(row).add(image);
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}

	private void joinImages(String ImgPath) throws IOException{
		int resultImageHeight=0, resultImageWidth=0;
		for(ArrayList<BufferedImage> arr:imageslist){
			BufferedImage image=arr.get(0);
			resultImageHeight=resultImageHeight+image.getHeight();
		}
		ArrayList<BufferedImage> arrImage=imageslist.get(0); 
		for(BufferedImage image: arrImage){
			resultImageWidth=resultImageWidth+image.getWidth();
		}
		BufferedImage resultImage = new BufferedImage(resultImageWidth,resultImageHeight ,BufferedImage.TYPE_INT_RGB);
		Graphics g = resultImage.createGraphics();

		int imageBelow=0;
		for(ArrayList<BufferedImage> arr:imageslist){
			int imageSide=0;
			for(BufferedImage screenshot : arr){

				g.drawImage(screenshot, imageSide, imageBelow, null);
				imageSide=imageSide+screenshot.getWidth();
			}
			imageBelow=imageBelow+arr.get(0).getHeight();
		}
		ImageIO.write(resultImage, "jpg", new File(ImgPath));
	}

	/**
	 * @author Cigniti
	 * @param driver
	 */
	@SuppressWarnings("unused")
	private void setMaximizeBrowser(WebDriver driver){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("var oldY = document.documentElement.clientHeight;" +
				"window.resizeTo(800, 600);" +
				"var padX = 800 - document.documentElement.clientWidth; var padY = 600 - document.documentElement.clientHeight;" +
				"window.resizeTo(padX, oldY + padY);");
	}
	
	private void takeScreenshot(WebDriver driver, int row, int col,String ImgPath) {
		//WebDriver augmentedDriver = new Augmenter().augment(driver);
		TakesScreenshot driver_secreenshot = (TakesScreenshot) driver;
		File file = driver_secreenshot.getScreenshotAs(OutputType.FILE);
		if (file != null)
			try {
				BufferedImage image = ImageIO.read(file);
				imageslist.get(row).add(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @author Cigniti
	 * @description : This method is used to capture screenshot for android native apps
	 * @param driver
	 * @param ImgPath
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void seleniumCaptureBrowserScreenShot(WebDriver driver,
			String ImgPath) throws InterruptedException, IOException {
		int height_covered = 0, width_covered = 0, pageHeightLeft = 0, pageWidthLeft = 0;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Long pageheight1 = (Long) js.executeScript("return window.innerHeight");
		Long maxPageHeight1 = (Long) js
				.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight,"
						+ "document.documentElement.clientHeight, window.innerHeight)");
		float sections = (float) maxPageHeight1 / pageheight1;
		int numberOfRows = (int) Math.ceil(sections); // denotes the number of
		// rows.

		imageslist = new ArrayList<ArrayList<BufferedImage>>(numberOfRows);

		Long pageWidth1 = (Long) js.executeScript("return window.innerWidth");
		Long maxPageWidth1 = (Long) js
				.executeScript("return Math.max(document.documentElement.scrollWidth, document.body.scrollWidth,"
						+ "document.documentElement.clientWidth, window.innerWidth)");
		sections = (float) maxPageWidth1 / pageWidth1;
		int numberOfCols = (int) Math.ceil(sections); // denotes the number of
		// columns.

		int pageheight = pageheight1.intValue();
		int maxPageHeight = maxPageHeight1.intValue();
		int pageWidth = pageWidth1.intValue();
		int maxPageWidth = maxPageWidth1.intValue();

		System.out.println("HTML Page height=" + pageheight
				+ "   Max Page Height=" + maxPageHeight + "   Rows="
				+ numberOfRows);
		System.out.println("HTML Page Width=" + pageWidth
				+ "   Max Page Width=" + maxPageWidth + "   Columns="
				+ numberOfCols);

		for (int row = 0; row < numberOfRows; row++) {

			ArrayList<BufferedImage> rowArr = new ArrayList<BufferedImage>(
					numberOfCols);
			imageslist.add(rowArr);
			width_covered = 0;
			pageHeightLeft = maxPageHeight - height_covered;

			for (int col = 0; col < numberOfCols; col++) {
				pageWidthLeft = maxPageWidth - width_covered;
				takeScreenshot(driver, row, col, ImgPath);
				System.out.println("\nRow=" + row + "  Col=" + col
						+ "  PageWidthLeft=" + pageWidthLeft
						+ "  PageHeightLeft=" + pageHeightLeft);

				if ((pageHeightLeft < pageheight)
						&& (pageWidthLeft < pageWidth)) {
					System.out
					.println("Page Width and Height both are at extreme!");
				} else if (pageHeightLeft < pageheight) {

					System.out.println("....Page Height needs cropping...");
					/* Crops the Image */
					BufferedImage lastScreenShot = rowArr
							.get(rowArr.size() - 1);
					int lastScreenShotHeight = lastScreenShot.getHeight();
					System.out.println("Last Image height="
							+ lastScreenShotHeight);

					double pagePercent = ((double) (pageheight - pageHeightLeft) / pageheight) * 100;
					System.out.println("Page Remove Percent from top="
							+ pagePercent);

					double imageToBeCropped = Math
							.round(((double) (pagePercent / 100))
									* lastScreenShotHeight);
					System.out
					.println("Height of Image to be cropped from top="
							+ imageToBeCropped);
					System.out.println("Height of resultant image="
							+ (lastScreenShotHeight - imageToBeCropped));

					BufferedImage subImage = lastScreenShot.getSubimage(0,
							(int) imageToBeCropped, lastScreenShot.getWidth(),
							(int) (lastScreenShotHeight - imageToBeCropped));
					// ImageIO.write(subImage, "jpg", new File(ImgPath +
					// row+"_"+col+"_1.jpg"));
					rowArr.set(rowArr.size() - 1, subImage);

				} else if (pageWidthLeft < pageWidth) {

					System.out
					.println("Page Width needs cropping...Size of Images array: "
							+ rowArr.size());
					/* Crops the Image */
					BufferedImage lastScreenShot = rowArr
							.get(rowArr.size() - 1);
					int lastScreenShotWidth = lastScreenShot.getWidth();
					System.out.println("Last Image Width="
							+ lastScreenShotWidth);

					double pagePercent = ((double) (pageWidth - pageWidthLeft) / pageWidth) * 100;
					System.out.println("Percent of UnUseful Page="
							+ pagePercent);

					double imageToBeCropped = Math
							.round(((double) (pagePercent / 100))
									* lastScreenShotWidth);
					System.out
					.println("Width of Image to be cropped from Left="
							+ imageToBeCropped);
					System.out.println("Width of resultant image="
							+ (lastScreenShotWidth - imageToBeCropped));

					BufferedImage subImage = lastScreenShot.getSubimage(
							(int) imageToBeCropped, 0,
							(int) (lastScreenShotWidth - imageToBeCropped),
							lastScreenShot.getHeight());
					// ImageIO.write(subImage, "jpg", new File(ImgPath +
					// row+"_"+col+".jpg"));
					rowArr.set(rowArr.size() - 1, subImage);

				} else {
					if (maxPageWidth == pageWidth) { // No action on width has
						// to be taken.
						break;
					}
					js.executeScript("window.scrollBy(" + pageWidth + ",0)");
					width_covered = width_covered + pageWidth;

					System.out.println("Horizontally Scrolled window by now "
							+ pageWidth + "  pixels. Row=" + row);
				}
				Thread.sleep(1000);
			}

			// js.executeScript("window.scrollBy(0,"+pageheight+")");
			height_covered = height_covered + pageheight;
			js.executeScript("window.scrollTo(0," + height_covered + ")");

			System.out.println("Vertically Scrolled window by now "
					+ height_covered + "  pixels.");
			Thread.sleep(1000);
		}
		try {
			joinImages(ImgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Cigniti
	 * @param ImgPath
	 * @return
	 */
	public boolean saveScreenshot(WebDriver driver, String ImgPath) {
		boolean flag = true;
		try {
			File file = null;
			file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(ImgPath));
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * @author Cigniti
	 * @param driver
	 * @param ImgPath
	 */
	public void saveFullScreenShot(WebDriver driver, String ImgPath) {
		CaptureScreenShot fullscreenshot = new CaptureScreenShot();
		try {
			fullscreenshot.seleniumCaptureBrowserScreenShot(driver, ImgPath);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}