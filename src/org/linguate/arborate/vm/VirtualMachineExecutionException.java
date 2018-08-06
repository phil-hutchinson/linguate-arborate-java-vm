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
public class VirtualMachineExecutionException extends RuntimeException{
    public VirtualMachineExecutionException(String message) {
        super(message);
    }
}
