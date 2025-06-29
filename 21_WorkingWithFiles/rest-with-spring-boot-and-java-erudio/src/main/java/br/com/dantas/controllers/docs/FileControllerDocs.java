package br.com.dantas.controllers.docs;

import br.com.dantas.data.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileControllerDocs {

    @Operation(summary = "Upload a File",
            description = "Upload a file to the server",
            tags = {"Files"},
            responses = {
                    @ApiResponse(
                            description = "File uploaded successfully",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UploadFileResponseDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    UploadFileResponseDTO uploadFile(MultipartFile file);

    @Operation(summary = "Upload Multiple Files",
            description = "Upload multiple files to the server",
            tags = {"Files"},
            responses = {
                    @ApiResponse(
                            description = "Files uploaded successfully",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UploadFileResponseDTO.class)))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] files);

    @Operation(summary = "Download a File",
            description = "Download a file from the server by its name",
            tags = {"Files"},
            responses = {
                    @ApiResponse(
                            description = "File downloaded successfully",
                            responseCode = "200",
                            content = @Content(mediaType = "application/octet-stream")
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<Resource> downloadFile(String fileName,
                                          HttpServletRequest request);
}