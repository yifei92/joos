// PARSER_WEEDER
public class J1_1_Cast_MultipleCastOfSameValue_1{
    public J1_1_Cast_MultipleCastOfSameValue_1 () {}

    public int x = (new J1_1_Cast_MultipleCastOfSameValue_1()).test();

    public static int test() {
      int a;
      a = 10;
      J1_1_Cast_MultipleCastOfSameValue_1 j = new J1_1_Cast_MultipleCastOfSameValue_1();
      j.test();
      return 123;
    }

}
