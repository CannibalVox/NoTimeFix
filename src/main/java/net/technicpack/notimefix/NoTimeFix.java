package net.technicpack.notimefix;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.technicpack.notimefix.io.DataReader;
import net.technicpack.notimefix.io.DataWriter;
import net.technicpack.notimefix.io.QuestingData;

import java.io.*;

@Mod(modid=NoTimeFix.MODID, name=NoTimeFix.MODNAME, version = NoTimeFix.VERSION)
public class NoTimeFix {
    public static final String MODID = "notimefix";
    public static final String MODNAME = "No Time Fix";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onSave(WorldEvent.Save event) {
        if (event.world.provider.dimensionId == 0 && !event.world.isRemote) {
            File hqmDat = new File(new File(event.world.getSaveHandler().getWorldDirectory(), "HardcoreQuesting"), "players.dat");

            if (!hqmDat.exists())
                return;

            try {
                DataReader reader = new DataReader(new FileInputStream(hqmDat));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataWriter writer = new DataWriter(byteArrayOutputStream);
                QuestingData data = new QuestingData(reader, writer);

                reader.close();
                writer.close();

                byte[] outData = byteArrayOutputStream.toByteArray();

                FileOutputStream writeOut = new FileOutputStream(hqmDat);
                writeOut.write(outData);
                writeOut.write(new byte[] {0});
                writeOut.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
