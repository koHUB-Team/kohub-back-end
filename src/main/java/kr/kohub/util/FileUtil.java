package kr.kohub.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import kr.kohub.type.FilePathType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource(value = "classpath:application.properties")
public class FileUtil {
  @Autowired
  private Environment env;

  private static final String FILE_EXTENSION_PATTERN = "(.[a-zA-Z]+)$";
  // private static final String FILE_NAME_PATTERN = "([a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣.]+)(.[a-zA-Z]+)";

  public String uploadThumbImg(String loadFileName, int zoom, FilePathType filePathType) {
    // 파일 경로
    String rootPath = getRootPath();
    String uploadPath = getUploadFilePath(filePathType);

    // 디렉토리 체크
    File dir = new File(rootPath + uploadPath);
    checkDirectory(dir);

    String loadFilePath = rootPath + uploadPath + loadFileName;
    String saveFileName = getUUIDFileName(".jpg");
    String saveFilePath = rootPath + uploadPath + saveFileName;
    System.out.println("loadFilePath : " + loadFilePath);
    System.out.println("saveFilePath : " + saveFilePath);

    File thumbFile = new File(saveFilePath); // 썸네일 이미지
    // RenderedOp renderedOp = JAI.create("fileload", loadFilePath); // 원본 이미지
    // BufferedImage bufferedImage = renderedOp.getAsBufferedImage();

    try {
      BufferedImage bufferedImage = ImageIO.read(new File(loadFilePath));

      if (zoom <= 0) {// 축소비율 설정
        zoom = 1;
      }
      int width = bufferedImage.getWidth() / zoom;
      int height = bufferedImage.getHeight() / zoom;

      BufferedImage thumbImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics2d = thumbImg.createGraphics();
      graphics2d.drawImage(bufferedImage, 0, 0, width, height, null);
      ImageIO.write(thumbImg, "jpg", thumbFile);

    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException();
    }

    return saveFileName;
  }

  public String upload(MultipartFile file, FilePathType filePathType) {
    String rootPath = getRootPath();
    String uploadPath = getUploadFilePath(filePathType);

    // 디렉토리 체크
    File dir = new File(rootPath + uploadPath);
    checkDirectory(dir);

    // 중복파일 이름 해결
    String fileName = getUUIDFileName(file.getOriginalFilename());
    String filePath = rootPath + uploadPath + fileName;

    // 파일 저장
    try (FileOutputStream fos = new FileOutputStream(filePath);
        InputStream is = file.getInputStream();) {
      int readCount = 0;
      byte[] buffer = new byte[1024];
      while ((readCount = is.read(buffer)) != -1) {
        fos.write(buffer, 0, readCount);
      }

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }

    return fileName;
  }

  public void delete(String fileName, FilePathType filePathType) {
    String rootPath = getRootPath();
    String uploadPath = getUploadFilePath(filePathType);
    String filePath = rootPath + uploadPath + fileName;

    File file = new File(filePath);
    if (!file.exists()) {
      throw new RuntimeException("File to delete Not Found Exception.");
    }

    if (!file.delete()) {
      throw new RuntimeException("File deletion Error Exception.");
    }
  }

  private static void checkDirectory(File dir) {
    if (!dir.isDirectory()) {
      dir.mkdirs();
    }
  }

  private static String getUUIDFileName(String fileName) {
    // UUID를 이용해서 랜덤한 값을 파일이름에 붙여서 중복이름 해결
    UUID uuid = UUID.randomUUID();
    fileName = uuid + "." + getFileExtension(fileName);

    return fileName;
  }

  public static String getFileExtension(String fileName) {
    Pattern pattern = Pattern.compile(FILE_EXTENSION_PATTERN);
    Matcher matcher = pattern.matcher(fileName);

    matcher.find();
    return matcher.group().replace(".", "");
  }

  // 파일 경로
  public String getRootPath() {
    return byEnvironment("file.path.root.window");
  }

  private String getUploadFilePath(FilePathType filePathType) {
    return byEnvironment("file.path." + filePathType.name().toLowerCase());
  }

  private String byEnvironment(String before) {
    String key = before + "." + env.getProperty("environment");
    return env.getProperty(key);
  }

  // private

  // public static boolean download(FileInfo fileInfo, HttpServletResponse response) {
  // boolean didDownload = false;
  //
  // String fileName = fileInfo.getFileName();
  // String saveFileName = ROOT_DIR_FOR_WINDOW + fileInfo.getSaveFileName();
  // String contentType = fileInfo.getContentType();
  //
  // try (FileInputStream fis = new FileInputStream(saveFileName);
  // OutputStream out = response.getOutputStream();) {
  //
  // int readCount = 0;
  // byte[] buffer = new byte[1024];
  // while ((readCount = fis.read(buffer)) != -1) {
  // out.write(buffer, 0, readCount);
  // }
  //
  // response.setHeader("Content-Length", "" + readCount);
  // response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
  // response.setHeader("Content-Transfer-Encoding", "binary");
  // response.setHeader("Content-Type", contentType);
  // response.setHeader("Pragma", "no-cache;");
  // response.setHeader("Expires", "-1;");
  //
  // didDownload = true;
  // } catch (Exception e) {
  // log.error(e.getMessage());
  // }
  //
  // return didDownload;
  // }
}
