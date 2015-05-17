package net.technicpack.notimefix.io;

import java.io.IOException;
import java.io.OutputStream;

public class DataWriter {
    private OutputStream stream;
    private int byteBuffer;
    private int bitCountBuffer;
    private int bits;

    public DataWriter(OutputStream stream) {
        this.stream = stream;
    }

    public void writeData(int data, int bitCount) {
        this.bits += bitCount;

        long mask = (long)Math.pow(2.0D, bitCount) - 1L;
        data = (int)(data & mask);

        while (this.bitCountBuffer + bitCount >= 8) {
            int bitsToAdd = 8 - this.bitCountBuffer;
            int addMask = (int)Math.pow(2.0D, bitsToAdd) - 1;
            int addData = data & addMask;
            data >>>= bitsToAdd;
            addData <<= this.bitCountBuffer;
            this.byteBuffer |= addData;
            try {
                this.stream.write(this.byteBuffer);
            } catch (IOException ex) {
            }

            this.byteBuffer = 0;
            bitCount -= bitsToAdd;
            this.bitCountBuffer = 0;
        }

        this.byteBuffer |= data << this.bitCountBuffer;
        this.bitCountBuffer += bitCount;
    }

    public void close() {
        try {
            this.stream.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeString(String str, int bits) {
        if (str != null) {
            byte[] bytes = str.getBytes();
            int l = bytes.length;
            writeData(l, bits);
            for (int i = 0; i < l; i++) {
                writeByte(bytes[i]);
            }
        }
    }

    public void writeBoolean(boolean value) {
        writeData(value?1:0, 1);
    }

    public void writeByte(byte value) {
        writeData(value, 8);
    }

    public void writeShort(short value) {
        writeData(value, 16);
    }

    public void writeInt(int value) {
        writeData(value, 32);
    }

    public void writeName(String value) {
        writeString(value, 5);
    }

    public void writePlayers(int value) {
        writeData(value, 16);
    }

    public void writeQuests(int value) {
        writeData(value, 10);
    }

    public void writeTasks(int value) {
        writeData(value, 4);
    }

    public void writeGroupCount(int value) {
        writeData(value, 10);
    }

    public void writeLimit(int value) {
        writeData(value, 10);
    }

    public void writeTeams(int value) {
        writeData(value, 10);
    }

    public void writeLives(int value) {
        writeData(value, 8);
    }

    public void writeTeamRewardSetting(int value) {
        writeData(value, 2);
    }

    public void writeTeamLivesSetting(int value) {
        writeData(value, 1);
    }

    public void writeDeaths(int value) {
        writeData(value, 12);
    }

    public void writeTicks(int value) {
        writeData(value, 10);
    }

    public void writeHours(int value) {
        writeData(value, 32);
    }

    public void writeReputation(int value) {
        writeData(value, 8);
    }

    public void writeReputationValue(int value) {
        writeData(value, 32);
    }
}
