// PARSER_WEEDER
public class J1_1_Cast_MultipleCastOfSameValue_1{
    public static int i = this.i;
    public J1_1_Cast_MultipleCastOfSameValue_1 () {}

    public static int test() {
      int a;
      i = this.i;
      return 123;
    }

}
