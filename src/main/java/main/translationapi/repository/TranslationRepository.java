package main.translationapi.repository;

import main.translationapi.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepository extends JpaRepository<TranslationEntity, Long> {

}
