public class TestHRDetector {

    public static void main( String args[] ) 
    {
    
        int [] testArray1 = {10,11,5,8,9};
        int [] testArray2 = {1,2,3,2,3};
        int [] testArray3 = {1,2,1,1,2,1,1};

        System.out.print("HR DETECTOR1 DETAILS:\n");
        HRDetector detector1 = new HRDetector(5);
        detector1.addReading(2);
        detector1.addReading(3);
        detector1.addReading(4);
        detector1.addReading(5);
        detector1.addReading(4);
        detector1.addReading(4);
        System.out.print("Values in array: " + detector1.toString() + " \n");
        System.out.print("Boolean result of comparison with testArray1: " + detector1.passed(testArray1) + " \n");
        System.out.print("Boolean result of comparison with testArray2: " + detector1.passed(testArray2) + " \n");
        System.out.print("Boolean result of comparison with testArray2: " + detector1.passed(testArray3) + " \n");
        System.out.print("Sum of all readings: "+ detector1.sumReadings() + " \n");
        System.out.print("Value of reading at position 3: " + detector1.returnReading(3) + " \n");
        System.out.print("Value of reading at position 9: " + detector1.returnReading(9) + " \n");
        
        System.out.print("\n\nHR DETECTOR2 DETAILS\n");
        HRDetector detector2 = new HRDetector(12);
        detector2.loadHRStats("readings.txt");
        System.out.printf("Values in array: " + detector2.toString());

        HRDetector detector3 = new HRDetector(7);
        detector3.addReading(20);
        detector3.addReading(2);
        detector3.addReading(45);
        detector3.addReading(11);
        detector3.addReading(9);
        detector3.addReading(18);
        detector3.addReading(24);
        detector3.recordHRStats("record.txt");
    }
}
