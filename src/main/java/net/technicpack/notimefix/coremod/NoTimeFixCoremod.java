package net.technicpack.notimefix.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.technicpack.notimefix.coremod.asm.FixPlayerTeamsEditor;
import net.technicpack.notimefix.coremod.asm.FixQuestMergeEditor;
import net.technicpack.notimefix.coremod.asm.IAsmEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoTimeFixCoremod implements IFMLLoadingPlugin {
    public static List<IAsmEditor> editors = new ArrayList<IAsmEditor>(2);

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
        boolean isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");

        editors.clear();
        editors.add(new FixPlayerTeamsEditor());
        editors.add(new FixQuestMergeEditor());
    }

    @Override
    public String getAccessTransformerClass() {
        return "net.technicpack.notimefix.coremod.NoTimeFixAccessTransformer";
    }
}
