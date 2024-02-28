//package dev.jam.accountservice.service.dtos;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "LOCATION-SERVICE")
//public interface LocationService {
//    // Endpoint to get a file by ID
//    @GetMapping("/api/v1/files/{id}")
//    public String getFile(@PathVariable String id) {
//        // Logic to retrieve the file by ID
//        return "Get file with ID: " + id;
//    }
//
//    // Endpoint to upload a file
//    @PostMapping("/files")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        // Logic to handle file upload
//        return "File uploaded successfully: " + file.getOriginalFilename();
//    }
//
//    // Endpoint to update a file by ID
//    @PutMapping("/files/{id}")
//    public String updateFile(@PathVariable String id, @RequestParam("file") MultipartFile file) {
//        // Logic to update the file by ID
//        return "File updated successfully: " + id;
//    }
//
//    // Endpoint to delete a file by ID
//    @DeleteMapping("/files/{id}")
//    public String deleteFile(@PathVariable String id) {
//        // Logic to delete the file by ID
//        return "File deleted successfully: " + id;
//    }
//}