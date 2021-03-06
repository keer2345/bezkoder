package com.keer.spring.controller;

import com.keer.spring.message.ResponseMessage;
import com.keer.spring.model.FileInfo;
import com.keer.spring.service.FilesStorageService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/files")
public class FilesController {
  @Autowired private FilesStorageService storageService;

  @PostMapping(value = "/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);
      message = "Upload the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseMessage(message));
    }
  }

  @GetMapping()
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos =
        storageService
            .loadAll()
            .map(
                path -> {
                  String filename = path.getFileName().toString();
                  String url =
                      MvcUriComponentsBuilder.fromMethodName(
                              FilesController.class, "getFile", path.getFileName().toString())
                          .build()
                          .toString();
                  return new FileInfo(filename, url);
                })
            .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }
}
