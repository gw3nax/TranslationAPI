package main.translationapi.service;

import jakarta.servlet.http.HttpServletRequest;
import main.translationapi.client.TranslationClient;
import main.translationapi.dto.TranslationRequest;
import main.translationapi.dto.TranslationResponse;
import main.translationapi.dto.YandexApiRequest;
import main.translationapi.dto.YandexApiResponse;
import main.translationapi.entity.TranslationEntity;
import main.translationapi.mapper.YandexRequestMapper;
import main.translationapi.mapper.YandexResponseMapper;
import main.translationapi.repository.TranslationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationClient translationClient;
    private final RestTemplate translator;
    private final YandexRequestMapper yandexRequestMapper;
    private final YandexResponseMapper yandexResponseMapper;
    private final ExecutorService executorService;

    public TranslationService(TranslationRepository translationRepository,
                              RestTemplate translator,
                              TranslationClient translationClient,
                              YandexResponseMapper yandexResponseMapper,
                              YandexRequestMapper yandexRequestMapper) {

        this.translationRepository = translationRepository;
        this.translator = translator;
        this.translationClient = translationClient;
        this.yandexRequestMapper = yandexRequestMapper;
        this.yandexResponseMapper = yandexResponseMapper;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public TranslationResponse translate(TranslationRequest translationRequest, HttpServletRequest request) {

        String[] wordsToTranslate = translationRequest.getTextToTranslate().split("\\s+");
        List<Future<TranslationResponse>> futures = new ArrayList<>();

        for (String word : wordsToTranslate) {
            futures.add(executorService.submit(() -> translateWord(new TranslationRequest(word,
                    translationRequest.getOriginalLang(),
                    translationRequest.getTranslatedLang()))));
        }

        List<String> translatedWords = new ArrayList<>();
        for (Future<TranslationResponse> future : futures) {
            try {
                TranslationResponse response = future.get();
                translatedWords.add(response.getTranslatedText());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        String translatedText = String.join(" ", translatedWords);
        TranslationResponse translationResponse = new TranslationResponse(translatedText);

        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setOriginalText(translationRequest.getTextToTranslate());
        translationEntity.setTranslatedText(translatedText);
        translationEntity.setIp(request.getRemoteAddr());
        translationRepository.save(translationEntity);

        return translationResponse;
    }

    private TranslationResponse translateWord(TranslationRequest translationRequest) {

        YandexApiRequest yandexApiRequest = yandexRequestMapper.mapToYandexRequest(translationRequest);
        YandexApiResponse yandexApiResponse = translator.postForObject(translationClient.getBaseUrl(), yandexApiRequest, YandexApiResponse.class);
        TranslationResponse translationResponse = yandexResponseMapper.mapToTranslationResponse(yandexApiResponse);

        return translationResponse;
    }
}
