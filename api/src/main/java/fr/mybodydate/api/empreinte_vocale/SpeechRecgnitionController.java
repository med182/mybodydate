package fr.mybodydate.api.empreinte_vocale;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpeechRecgnitionController {

    @Autowired
    private fr.mybodydate.api.services.SpeechRecognitionService speechRecognitionService;

    @PostMapping("/transcribe")
    public String transcribeAudio(@RequestBody String filePath) {
        try {
            return speechRecognitionService.transcribeAudio(filePath);
        } catch (IOException e) {
            return "Erreur lors de la lecture du fichier audio.";
        } catch (Exception e) {
            return "Erreur lors de la reconnaissance vocale.";
        }
    }
}
