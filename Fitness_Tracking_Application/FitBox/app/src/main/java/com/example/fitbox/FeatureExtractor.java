package com.example.fitbox;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;


public class FeatureExtractor {
    public static double[] Features = new double[40];
    public static double[] x_array = new double[100];
    public static double[] y_array = new double[100];
    public static double[] z_array = new double[100];
    public static double[] m_array = new double[100];

    public static double[] x_array_fft = new double[5];
    public static double[] y_array_fft = new double[5];
    public static double[] z_array_fft = new double[5];
    public static double[] m_array_fft = new double[5];

    public static void processAccelerometerData(double[][] sensor) {
        for (int i = 0; i < sensor.length; i++) {
            // Extract and store the X, Y, and Z-axis values
            double x = sensor[i][0];
            double y = sensor[i][1];
            double z = sensor[i][2];

            x_array[i] = x;
            y_array[i] = y;
            z_array[i] = z;

            // Calculate and store the magnitude
            double magnitude = Math.sqrt(x * x + y * y + z * z);
            m_array[i] = magnitude;
        }

        // calculate FFT for x, y, z, and m lists
        x_array_fft = getFirst5FFTMagnitudes(x_array);
        y_array_fft = getFirst5FFTMagnitudes(y_array);
        z_array_fft = getFirst5FFTMagnitudes(z_array);
        m_array_fft = getFirst5FFTMagnitudes(m_array);

    }

    public static void extractFeatures(){
        //Features in Time Domain
        // Auto-correlation
        double[] az_array = Autocorrelation(z_array);
        double[] am_array = Autocorrelation(m_array);

        // Calculate the total sum for 'y_array'
        Features[0] = totalAbsoluteSum(y_array);

        // Energy
        Features[1] = energy(x_array);
        Features[2] = energy(y_array);
        Features[3] = energy(z_array);

        // Skewness and Kurtosis
        Features[4] = skewness(az_array);
        Features[5] = kurtosis(az_array);

        // Mean
        Features[6] = mean(y_array);
        Features[7] = mean(am_array);

        // Variance
        Features[8] = variance(y_array);
        Features[9] = variance(m_array);
        Features[10] = variance(am_array);

        // Standard Deviation
        Features[11] = stdDev(x_array);
        Features[12] = stdDev(y_array);
        Features[13] = stdDev(z_array);
        Features[14] = stdDev(m_array);

        // Mean Absolute Deviation
        Features[15] = MAD(y_array);
        Features[16] = MAD(z_array);
        Features[17] = MAD(m_array);

        // Median
        Features[18] = median(y_array);

        // Median Absolute Deviation
        Features[19] = medianAbsDev(x_array);
        Features[20] = medianAbsDev(m_array);

        // IQR
        Features[21] = iqr(m_array);

        // RMS
        Features[22] = rms(x_array);
        Features[23] = rms(y_array);

        // Features in the Frequency Domain

        //mean
        Features[24] = mean(x_array_fft);
        Features[25] = mean(m_array_fft);

        // FFT Standard Deviation
        Features[26] = stdDev(z_array_fft);

        // FFT Mean Absolute Deviation
        Features[27] = MAD(x_array_fft);
        Features[28] = MAD(m_array_fft);

        // FFT Energy
        Features[29] = energy(x_array_fft);
        Features[30] = energy(y_array_fft);
        Features[31] = energy(z_array_fft);

        // FFT RMS
        Features[32] = rms(x_array_fft);
        Features[33] = rms(y_array_fft);
        Features[34] = rms(z_array_fft);

        // FFT Max
        double x_max_fft = max(x_array_fft);
        double y_max_fft = max(y_array_fft);
        double z_max_fft = max(z_array_fft);
        double m_max_fft = max(m_array_fft);

        Features[35] = x_max_fft;
        Features[36] = y_max_fft;
        Features[37] = z_max_fft;

        // FFT Max-Min Difference
        // FFT Min
        double x_min_fft = min(x_array_fft);
        double m_min_fft = min(m_array_fft);

        Features[38] = x_max_fft - x_min_fft;
        Features[39] = m_max_fft - m_min_fft;
    }

    public static double mean(double[] data) {
        Mean mean = new Mean();
        return mean.evaluate(data);
    }

    public static double variance(double[] data) {
        Variance variance = new Variance();
        return variance.evaluate(data);
    }

    public static double stdDev(double[] data) {
        StandardDeviation stdDev = new StandardDeviation();
        return stdDev.evaluate(data);
    }

    public static double MAD(double[] data) {
        double mean = mean(data);
        double sum = 0;
        for (double datum : data) {
            sum += Math.abs(datum - mean);
        }
        return sum / data.length;
    }

    public static double median(double[] data) {
        return StatUtils.percentile(data, 50.0);
    }

    public static double medianAbsDev(double[] data) {
        double median = median(data);
        double[] absoluteDeviations = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            absoluteDeviations[i] = Math.abs(data[i] - median);
        }
        return median(absoluteDeviations);
    }

    public static double iqr(double[] data) {
        double q1 = StatUtils.percentile(data, 25.0);
        double q3 = StatUtils.percentile(data, 75.0);
        // Calculate the (IQR)

        return q3 - q1;
    }

    public static double rms(double[] data) {
        double sumOfSquares = 0.0;
        for (double datum : data) {
            sumOfSquares += datum * datum;
        }
        return Math.sqrt(sumOfSquares / data.length);
    }

    public static double min(double[] data) {
        double min = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

    public static double max(double[] data) {
        double max = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    public static double skewness(double[] data) {
        Skewness skewness = new Skewness();
        return skewness.evaluate(data);
    }

    public static double kurtosis(double[] data) {
        Kurtosis kurtosis = new Kurtosis();
        return kurtosis.evaluate(data);
    }

    public static double energy(double[] data) {
        double sumOfSquares = 0.0;
        for (double datum : data) {
            sumOfSquares += datum * datum;
        }
        return sumOfSquares;
    }


    public static double totalAbsoluteSum(double[] data) {
        double sum = 0.0;
        for (double datum : data) {
            sum += Math.abs(datum);
        }
        return sum;
    }


    public static double[] Autocorrelation(double[] segment) {
        double[] autocorrelation = new double[10];
        double mu = mean(segment);

        for (int lag = 0; lag < 10; lag++) {
            double numerator = 0.0;
            double denominator = 0.0;
            for (int i = lag; i < segment.length; i++) {
                double diff1 = segment[i] - mu;
                double diff2 = segment[i - lag] - mu;
                numerator += diff1 * diff2;
                denominator += diff1 * diff1;
            }
            autocorrelation[lag] = numerator / denominator;
        }

        return autocorrelation;
    }



    public static double[] getFirst5FFTMagnitudes(double[] input) {
        int paddedLength = (int) Math.pow(2, Math.ceil(Math.log(input.length) / Math.log(2)));
        double[] paddedInput = new double[paddedLength];
        System.arraycopy(input, 0, paddedInput, 0, input.length);

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] complexResult = transformer.transform(paddedInput, TransformType.FORWARD);

        double[] result = new double[5];  // Array to store the first 5 FFT magnitudes

        for (int i = 0; i < 5; i++) {
            result[i] = complexResult[i].abs();
        }

        return result;
    }








}