package net.technicpack.notimefix;


import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import net.technicpack.notimefix.coremod.NoTimeFixResourcePack;

public class NoTimeFix extends DummyModContainer {
    public static final String MODID = "notimefix";
    public static final String MODNAME = "No Time Fix";
    public static final String VERSION = "2.0.2";

    public NoTimeFix() {
        super(new ModMetadata());

        ModMetadata metadata = getMetadata();
        metadata.modId = NoTimeFix.MODID;
        metadata.version = NoTimeFix.VERSION;
        metadata.name = NoTimeFix.MODNAME;
        metadata.authorList = ImmutableList.of("Cannibalvox");
        metadata.url = "http://www.technicpack.net/";
        metadata.credits = "Developed by Technic";
        metadata.description = "Who has time to debug things?";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Override
    public Class<?> getCustomResourcePackClass() { return NoTimeFixResourcePack.class; }
}
