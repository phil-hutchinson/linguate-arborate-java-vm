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
    INTEGER_SHIFT_LEFT,
    INTEGER_SHIFT_RIGHT_ARITHMETIC,
    INTEGER_SHIFT_RIGHT_LOGICAL,
    INTEGER_ROTATE_LEFT,
    INTEGER_ROTATE_RIGHT,
    INTEGER_BITWISE_AND,
    INTEGER_BITWISE_OR,
    INTEGER_BITWISE_NOT,
    INTEGER_BITWISE_XOR,
    INTEGER_EQUAL,
    INTEGER_NOT_EQUAL,
    INTEGER_GREATER_THAN,
    INTEGER_LESS_THAN,
    INTEGER_GREATER_EQUAL,
    INTEGER_LESS_EQUAL,
    
    // boolean operations
    BOOLEAN_TO_STACK,
    BOOLEAN_EQUAL,
    BOOLEAN_NOT_EQUAL,
    BOOLEAN_AND,
    BOOLEAN_OR,
    BOOLEAN_NOT,
    BOOLEAN_XOR,
        
    // function operations
    CALL_FUNCTION,
    EXIT_FUNCTION,
    BRANCH,
    BRANCH_TRUE,
    BRANCH_FALSE,
}
