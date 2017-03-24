// REACHABILITY
public class J1_7_Reachability_AfterIfWithWhileTrue {

	public J1_7_Reachability_AfterIfWithWhileTrue () {}

		public int f;
		public int g;
		public int h;
		public int m2(int a) {
			f = a;
			g = 2;
			h = -1;
			return f;
		}

	public static int test() {
		return J1_7_Reachability_AfterIfWithWhileTrue.m(1, 2, 3);
	}

	public static int m(int a, int b, int c) {
		J1_7_Reachability_AfterIfWithWhileTrue c;
		c = new J1_7_Reachability_AfterIfWithWhileTrue();
		c.m2(3);
		return 3;
	}
}
