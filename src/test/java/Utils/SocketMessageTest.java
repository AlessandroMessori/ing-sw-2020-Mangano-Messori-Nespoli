package Utils;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SocketMessageTest {

    static SocketMessage socketMessage;
    static Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        socketMessage = new SocketMessage("JoinGame", gson.toJson("Username"));
    }

    @After
    public void tearDown() throws Exception {
        socketMessage = null;
    }

    @Test
    public void getHeader() {
        socketMessage.setHeader("JoinGame");
        assertEquals("JoinGame", socketMessage.getHeader());
    }

    @Test
    public void setHeader() {
        socketMessage.setHeader("SendDivinity");
        assertEquals("SendDivinity", socketMessage.getHeader());
    }

    @Test
    public void getBody() {
        socketMessage.setBody("Username");
        assertEquals("Username", socketMessage.getBody());
    }

    @Test
    public void setBody() {
        socketMessage.setBody("Player1");
        assertEquals("Player1", socketMessage.getBody());
    }

    @Test
    public void getFullMessage() {

        socketMessage.setBody("JoinGame");
        socketMessage.setHeader("Username");

        assertEquals("{\"header\":\"Username\",\"body\":\"JoinGame\"}", socketMessage.getFullMessage());

    }
}
