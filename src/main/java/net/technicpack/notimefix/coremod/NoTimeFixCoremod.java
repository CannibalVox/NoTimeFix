package net.technicpack.notimefix.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class NoTimeFixCoremod implements IFMLLoadingPlugin {

    public NoTimeFixCoremod() {
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "net.technicpack.notimefix.coremod.NoTimeFixClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return "net.technicpack.notimefix.NoTimeFix";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return "net.technicpack.notimefix.coremod.NoTimeFixAccessTransformer";
    }
}
