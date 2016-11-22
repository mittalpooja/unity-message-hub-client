# unity-message-hub-client

 Writer thread sends messages to the server. This thread used stdout to read the requests on the client end & then writes the requests to the socket stream
 
 Note : For simplicity sake - this tread reads the following inputs
 1. getId (for getId request). It sends the message encoded into byte on the stream.
 2. getList (for getList request). Encodes the message to byte before sending it to the server
 3. relay (for relay request). It is followed by json in the next line.
 {"receivers":"2,3,4,7","message":"hello again"}
    This client takes the input in text/json form and converts it to more conpact form as described in the [MessageHubProtocol.java](https://github.com/mittalpooja/unity-message-hub-library/blob/master/src/main/java/com/unity/messagehub/library/MessageHubProtocol.java)
    

<example run>
[Poojas-MacBook-Pro:unity-message-hub-client poojamittal$ java -cp target/unity-message-hub-client-1.0-SNAPSHOT.jar:/Users/poojamittal/.m2/repository/com/googlecode/json-simple/json-simple/1.1/json-simple-1.1.jar:/Users/poojamittal/Documents/workspace/unity-message-hub-library/target/unity-message-hub-library-1.0-SNAPSHOT.jar com.unity.messagehub.client.Client
* __getId__
* 3
* __getList__
* 1,2,4
* __relay__
* __{"receivers":"1,2","message":"some message"}__
<example run>
