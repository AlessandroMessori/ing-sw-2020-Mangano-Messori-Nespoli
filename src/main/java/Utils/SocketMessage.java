package Utils;

import Server.Model.Pawn;

public class SocketMessage {

    private String header;
    private String body;


    /**
     * returns value of header
     *
     * @return header value
     */
    public String getHeader() {
        return header;
    }

    /**
     * sets value of Header
     *
     * @param h value of header
     */
    public void setHeader(String h) {
        header = h;
    }

    /**
     * returns value of body
     *
     * @return body value
     */
    public String getBody() {
        return body;
    }

    /**
     * sets value of Body
     *
     * @param b value of body
     */
    public void setBody(String b) {
        body = b;
    }

    /**
     *
     * @return complete socket message
     */
    public String getFullMessage() {
        return "{\"header\":\"" + getHeader() + "\"," + "\"body\":\"" + getBody() + "\"}";
    }

    public SocketMessage(String h, String b) {
        header = h;
        body = b;
    }
}
