/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websockettestweb;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.clapper.util.classutil.AndClassFilter;
import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;
import org.clapper.util.classutil.InterfaceOnlyClassFilter;
import org.clapper.util.classutil.NotClassFilter;
import org.clapper.util.classutil.SubclassClassFilter;

/**
 *
 * @author thomasthimothee
 */
public class MyClassFinder {

    IEasyWebsocket findClass(File dir) {
        try {
            ClassFinder finder = new ClassFinder();
            finder.add(dir); //Add a jar file, zip file or directory to the list of places to search for classes.
            ClassFilter filter
                    = new AndClassFilter //Construct a new AndClassFilter with a set of contained filters.
                            // Must not be an interface
                            (new NotClassFilter(new InterfaceOnlyClassFilter()),
                            // Must implement the ITestClass interface
                            new SubclassClassFilter(IEasyWebsocket.class));

            ArrayList<ClassInfo> foundClasses = new ArrayList();
            finder.findClasses(foundClasses, filter); // search in dir for classes that match ANDfilter, and put them in foundClasses

            //hardcode that we get the first class that matches these criteria
            ClassInfo classInfo = foundClasses.get(0);
            
            return (IEasyWebsocket) Class.forName(classInfo.getClassName()).newInstance();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MyClassFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
