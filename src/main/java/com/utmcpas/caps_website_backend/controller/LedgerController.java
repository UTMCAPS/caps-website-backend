package com.utmcpas.caps_website_backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.utmcpas.caps_website_backend.model.dto.LedgerDTO;
import com.utmcpas.caps_website_backend.model.entity.Ledger;
import com.utmcpas.caps_website_backend.service.LedgerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/ledgers")
@Slf4j
public class LedgerController {

    @Autowired
    private LedgerService legerService;

    @PostMapping("/upload-ledger")
    public boolean uploadInfo(@RequestPart("file") MultipartFile file, @RequestPart("ledger") LedgerDTO ledgerDTO) {

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path directoryPath = Paths.get("src/main/resources/invoice");
        Path filePath = directoryPath.resolve(fileName);
        try {

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Files.write(filePath, file.getBytes());

            Ledger ledger = Ledger.builder()
                    .item(ledgerDTO.getItem())
                    .amount(ledgerDTO.getAmount())
                    .expenseDate(ledgerDTO.getExpenseDate())
                    .invoiceUrl(fileName)
                    .notes(ledgerDTO.getNotes())
                    .build();

            legerService.insertInfo(ledger);

            return true;
        } catch (IOException e) {
            log.info("Upload ledger unseccuss");
            return false;
        }
    }

    @PostMapping("/get-all")
    public List<Ledger> fetchAllLedger() {
        return legerService.findAll();
    }

    @GetMapping("/invoice/{fileName}")
    public ResponseEntity<Resource> getInvoice(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("src/main/resources/invoice").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String encodedFileName = java.net.URLEncoder.encode(resource.getFilename(), "UTF-8").replaceAll("\\+", "%20");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // You can change to the appropriate image type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFileName + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/del-ledger/{ledgerID}")
    public void deleteLedger(@RequestParam Integer ledgerID) {
        legerService.deleteLedgerById(ledgerID);
    }
    
}
