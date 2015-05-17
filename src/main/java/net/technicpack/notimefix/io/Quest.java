package net.technicpack.notimefix.io;

public class Quest {

    private int id;
    private int bits;
    private byte[] data;

    public Quest(DataReader reader) {
        this.id = reader.readQuests();
        this.bits = reader.readInt();

        int bytes = (this.bits/8) + (this.bits%8 == 0?0:1);
        this.data = new byte[bytes];

        int tmpBits = this.bits;
        int index = 0;

        while (tmpBits > 8) {
            this.data[index++] = reader.readByte();
            tmpBits -= 8;
        }

        if (tmpBits > 0)
            this.data[index] = (byte)reader.readData(tmpBits);
    }

    public void write(DataWriter writer) {
        writer.writeQuests(this.id);
        writer.writeInt(this.bits);

        int index = 0;

        while (bits > 8) {
            writer.writeByte(this.data[index++]);
            bits -= 8;
        }

        if (bits > 0)
            writer.writeData(this.data[index], bits);
    }
}
