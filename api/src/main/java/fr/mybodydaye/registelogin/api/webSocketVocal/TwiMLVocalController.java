package fr.mybodydaye.registelogin.api.webSocketVocal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Pause;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.voice.Start;
import com.twilio.twiml.voice.Stream;

@Controller
public class TwiMLVocalController {

    @GetMapping(value = "/twiml", produces = "application/xml")
    @ResponseBody
    public String getStreamsTwiml(UriComponentsBuilder uriInfo) {
        String wssUrl = "wss://" + uriInfo.build().getHost() + "/talk_phone";

        return new VoiceResponse.Builder()
                .say(new Say.Builder("Hello! Start talking and the live audio will be streamed to your app  ").build())
                .start(new Start.Builder().stream(new Stream.Builder().url(wssUrl).build()).build())
                .pause(new Pause.Builder().length(30).build())
                .build().toXml();

    }

}
