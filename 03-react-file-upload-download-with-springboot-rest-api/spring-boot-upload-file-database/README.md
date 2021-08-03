# Spring Boot File upload example with Multipart File

In this tutorial, I will show you how to upload and download files with a Spring Boot Rest APIs
to/from a static folder. We also use Spring Web MultipartFile interface to handle
HTTP [multi-part](https://www.w3.org/Protocols/rfc1341/7_2_Multipart.html)
requests.

## Spring Boot Rest APIs for uploading Files

Our Spring Boot Application will provide APIs for:

- uploading File to a static folder in the Server
- downloading File from server with the link
- getting list of Files’ information (file name & url)

Methods    |Urls    |Actions
----|----|----
POST    | /upload    |upload a File
GET    | /files    |get List of Files (name & url)
GET    | /files/[filename]    |download a File

This is the static folder that stores all uploaded files:

![](img/img.png)

If you want to store files in database like this:

![](img/img_1.png)

## Setup Spring Boot project

- pom.xml
- Application.java
- application.yml

## Create Service for File Storage

- _service/FilesStorageService.java_
- _service/FilesStorageServiceImpl.java_

## Define Data Models

- _model/FileInfo.java_

## Define Response Message

- _message/ResponseMessage.java_

## Create Controller for upload & download Files

- _controller/FilesController.java_

## Configure Multipart File for Servlet

_application.yml_

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 1MB
      max-request-size: 1MB
```

## Handle File Upload Exception

This is where we handle the case in that a request exceeds Max Upload Size. The system will throw
`MaxUploadSizeExceededException` and we’re gonna use @ControllerAdvice with
`@ExceptionHandlerannotation` for handling the exceptions.

- _exception/FileUploadExceptionAdvice.java_

## Initialize Storage

- _Application.java_

## Run & Test

Run Spring Boot application with command:

```shell
mvn spring-boot:run
```

Refresh the project directory and you will see uploads folder inside it.

Let’s use **Postman** to make some requests.

– Upload some files:

![](img/img_2.png)

- Upload a file with size larger than max file size (1M):

![](img/img_3.png)

– Retrieve list of Files’ information:

![](img/img_4.png)


– Now you can download any file from one of the paths above.
For example: http://localhost:8080/files/aaa.jpeg.