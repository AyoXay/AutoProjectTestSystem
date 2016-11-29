/**
 * @Authuor Administrator
 * @Create 2016-11-29-16:51
 */
public class TestMain {
    public static void main(String[] args) {
        TestMain testMain = new TestMain();
        Test test = testMain.new Test();
        testMain.superPrint("1");
        test.printStr("private");
        test.print("protected");
        test.printString("public");
        pPrint("static private");
    }

    public void superPrint(String str){
        System.out.println(str);
    }

    public static void pPrint(String str){
        new TestMain().new Test().printStr(str);
    }

    private class Test{
        public void printString(String str){
            System.out.println(str);
        }

        private void printStr(String str){
            System.out.println(str);
        }

        protected void print(String str){
            System.out.println(str);
        }
    }

}

//class NewTest{
//    static TestMain testMain = new TestMain();
//    static TestMain.Test test = testMain.new Test();
//
//    public static void main(String[] args) {
//        test.printString("public");
//        test.printString("protected");
//    }
//}
