import java.util.*;

class Student {
    double theory, lab;
    int attendance;

    Student(double t, double l, int a) {
        theory = t;
        lab = l;
        attendance = a;
    }
}

interface ModerationRule {
    double apply(double score, Student s);
}

class AttendanceModeration implements ModerationRule {
    public double apply(double score, Student s) {
        return s.attendance > 90 ? score + 5 : score;
    }
}

abstract class EvaluationEngine {

    List<ModerationRule> rules = new ArrayList<>();

    public final void evaluate(Student s) {
        double score = calculateFinalScore(s.theory, s.lab);

        for (ModerationRule r : rules)
            score = r.apply(score, s);

        System.out.println(generateGrade(score));
    }

    abstract double calculateFinalScore(double theory, double lab);
    abstract String generateGrade(double score);

    void addRule(ModerationRule r) {
        rules.add(r);
    }
}

class BTechEvaluation extends EvaluationEngine {
    double calculateFinalScore(double t, double l) {
        return t * 0.7 + l * 0.3;
    }

    String generateGrade(double s) {
        return s >= 85 ? "A" : s >= 70 ? "B" : "C";
    }
}

class MCAEvaluation extends EvaluationEngine {
    double calculateFinalScore(double t, double l) {
        return t * 0.6 + l * 0.4;
    }

    String generateGrade(double s) {
        return s >= 90 ? "Distinction" : "First" ;
    }
}

public class Main {
    public static void main(String[] args) {

        Student st = new Student(80, 90, 95);

        EvaluationEngine engine = new BTechEvaluation();
        engine.addRule(new AttendanceModeration());
        engine.evaluate(st);

        engine = new MCAEvaluation();
        engine.evaluate(st);
    }
}
