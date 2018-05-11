package com.klaus.webcamera.live.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
@EnableScheduling
public class PreviewController {

    private SimpMessagingTemplate template;

    private Camera camera;

    @Value("${camera.rtspUrl}")
    private String rtspUrl;

    @Autowired
    public PreviewController( SimpMessagingTemplate template) {
        this.template = template;
    }

    @PostConstruct
    public void initCamera() {
        // you may manage multiple cameras and do whatever you want, i.e. face detection, in @Service.
        this.camera = new Camera(rtspUrl);
    }

    @Scheduled(fixedRate = 50)
    public void greeting() throws Exception {
        byte[] image = this.camera.getImage();
        template.convertAndSend("/preview/cam0", new String(image));
    }


}
