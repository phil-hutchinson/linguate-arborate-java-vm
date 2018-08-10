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
public enum InstructionCode {
    // stack operations
// stack operations
    VARIABLE_TO_STACK,
    STACK_TO_VARIABLE,
    
    // integer operations
    INTEGER_TO_STACK,
    INTEGER_ADD,
    INTEGER_SUBTRACT,
    INTEGER_MULTIPLY,
    INTEGER_DIVIDE,
    INTEGER_MODULUS,
    
    // function operations
    CALL_FUNCTION,
}
