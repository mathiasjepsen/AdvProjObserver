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
            finder.add(dir);
            ClassFilter filter
                    = new AndClassFilter 
                            // Must not be an interface
                            (new NotClassFilter(new InterfaceOnlyClassFilter()),
                            // Must implement the ITestClass interface
                            new SubclassClassFilter(IEasyWebsocket.class));

            ArrayList<ClassInfo> foundClasses = new ArrayList();
            finder.findClasses(foundClasses, filter);
            
            //hardcode that we get the first class that matches these criteria
            ClassInfo classInfo = foundClasses.get(0);
            
            return (IEasyWebsocket) Class.forName(classInfo.getClassName()).newInstance();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MyClassFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
