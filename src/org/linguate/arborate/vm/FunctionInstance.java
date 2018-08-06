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
public class FunctionInstance {
    private final FunctionDefinition definition;
    private final List<Object> localVars;

    public FunctionInstance(FunctionDefinition definition, List<Object> localVars) {
        this.definition = definition;
        this.localVars = localVars;
    }

    public FunctionDefinition getDefinition() {
        return definition;
    }

    public List<Object> getLocalVars() {
        return localVars;
    }

    
}
