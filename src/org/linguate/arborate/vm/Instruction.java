/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linguate.arborate.vm;

/**
 *
 * @author Phil Hutchinson
 */
public class Instruction {
    private InstructionCode instructionCode;
    private Object data;

    public InstructionCode getInstructionCode() {
        return instructionCode;
    }

    public Object getData() {
        return data;
    }

    public Instruction(InstructionCode instructionCode) {
        this.instructionCode = instructionCode;
        this.data = null;
    }
    public Instruction(InstructionCode instructionCode, Object data) {
        this.instructionCode = instructionCode;
        this.data = data;
    }
    
}
