package kr.kohub.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import kr.kohub.type.FilePathType;
import lombok.extern.slf4j.Slf4j;

// 파일 디렉토리 구조 결정, filePathType결정
// 파일 이미지 썸네일 저장
// 파일 이미지 타입 필요
// 업로드 saveFileName 고려
// 다운로드 파일명 UUID 고려 필요.
// 이미지 뿐만 아니라 다른 파일도 존재

// 파일업로드,다운로드시 트랜잭션 처리할 수 있도록 예외처리를 안 함
@Slf4j
@Component
@PropertySource(value = "classpath:application.properties")
public class FileUtil {
  @Autowired
  private Environment env;

  private static final String FILE_EXTENSION_PATTERN = "(.[a-zA-Z]+)$";
  // private static final String FILE_NAME_PATTERN = "([a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣.]+)(.[a-zA-Z]+)";

  public static String uploadThumbImg() {
    // 썸네일 업로드 구현 필요
    return "";
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

    // saveFileName 결정
    return uploadPath + fileName;
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
