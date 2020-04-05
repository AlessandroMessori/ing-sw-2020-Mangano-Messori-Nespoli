package Utils;

import Server.Model.Divinity;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MessageSerializerTest {
    MessageSerializer messageSerializer;

    @Before
    public void setUp() throws Exception {
        messageSerializer = new MessageSerializer();
    }

    @After
    public void tearDown() throws Exception {
        messageSerializer = null;
    }

    @Test
    public void serializeJoinGameTest() {
        assertEquals("{\"header\":\"JoinGame\",\"username\":\"Player1\",\"3players\":false}",
                messageSerializer.serializeJoinGame("Player1", false).toString());
    }

    @Test
    public void serializeSendDivinitiesTest() {
        ArrayList<Divinity> divinities = new ArrayList<Divinity>();
        divinities.add(Divinity.APOLLO);
        divinities.add(Divinity.ARTEMIS);
        assertEquals("{\"header\":\"SendDivinities\",\"divinities\":\"[APOLLO, ARTEMIS]\"}",
                messageSerializer.serializeDivinities(divinities).toString());
    }

    @Test
    public void serializeSendDivinityTest() {
        assertEquals("{\"header\":\"SendDivinity\",\"divinity\":\"APOLLO\"}",
                messageSerializer.serializeDivinity(Divinity.APOLLO).toString());
    }
}
