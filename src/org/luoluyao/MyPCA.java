package org.luoluyao;

import Jama.Matrix;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by luoluyao on 2017/11/20.
 */
public class MyPCA {
    /**
    * PCA算法的步骤
    * 1、归一化：取均值然后减去均值
    * 2、求协方差矩阵
    * 3、求特征值
    * 4、求特征向量
    * 5、得到最后的主要成分矩阵
    * */
    private double thr;
    public MyPCA(double thr) {
        this.thr = thr;
    }

    /**
    * 归一化
    * @param matrix
    *
    * @return result 归一化后的矩阵
    * */
     double[][] changeAverageToZero(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        double[][] result = new double[row][col];
        for (int i = 0; i < col; i++) {
            double tmpSum = 0;
            for (int j = 0; j < row; j++) {
                tmpSum += matrix[j][i];
            }
            double ave = tmpSum / row;
            for (int k = 0; k < row; k++) {
                result[k][i] = matrix[k][i] - ave;
            }
        }
        return result;
    }

    /**
     * 求得协方差矩阵
     *
     * @param matrix
     *            归一化后的矩阵
     * @return result 协方差矩阵
     */
     double[][] calVarianceMatrix(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        double[][] result = new double[col][col];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                double tmpSum = 0;
                for (int m = 0; m < row; m++) {
                    tmpSum += matrix[m][i] * matrix[m][j];
                }
                result[i][j] = tmpSum / (row - 1);
            }
        }
        return result;
    }

    /**
     * 求特征值
     *
     * @param matrix
     *              矩阵
     * @return result 特征值矩阵
     */
     double[][] geEigenValueMatrix(double[][] matrix) {
         Matrix matrix1 = new Matrix(matrix);
         matrix1.eig().getD().print(10, 6);
         return matrix1.eig().getD().getArray();
    }

    /**
     * 求特征向量
     *
     * @param matrix
     *              矩阵
     * @return result 特征向量矩阵
     */
     double[][] geEigenVectorMatrix(double[][] matrix) {
         Matrix matrix1 = new Matrix(matrix);
         matrix1.eig().getV().print(10, 6);
         return matrix1.eig().getV().getArray();
    }

    /**
     *  获得主成分矩阵
     *
     *  @param valueMatrix
     *              特征值矩阵
     *  @param vectorMatrix
     *              特征向量矩阵
     *  @param matrix
     *              原矩阵
     *
     *  @return result 降维后的矩阵
     */
     double[][] pca(double[][] valueMatrix, double[][] vectorMatrix, double[][] matrix) {
        double[] value = new double[valueMatrix[0].length];
        double total = 0;
         HashMap<Double, double[]> hashMap = new HashMap<Double, double[]>();
        for (int i = 0; i < value.length; i++) {
            value[i] = valueMatrix[i][i];
            total += value[i];
            double[] tmp = new double[vectorMatrix.length];
            for (int j = 0; j < vectorMatrix.length; j++) {
                tmp[j] = vectorMatrix[j][i];
            }
            hashMap.put(value[i], tmp);
        }
        Arrays.sort(value);
        HashSet<Double> hashSet = new HashSet<Double>();
        double tmpSum = 0;
        int totalNum = 0;
        for (int i = value.length - 1; i >= 0; i--) {
            tmpSum += value[i];
            hashSet.add(value[i]);
            totalNum++;
            if (tmpSum / total >= thr) {
                break;
            }
        }

        double[][] newVectorMatrix = new double[totalNum][vectorMatrix[0].length];
        int count = 0;
        for (int i = value.length - 1; i >= 0; i--) {
            if (hashSet.contains(value[i])) {
                newVectorMatrix[count++] = hashMap.get(value[i]);
            }
        }

         System.out.println("\n" + "当前阈值: " + thr);
         System.out.println("取得的主成分数: " + totalNum + "\n");

        Matrix matrix1 = new Matrix(matrix);
        Matrix matrix2 = new Matrix(newVectorMatrix);
        return matrix1.times(matrix2.transpose()).getArray();
    }


  }
