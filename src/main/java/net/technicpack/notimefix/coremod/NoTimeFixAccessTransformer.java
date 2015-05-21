package net.technicpack.notimefix.coremod;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

public class NoTimeFixAccessTransformer extends AccessTransformer {

    public NoTimeFixAccessTransformer() throws IOException {
        super("notimefix_at.cfg");
    }
}
