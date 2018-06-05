/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websockettestweb;

/**
 *
 * @author thomasthimothee
 */
public class TestClassImpl implements ITestClass{

    @Override
    public void run() {
        System.out.println("TestClassImpl is running");    }

    @Override
    public void stop() {
        System.out.println("TestClassImpl is stopping");    }
    
}
