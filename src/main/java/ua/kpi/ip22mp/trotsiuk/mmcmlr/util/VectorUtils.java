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
}
