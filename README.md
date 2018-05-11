# stream webcamera to browser

this is a demo to stream webcamera's rstp stream to browser using websocket.
The core idea likes below:

   1. the server poll the cameras to get frames using opencv to read camera's rtsp stream.
   2. the server serves as a websocket server and send each frame to the client(javascript).

Between 1 and 2 you can do any processes, i.e. face detection, to your frames.

## 1. get started

#### 1.1. open the project with IntelliJ IDEA

#### 1.2. prepare your opencv *.so/*.dll lib paths to java vm options.
   
    ```
        -Djava.library.path=/path/to/your/opencv/library

    ``` 
   Note your opencv should be compiled with ffmpeg.
#### 1.3. change the camera url in `application.yml` and run the project.
   we read the camera's frames with RTSP protocol which should be supported by your webcamera.
   Most cameras support such a protocal. You can find your RTSP url format by google with your camera type.
   
#### 1.4. watch your webcamera in browser such as Chrome.
    url: localhost:8080/index.html   

## 2. code explain

 - `PreviewController.java` the `greeting()` will send the latest frame to the sock client every 50ms(`@Scheduled(fixedRate = 50)`)
 - `Camera.java` this class will create a background thread polling the camera's stream.
 - `resources/static/index.html(app.js)` this file will subscribe server's socket endpoint.

## 3. future work
 Note the server send each frame sequentially to the client, thus a high bandwidth is required. The project can be further improved by involved video compression technology.**Your contribution is welcomed.**