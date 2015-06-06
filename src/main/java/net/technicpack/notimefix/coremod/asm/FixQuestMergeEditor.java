package net.technicpack.notimefix.coremod.asm;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.tree.*;

public class FixQuestMergeEditor implements IAsmEditor {

    public FixQuestMergeEditor() {

    }

    @Override
    public void edit(MethodNode method) {
        LabelNode afterAvailabilityMerge = null;

        for (int i = 0; i < method.instructions.size(); i++) {
            if (method.instructions.get(i).getOpcode() == IFEQ) {
                afterAvailabilityMerge = ((JumpInsnNode)method.instructions.get(i)).label;
                break;
            }
        }

        if (afterAvailabilityMerge == null)
            throw new RuntimeException("Could not locate an IFEQ instruction in the method "+method.name);

        LabelNode firstNode = (LabelNode)method.instructions.get(0);

        method.instructions.insertBefore(firstNode, new VarInsnNode(ALOAD, 2));
        method.instructions.insertBefore(firstNode, new FieldInsnNode(GETFIELD, "hardcorequesting/quests/QuestData", "completed", "Z"));
        method.instructions.insertBefore(firstNode, new JumpInsnNode(IFEQ, firstNode));
        method.instructions.insertBefore(firstNode, new VarInsnNode(ALOAD, 2));
        method.instructions.insertBefore(firstNode, new FieldInsnNode(GETFIELD, "hardcorequesting/quests/QuestData", "claimed", "Z"));
        method.instructions.insertBefore(firstNode, new JumpInsnNode(IFNE, afterAvailabilityMerge));
    }

    @Override
    public String getClassName() {
        return "hardcorequesting.quests.Quest";
    }

    @Override
    public String getMethodName() {
        return "mergeProgress";
    }

    @Override
    public String getMethodDesc() {
        return "(Ljava/lang/String;Lhardcorequesting/quests/QuestData;Lhardcorequesting/quests/QuestData;)V";
    }
}
