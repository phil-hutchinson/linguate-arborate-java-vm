/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Phil Hutchinson
 */
public class FunctionDefinition {
    private final List<Instruction> code;
    private final int varCount;
    private final List<BaseType> inParams;
    private final int inParamCount;
    private final List<BaseType> outParams;
    private final int outParamCount;

    public FunctionDefinition(List<Instruction> code, int varCount, Iterable<BaseType> inParams, Iterable<BaseType> outParams) {
        this.code = code;
        this.varCount = varCount;

        List<BaseType> inParamList = new ArrayList<>();
        List<BaseType> outParamList = new ArrayList<>();
        inParams.forEach(inParamList::add);
        outParams.forEach(outParamList::add);
        this.inParams = Collections.unmodifiableList(inParamList);
        this.outParams = Collections.unmodifiableList(outParamList);
        
        this.inParamCount = inParamList.size();
        this.outParamCount = outParamList.size();
    }

    public List<Instruction> getCode() {
        return code;
    }

    public int getVarCount() {
        return varCount;
    }
    
    public List<BaseType> getInParams() {
        return inParams;
    }

    public List<BaseType> getOutParams() {
        return outParams;
    }

    public int getInParamCount() {
        return inParamCount;
    }

    public int getOutParamCount() {
        return outParamCount;
    }
}
