package csd230.lab1.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Publication REST API", description="JSON API for managing publications")
@RestController
@RequestMapping("/api/rest/publications")
@CrossOrigin(origins = "*")
public class PublicationRestController {
        // This controller is intentionally left empty as a placeholder for future implementation.
        // It can be implemented similarly to BookRestController and DrumKitRestController when needed.

}
