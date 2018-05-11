package com.klaus.webcamera.live.demo;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class Camera implements Runnable {

    static {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    private static final Logger logger = LoggerFactory.getLogger(Camera.class);

    private String rtspUrl;

    private volatile Mat lastFrame = new Mat();

    private VideoCapture capture;

    private Thread bgThread;

    public Camera(String rtspUrl) {
        this.rtspUrl = rtspUrl;
        this.init();
    }

    private void init(){
        this.capture = new VideoCapture(rtspUrl);
        bgThread = new Thread(this);
        bgThread.start();
    }

    public void readVideoStream() {
        logger.info("begin fetch frames....");
        while (capture.isOpened()) {
            boolean res = capture.read(lastFrame);
        }
    }

    public Mat getLastFrame(){
        return lastFrame;
    }

    public byte[] getImage() {
        Mat frame = new Mat();
        Imgproc.resize(lastFrame,frame,new Size(640, 480));
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        byte[] imgBytes = Base64.getEncoder().encode(buffer.toArray());
        //opencv Mat should be released by hand in java. Java GC is not reliable when chaos of Mat created.
        frame.release();
        buffer.release();
        return imgBytes;
    }

    @Override
    public void run() {
        readVideoStream();
    }

}
