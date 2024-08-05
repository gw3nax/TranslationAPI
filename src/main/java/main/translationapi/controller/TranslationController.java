package main.translationapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import main.translationapi.dto.TranslationRequest;
import main.translationapi.dto.TranslationResponse;
import main.translationapi.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TranslationController {

    private final TranslationService translationService;
    private final HttpServletRequest request;

    public TranslationController(TranslationService translationService,
                                 HttpServletRequest request){
        this.translationService = translationService;
        this.request = request;
    }

    @PostMapping("/translate")
    public ResponseEntity<TranslationResponse> translationRequest(@RequestBody TranslationRequest translationRequest){

        TranslationResponse response = translationService.translate(translationRequest, request);
        return ResponseEntity.ok(response);
    }
}
