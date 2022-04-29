package PROJECT;
import java.util.ArrayList;

public class EVAandSEP {

   private ArrayList<Integer> x;

   public GradeRegister() {
       this.x = new ArrayList<>();
   }

   public void addGradeBasedOnPoints(int points) {
       this.x.add(pointsToGrades(points));
   }

   public int numberOfGrades(int x) {
       int count = 0;
       for (int received: this.x) {
           if (received == x) {
               count++;
           }
       }

       return count;
   }

   public static int pointsToGrades(int points) {

       int x = 0;
       if (points < 50) {
           x = 0;
       } else if (points < 60) {
           x = 1;
       } else if (points < 70) {
           x = 2;
       } else if (points < 80) {
           x = 3;
       } else if (points < 90) {
           x = 4;
       } else {
           x = 5;
       }

       return x;
   }
}
