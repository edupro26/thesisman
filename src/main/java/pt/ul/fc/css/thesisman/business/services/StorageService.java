package pt.ul.fc.css.thesisman.business.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pt.ul.fc.css.thesisman.business.exceptions.ApplicationException;

@Service
public class StorageService {

  private final Path rootLocation;

  public StorageService() {
    File newFolder = new File("files");
    if (!newFolder.exists()) {
      newFolder.mkdir();
    }
    this.rootLocation = Paths.get("files");
  }

  public void store(MultipartFile file) throws ApplicationException {
    try {
      if (file.isEmpty()) {
        throw new ApplicationException("Failed to store empty file.");
      }
      Path destinationFile =
          this.rootLocation
              .resolve(Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
              .normalize()
              .toAbsolutePath();
      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new ApplicationException("Cannot store file outside current directory.");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new ApplicationException("Failed to store file.");
    }
  }
}
