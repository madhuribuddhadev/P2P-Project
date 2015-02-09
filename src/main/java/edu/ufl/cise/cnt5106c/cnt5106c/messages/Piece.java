package edu.ufl.cise.cnt5106c.cnt5106c.messages;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author Giacomo Benincasa    (giacomo@cise.ufl.edu)
 */
public class Piece extends Message {

    public Piece (int pieceIdx, byte[] content) throws Exception {
        super (Type.PIECE, join (pieceIdx, content));
    }

    public int getPieceIndex() {
        ByteBuffer buf = ByteBuffer.wrap(Arrays.copyOfRange(_payload, 0, 3));
        return buf.getInt();
    }

    public byte[] getContent() {
        return Arrays.copyOfRange(_payload, 4, _payload.length-1);
    }

    private static byte[] join (int pieceIdx, byte[] second) {
        ByteArrayOutputStream bof = new ByteArrayOutputStream (4 + second.length);
        bof.write(pieceIdx);
        bof.write(second, 0, second.length);
        return bof.toByteArray();
    }
}
