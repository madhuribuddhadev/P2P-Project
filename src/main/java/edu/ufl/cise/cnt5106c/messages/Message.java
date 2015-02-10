package edu.ufl.cise.cnt5106c.messages;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.io.DataInputStream;

/**
 *
 * @author Giacomo Benincasa    (giacomo@cise.ufl.edu)
 */
public class Message {

    private int _length;
    private final Type _type;
    protected byte[] _payload;

    Message (Type type) throws Exception {
        this (type, null);
    }

    Message (Type type, byte[] payload) throws Exception {
        _length = (payload == null ? 0 : payload.length) + 1;
        _type = type;
        _payload = payload;
    }

    private void writeObject(ObjectOutputStream oos)
        throws IOException {

        oos.write(_length);
        oos.write(_type.getValue());
        oos.write(_payload, 0, _payload.length);
    }

    public static Message readMessage (int length, Type type, DataInputStream in) throws Exception {
        byte[] payload = null;
        if (length > 0) {
            payload = new byte[length];
            if (in.read(payload, 0, length) < length) {
                throw new IOException("payload bytes read are less than " + length);
            }
        }

        switch (type) {
            case CHOKE:
                return new Choke();

            case UNCHOKE:
                return new Unchoke();

            case INTERESTED:
                return new Interested();

            case NOT_INTERESTED:
                return new NotInterested();

            case HAVE:
                return new Have (payload);

            case BITFIELD:
                return new Bitfield (payload);

            case REQUEST:
                return new Request (payload);

            case PIECE:
                return new Piece (payload);

            default:
                throw new Exception ("message type not handled: " + type.toString());
        }
    }
}