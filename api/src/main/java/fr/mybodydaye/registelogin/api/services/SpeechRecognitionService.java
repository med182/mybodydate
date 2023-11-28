package fr.mybodydaye.registelogin.api.services;

import java.io.FileInputStream;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.protobuf.ByteString;

public class SpeechRecognitionService {

    public String transcribeAudio(String filePath) throws Exception {
        try (SpeechClient speechClient = SpeechClient.create()) {
            ByteString audioBytes = ByteString.readFrom(new FileInputStream(filePath));

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("fr-FR")
                    .build();

            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

            RecognizeResponse response = speechClient.recognize(config, audio);

            if (!response.getResultsList().isEmpty()) {
                return response.getResults(0).getAlternatives(0).getTranscript();
            } else {
                return "Aucune transcription disponible.";
            }

        }
    }
}
