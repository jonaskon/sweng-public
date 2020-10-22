import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public static class App {
    public static void main(String[] args) throws FileNotFoundException {
        List<Double> numbers = getNumbers("data");
        Double mean = computeMean(numbers);
        Double stdDev = computeStdDev(numbers, mean);
        List<Double> normalized = normalize(numbers, mean, stdDev);
        System.out.println(normalized);
        writeOnFile(normalized, "output");
    }

    public static List<Double> getNumbers(String filename) {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        List<Double> numbers = new ArrayList<>();
        while (scanner.hasNextDouble()) {
            double number = scanner.nextDouble();
            numbers.add(number);
        }
        scanner.close();
        return numbers;
    }

    public static Double computeMean(List<Double> numbers) {
        double sum = 0;
        for (double f : numbers) {
            sum += f;
        }
        double mean = sum / numbers.size();
        return mean;
    }

    public static double computeStdDev(List<Double> numbers, Double mean) {
        double sumSquare = 0;
        for (double f : numbers) {
            double diff = f - mean;
            sumSquare += diff * diff;
        }
        double std = Math.sqrt(sumSquare / numbers.size());
        return std;
    }

    public static List<Double> normalize(List<Double> numbers, Double mean, Double stdDev) {
        List<Double> normalized = new ArrayList<>();
        for (double f : numbers) {
            normalized.add((f - mean) / stdDev);
        }
        return normalized;
    }

    public static void writeOnFile(List<Double> normalized, String filename) throws FileNotFoundException {
        try {
            FileWriter fw = new FileWriter(filename);
            for (double n : normalized) {
                fw.write(Double.toString(n));
                fw.write("\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing output file");
        }
        System.out.println("Wrote output file.");
    }
}
