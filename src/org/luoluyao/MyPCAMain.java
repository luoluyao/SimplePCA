package org.luoluyao; /**
 * Created by luoluyao on 2017/11/20.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MyPCAMain {

    public static double[][] readData(String path) {
        double[][] primaryArray = new double[150][4];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
            String lines = bufferedReader.readLine();
            int index = 0;
            while (lines != null) {
                String[] oneType = lines.split("\\s+");
                for (int i = 0; i < 4; i++) {
                    primaryArray[index][i] =Double.parseDouble(oneType[i]);
                }
                lines = bufferedReader.readLine();
                index++;
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return primaryArray;
    }
    public static void main(String[] args) {
            MyPCA pca = new MyPCA(0.95);

            // 获取原始数据
            double[][] primaryArray = readData("E:\\luoluyao\\java\\pca\\data.txt");
            System.out.println("--------------------------------------------");
            System.out.println("原始数据: ");
            System.out.println(primaryArray.length + "行，"
                    + primaryArray[0].length + "列");
            for (int i = 0; i < primaryArray.length; i++) {
                for (int j = 0; j < primaryArray[0].length; j++) {
                    System.out.print(+primaryArray[i][j] + " \t");
                }
                System.out.println();
            }

            // 均值中心化后的矩阵
            double[][] averageArray = pca.changeAverageToZero(primaryArray);
            System.out.println("--------------------------------------------");
            System.out.println("均值0化后的数据: ");
            System.out.println(averageArray.length + "行，"
                    + averageArray[0].length + "列");
            for (int i = 0; i < averageArray.length; i++) {
                for (int j = 0; j < averageArray[0].length; j++) {
                    System.out.print((float) averageArray[i][j] + " \t");
                }
                System.out.println();
            }

            // 协方差矩阵
            double[][] varMatrix = pca.calVarianceMatrix(averageArray);
            System.out.println("---------------------------------------------");
            System.out.println("协方差矩阵: ");
            for (int i = 0; i < varMatrix.length; i++) {
                for (int j = 0; j < varMatrix[0].length; j++) {
                    System.out.print((float) varMatrix[i][j] + "\t");
                }
                System.out.println();
            }
            // 特征值矩阵
            System.out.println("--------------------------------------------");
            System.out.println("特征值矩阵: ");
            double[][] eigenvalueMatrix = pca.geEigenValueMatrix(varMatrix);

            // 特征向量矩阵
            System.out.println("--------------------------------------------");
            System.out.println("特征向量矩阵: ");
            double[][] eigenVectorMatrix = pca.geEigenVectorMatrix(varMatrix);

            // 主成分矩阵
            System.out.println("--------------------------------------------");
            double[][] principalMatrix = pca.pca(
                    eigenvalueMatrix, eigenVectorMatrix,primaryArray);
            System.out.println("降维后的矩阵: ");
            for (int i = 0; i < principalMatrix.length; i++) {
                for (int j = 0; j < principalMatrix[0].length; j++) {
                    System.out.print((float) principalMatrix[i][j] + "\t");
                }
                System.out.println();
            }
    }
}
