package net.technicpack.notimefix.io;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.io.InputStream;

public class DataReader {
    private InputStream stream;
    private int byteBuffer;
    private int bitCountBuffer;

    public DataReader(InputStream stream) {
        this.stream = stream;
        this.byteBuffer = 0;
        this.bitCountBuffer = 0;
    }

    public int readData(int bitCount) {
        int data = 0;
        int readBits = 0;
        while (true) {
            int bitsLeft = bitCount - readBits;
            if (this.bitCountBuffer >= bitsLeft) {
                data |= (this.byteBuffer & (int)Math.pow(2.0, bitsLeft) - 1) << readBits;
                this.byteBuffer >>>= bitsLeft;
                this.bitCountBuffer -= bitsLeft;
                readBits += bitsLeft;
                break;
            }

            data |= this.byteBuffer << readBits;
            readBits += this.bitCountBuffer;

            try {
                this.byteBuffer = this.stream.read();
            } catch (IOException ex) {
                this.byteBuffer = 0;
            }

            this.bitCountBuffer = 8;
        }

        return data;
    }

    public void close() {
        try {
            this.stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readString(int bits) {
        int length = readData(bits);
        if (length == 0) {
            return null;
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = readByte();
        }
        return new String(bytes);
    }

    public boolean doesUnderlyingStreamHasMoreThanAByteOfData()
    {
        try {
            return this.stream.available() > 0; } catch (IOException ignored) {
        }
        return false;
    }

    public boolean readBoolean() {
        return readData(1) != 0;
    }

    public byte readByte() {
        return (byte)readData(8);
    }

    public short readShort() {
        return (short)readData(16);
    }

    public int readInt() {
        return readData(32);
    }

    public String readName() {
        return readString(5);
    }

    public int readPlayers() {
        return readData(16);
    }

    public int readQuests() {
        return readData(10);
    }

    public int readTasks() {
        return readData(4);
    }

    public int readGroupCount() {
        return readData(10);
    }

    public int readLimit() {
        return readData(10);
    }

    public int readTeams() {
        return readData(10);
    }

    public int readLives() {
        return readData(8);
    }

    public int readTeamRewardSetting() {
        return readData(2);
    }

    public int readTeamLivesSetting() {
        return readData(1);
    }

    public int readDeaths() {
        return readData(12);
    }

    public int readTicks() {
        return readData(10);
    }

    public int readHours() {
        return readData(32);
    }

    public int readReputation() {
        return readData(8);
    }

    public int readReputationValue() {
        return readData(32);
    }
}
