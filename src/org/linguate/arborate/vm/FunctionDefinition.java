/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.List;

/**
 *
 * @author Phil Hutchinson
 */
public class FunctionDefinition {
    private final List<Instruction> code;
    private final int varCount;
    private final int inParamCount;
    private final int outParamCount;

    public FunctionDefinition(List<Instruction> code, int varCount, int inParamCount, int outParamCount) {
        this.code = code;
        this.varCount = varCount;
        this.inParamCount = inParamCount;
        this.outParamCount = outParamCount;
    }

    public List<Instruction> getCode() {
        return code;
    }

    public int getVarCount() {
        return varCount;
    }
    
    public int getInParamCount() {
        return inParamCount;
    }

    public int getOutParamCount() {
        return outParamCount;
    }
    
    
}
