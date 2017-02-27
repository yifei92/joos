package a.b.c;

// PARSER_WEEDER
/**
 * This method is supposed to test whether access to the resulting
 * objects of method calls are parsed correctly.
 **/
public class inputfile{

    public int i;
    
    public constr(int j){   
        i = j;
        int x;
        int k;
        for(k = 0 ; k < 10 ; k = k+1) {
            int x;
            int s = 0;
            while(k < i) {
                boolean blah = true;
            }
        }
    }

    public J1_1_AmbiguousName_AccessResultFromMethod m1(int a, int b){
	   return new J1_1_AmbiguousName_AccessResultFromMethod(i+1);
    }

    public static int m2(int b){
	   return new J1_1_AmbiguousName_AccessResultFromMethod(120).inc().inc().inc().i;
    }

}
