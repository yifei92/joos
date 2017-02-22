package a.b.c;

// PARSER_WEEDER
/**
 * This method is supposed to test whether access to the resulting
 * objects of method calls are parsed correctly.
 **/
public class inputfile{

    public int i;

    public J1_1_AmbiguousName_AccessResultFromMethod(int j){   
        i = j;
        int x;
        int k;
        for(k = 0 ; k < 10 ; k = k+1) {
            int s = 0;
            while(k < i) {
                boolean blah = true;
            }
        }
    }

    public J1_1_AmbiguousName_AccessResultFromMethod inc(int a, int b){
	   return new J1_1_AmbiguousName_AccessResultFromMethod(i+1);
    }

    public static int test(int b){
	   return new J1_1_AmbiguousName_AccessResultFromMethod(120).inc().inc().inc().i;
    }

}
