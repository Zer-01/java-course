package edu.hw11.Task3;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public class FibonacciBCAppender implements ByteCodeAppender {
    @SuppressWarnings("MagicNumber")
    @Override
    public @NotNull Size apply(
        @NotNull MethodVisitor mv,
        Implementation.@NotNull Context context,
        @NotNull MethodDescription methodDescription
    ) {
        Label start = new Label();
        Label moreThenZero = new Label();
        Label notEqOne = new Label();
        Label forStart = new Label();
        Label forEnd = new Label();
        Label end = new Label();
        int arg = 0;
        int fibPrev = 1;
        int fibCurrent = 3;
        int forCounter = 5;

        mv.visitCode();
        mv.visitFrame(Opcodes.F_NEW, 1, new Object[] {Opcodes.INTEGER}, 0, null);

        mv.visitLocalVariable("arg", "I", null, start, end, arg);
        mv.visitLocalVariable("fibPrev", "J", null, start, end, fibPrev);
        mv.visitLocalVariable("fibCurrent", "J", null, start, end, fibCurrent);
        mv.visitLocalVariable("forCounter", "I", null, start, end, forCounter);

        mv.visitLabel(start);
        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitJumpInsn(Opcodes.IFGT, moreThenZero); //if(n<=0) return 0
        mv.visitInsn(Opcodes.LCONST_0);
        mv.visitInsn(Opcodes.LRETURN);
        mv.visitLabel(moreThenZero);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        mv.visitVarInsn(Opcodes.ILOAD, 0);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitJumpInsn(Opcodes.IF_ICMPNE, notEqOne); //if(n==1) return 1
        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitInsn(Opcodes.LRETURN);
        mv.visitLabel(notEqOne);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        mv.visitInsn(Opcodes.LCONST_0);
        mv.visitVarInsn(Opcodes.LSTORE, fibPrev); //long fibPrev = 0;
        mv.visitInsn(Opcodes.LCONST_1);
        mv.visitVarInsn(Opcodes.LSTORE, fibCurrent); //long fibCurrent = 1;

        mv.visitInsn(Opcodes.ICONST_2);
        mv.visitVarInsn(Opcodes.ISTORE, forCounter); //i=2
        mv.visitLabel(forStart);
        mv.visitFrame(Opcodes.F_APPEND, 3, new Object[] {Opcodes.LONG, Opcodes.LONG, Opcodes.INTEGER}, 0, null);
        mv.visitVarInsn(Opcodes.ILOAD, forCounter);
        mv.visitVarInsn(Opcodes.ILOAD, arg);
        mv.visitJumpInsn(Opcodes.IF_ICMPGT, forEnd); //i <= n

        mv.visitVarInsn(Opcodes.LLOAD, fibCurrent); //int fibTemp = fibCurrent;
        mv.visitVarInsn(Opcodes.LLOAD, fibCurrent);
        mv.visitVarInsn(Opcodes.LLOAD, fibPrev);
        mv.visitInsn(Opcodes.LADD); //fibCurrent = fibPrev + fibCurrent;
        mv.visitVarInsn(Opcodes.LSTORE, fibCurrent);
        mv.visitVarInsn(Opcodes.LSTORE, fibPrev); //fibPrev = fibTemp;

        mv.visitIincInsn(forCounter, 1); //i++
        mv.visitJumpInsn(Opcodes.GOTO, forStart);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        mv.visitLabel(forEnd);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        mv.visitLabel(end);
        mv.visitVarInsn(Opcodes.LLOAD, fibCurrent);
        mv.visitInsn(Opcodes.LRETURN); //return fibCurrent

        mv.visitMaxs(6, 6);
        mv.visitEnd();

        return new Size(6, 6);
    }
}
