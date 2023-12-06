package ua.kpi.ip22mp.trotsiuk.mmcmlr.util;

import org.apache.commons.math3.linear.RealVector;

import static java.util.Arrays.stream;

public class VectorUtils {

    private static final Double DOUBLE_ZERO = 0.0;

    public static double compareVectorsByPavlov(RealVector vector1, RealVector vector2) {
        return vector1.mapDivide(vector1.getNorm())
                .subtract(vector2.mapDivide(vector2.getNorm()))
                .getNorm();
    }

    public static int getNumberOfZerosInArray(double[] array) {
        return (int) stream(array).filter(number -> number == DOUBLE_ZERO).count();
    }

    public static int getNumberOfMissingZeros(double[] array1, double[] array2) {
        int count = 0;
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] == DOUBLE_ZERO && array2[i] != DOUBLE_ZERO) {
                count++;
            }

            if (array2[i] == DOUBLE_ZERO && array1[i] != DOUBLE_ZERO) {
                count++;
            }
        }

        return count;
    }
}
