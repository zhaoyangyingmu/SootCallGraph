import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.SourceLocator;
import soot.options.Options;

import java.util.LinkedHashSet;
import java.util.Set;

public class HelloSoot {
    private static String PROJECT_ROOT = "G:\\Programming\\SootCallGraph\\"; // project root
    private static String JAVA_FILE_PATH = PROJECT_ROOT + "src\\main\\java\\entity";// java file directory
    private static String CLASS_FILE_PATH = PROJECT_ROOT + "target\\classes\\entity"; // class file directory
    private static String JRE_LIB_PATH = "F:\\Program Files\\Java\\jdk1.8.0_161\\jre\\lib\\"; // JDK jre lib path
    public void getClassUnderDir() {
        for (String clzName: SourceLocator.v().getClassesUnder(CLASS_FILE_PATH)) {
            System.out.printf("api class: %s\n", clzName);
            Scene.v().loadClass(clzName, SootClass.BODIES).setApplicationClass();
        }
    }

    public void getMethods() {
        for (SootClass clz : Scene.v().getApplicationClasses()) {
            System.out.println(clz.getName());
            if (clz.getMethods().size() == 0){System.out.println("do not have methods!!!!!");}
            else{
                System.out.println("method num:"+clz.getMethods().size());
                for(SootMethod me : clz.getMethods()) {
                    System.out.println(me.toString());
                    if (me.hasActiveBody()){
                        System.out.println(me.getActiveBody().toString());
                    }
                }
            }

        }

    }

    private static void setOptions() {
        soot.options.Options.v().set_keep_line_number(true);
        soot.options.Options.v().set_whole_program(true);
        // LWG
        soot.options.Options.v().setPhaseOption("jb", "use-original-names:true");
        soot.options.Options.v().setPhaseOption("cg", "verbose:false");
        soot.options.Options.v().setPhaseOption("cg", "trim-clinit:true");
        //soot.options.Options.v().setPhaseOption("jb.tr", "ignore-wrong-staticness:true");

        soot.options.Options.v().set_src_prec(Options.src_prec_java);
        soot.options.Options.v().set_prepend_classpath(true);

        // don't optimize the program
        soot.options.Options.v().setPhaseOption("wjop", "enabled:false");
        // allow for the absence of some classes
        //soot.options.Options.v().set_allow_phantom_refs(true);

    }

    private static void setSootClassPath() {
        System.setProperty("soot.class.path", ".;" + CLASS_FILE_PATH + ";" +
                JRE_LIB_PATH + "jce.jar;" +
                JRE_LIB_PATH + "rt.jar;" +
                JAVA_FILE_PATH);
    }

    public static void main(String[] args) {
        setSootClassPath();
        setOptions();

        HelloSoot s = new HelloSoot();
        s.getClassUnderDir();
        System.out.println("excuse me????");
//        s.getMethods();
    }
}
