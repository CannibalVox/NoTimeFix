package net.technicpack.notimefix.coremod.asm;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class FixPlayerTeamsEditor implements IAsmEditor {
    @Override
    public void edit(MethodNode method) {
        for (int i = 0; i < method.instructions.size(); i++) {
            AbstractInsnNode instruction = method.instructions.get(i);
            if (instruction.getOpcode() == RETURN) {
                method.instructions.insertBefore(instruction, new VarInsnNode(ALOAD, 0));
                MethodInsnNode methodInstruction = new MethodInsnNode(INVOKESTATIC, "net/technicpack/notimefix/mixin/QuestingDataMixin", "fixPlayerData", "(Lhardcorequesting/QuestingData;)V", false);
                method.instructions.insertBefore(instruction, methodInstruction);
                break;
            }
        }
    }

    @Override
    public String getClassName() {
        return "hardcorequesting.QuestingData";
    }

    @Override
    public String getMethodName() {
        return "<init>";
    }

    @Override
    public String getMethodDesc() {
        return "(Lhardcorequesting/FileVersion;Lhardcorequesting/network/DataReader;)V";
    }
}
