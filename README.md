# unity-message-hub-client

 Writer thread sends messages to the server. This thread used stdout to read the requests on the client end & then writes the requests to the socket stream
 
 Note : For simplicity sake - this tread reads the following inputs
 1. getId (for getId request). It sends the message encoded into byte on the stream.
 2. getList (for getList request). Encodes the message to byte before sending it to the server
 3. relay (for relay request). It is followed by json in the next line.
    {"receivers":"2,3,4,7","message":"hello again"}
    This client takes the input in text/json form and converts it to more conpact form as described in the https://github.com/mittalpooja/unity-message-hub-library/blob/master/src/main/java/com/unity/messagehub/library/MessageHubProtocol.java
