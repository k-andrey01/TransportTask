import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;

public class MyChooser {
    Integer[][] checkMatrix = new Integer[3][3];
    Integer[] u = {-1,-1,-1};
    Integer[] w = {-1,-1,-1};

    public Integer[][] methodNorthEast(Integer[] makeArr, Integer[] needArr){
        Integer i = 0;
        Integer j = 0;

        Integer[][] resMatrix = {{0,0,0},{0,0,0},{0,0,0}};

        while (i<3 && j<3){
            if (needArr[j].compareTo(makeArr[i])==1){
                resMatrix[i][j] = makeArr[i];
                needArr[j] = needArr[j]-makeArr[i];
                makeArr[i] = 0;
                i++;
            }
            else if (needArr[j].compareTo(makeArr[i])==-1){
                resMatrix[i][j] = needArr[j];
                makeArr[i] = makeArr[i]-needArr[j];
                needArr[j] = 0;
                j++;
            }
            else if (needArr[j].equals(makeArr[i])){
                resMatrix[i][j] = needArr[j];
                makeArr[i] = makeArr[i]-needArr[j];
                needArr[j] = 0;
                j++;
                i++;
            }
        }
        return resMatrix;
    }

    public Integer[][] methodMinimalElement(Integer[] makeArr, Integer[] needArr){
        Integer[][] priceMatrix = {{3637,3043,4386},
                                   {3793,3165,4711},
                                   {4509,3714,5607}};
        Integer[][] oporMatrix = {{0,0,0},{0,0,0},{0,0,0}};
        Integer x = -1;
        Integer y = -1;
        int count = 0;
        while (count<9) {
            Integer minElement = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (priceMatrix[i][j].compareTo(minElement) == -1) {
                        minElement = priceMatrix[i][j];
                        x = i;
                        y = j;
                    }
                }
            }
            priceMatrix[x][y] = Integer.MAX_VALUE;
            if (needArr[y].equals(makeArr[x]) && !needArr[y].equals(0) && !makeArr[x].equals(0)) {
                oporMatrix[x][y] = needArr[y];
                priceMatrix[x][y] = Integer.MAX_VALUE;
                needArr[y] = 0;
                makeArr[x] = 0;
            } else if (needArr[y].compareTo(makeArr[x]) == 1 && !needArr[y].equals(0) && !makeArr[x].equals(0)) {
                oporMatrix[x][y] = makeArr[x];
                needArr[y] -= makeArr[x];
                priceMatrix[x][y] = Integer.MAX_VALUE;
                makeArr[x] = 0;
            } else if (needArr[y].compareTo(makeArr[x]) == -1 && !needArr[y].equals(0) && !makeArr[x].equals(0)) {
                oporMatrix[x][y] = needArr[y];
                makeArr[x] -= needArr[y];
                priceMatrix[x][y] = Integer.MAX_VALUE;
                needArr[y] = 0;
            }
            count++;
        }
        return oporMatrix;
    }

    public Boolean checkPlan(Integer[][] oporMatrix, Integer[][] priceMatrix){
        for (int j=0; j<3; j++){
            for (int i=0; i<3; i++){
                if (oporMatrix[j][i]!=0){
                    if (!u[j].equals(-1) && w[i].equals(-1)) {
                        w[i] = priceMatrix[j][i] - u[j];

                        calcPot(priceMatrix, oporMatrix, i ,j);
                    }
                    else if (!w[i].equals(-1) && u[j].equals(-1)) {
                        u[j] = priceMatrix[j][i] - w[i];

                        calcPot(priceMatrix, oporMatrix, i ,j);
                    }
                    else if (w[i].equals(-1) && u[j].equals(-1)){
                        u[j] = 0;
                        w[i] = priceMatrix[j][i]-u[j];

                        calcPot(priceMatrix, oporMatrix, i ,j);
                    }
                }
            }
        }
//        System.out.println("Poss");
//        for (Integer a:u) {
//            System.out.print(a+" ");
//        };
//        System.out.println();
//        for (Integer a:w) {
//            System.out.print(a+" ");
//        };
        //System.out.println("\nDifferent Matrix");
        Boolean flag = true;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                Integer diff = priceMatrix[i][j]-(w[j]+u[i]);
                checkMatrix[i][j] = diff;
                //System.out.print(diff+" ");
                if (diff.compareTo(0)==-1){
                    flag=false;
                }
            }
            //System.out.println();
        }
        for (int i=0; i<3; i++) {
            u[i] = -1;
            w[i] = -1;
        }
        return flag;
    }

    public void calcPot(Integer[][] priceMatrix, Integer[][] oporMatrix, int i, int j){
        for (int k = 0; k < 3; k++) {
            if (k != i && oporMatrix[j][k]!=0 && w[k].equals(-1)) {
                w[k] = priceMatrix[j][k] - u[j];
                calcPot(priceMatrix, oporMatrix, k, j);
            }
            if (k != j && oporMatrix[k][i]!=0 && u[k].equals(-1)) {
                u[k] = priceMatrix[k][i] - w[i];
                calcPot(priceMatrix, oporMatrix, i, k);
            }
        }
    }

    public Integer[][] getNewMatrix(Integer[][] oporMatrix){
        Integer tmpX = -1;
        Integer tmpY = -1;
        Integer tmpX1 = -1;
        Integer tmpY1 = -1;
        Integer tmpX2 = -1;
        Integer tmpY2 = -1;
        Integer tmpX3 = -1;
        Integer tmpY3 = -1;
        Boolean flag = false;
        Integer maxAbs = -1;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (Math.abs(checkMatrix[i][j])>maxAbs && checkMatrix[i][j].compareTo(0)==-1){
                    tmpX = i;
                    tmpY = j;
                    maxAbs = Math.abs(checkMatrix[i][j]);
                }
            }
        }
        Integer minWeight = 0;

        if (tmpX.equals(0) && tmpY.equals(0) && !flag){
            if (oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY+1; tmpX2 = tmpX; tmpY2 = tmpY+1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY+2].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+2].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+2];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY+2; tmpX2 = tmpX; tmpY2 = tmpY+2; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && oporMatrix[tmpX+2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+2][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+2][tmpY];
                tmpX1 = tmpX+2; tmpY1 = tmpY+1; tmpX2 = tmpX; tmpY2 = tmpY+1; tmpX3 = tmpX+2; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY+2].compareTo(0)==1 && oporMatrix[tmpX+2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+2][tmpY+2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+2].compareTo(oporMatrix[tmpX+2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+2];
                else
                    minWeight = oporMatrix[tmpX+2][tmpY];
                tmpX1 = tmpX+2; tmpY1 = tmpY+2; tmpX2 = tmpX; tmpY2 = tmpY+2; tmpX3 = tmpX+2; tmpY3 = tmpY;
            }
        }

        else if (tmpX.equals(0) && tmpY.equals(1)  && !flag){
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY-1; tmpX2 = tmpX; tmpY2 = tmpY-1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX+2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+2][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX+2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX+2][tmpY];
                tmpX1 = tmpX+2; tmpY1 = tmpY-1; tmpX2 = tmpX; tmpY2 = tmpY-1; tmpX3 = tmpX+2; tmpY3 = tmpY;
            }
            if (oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY+1; tmpX2 = tmpX; tmpY2 = tmpY+1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && oporMatrix[tmpX+2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+2][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+2][tmpY];
                tmpX1 = tmpX+2; tmpY1 = tmpY+1; tmpX2 = tmpX; tmpY2 = tmpY+1; tmpX3 = tmpX+2; tmpY3 = tmpY;
            }
        }

        else if (tmpX.equals(0) && tmpY.equals(2) && !flag){
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY-1; tmpX2 = tmpX; tmpY2 = tmpY-1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX+2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+2][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX+2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX+2][tmpY];
                tmpX1 = tmpX+2; tmpY1 = tmpY-1; tmpX2 = tmpX; tmpY2 = tmpY-1; tmpX3 = tmpX+2; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY-2].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY-2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-2].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-2];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY-2; tmpX2 = tmpX; tmpY2 = tmpY-2; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY-2].compareTo(0)==1 && oporMatrix[tmpX+2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+2][tmpY-2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-2].compareTo(oporMatrix[tmpX+2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-2];
                else
                    minWeight = oporMatrix[tmpX+2][tmpY];
                tmpX1 = tmpX+2; tmpY1 = tmpY-2; tmpX2 = tmpX; tmpY2 = tmpY-2; tmpX3 = tmpX+2; tmpY3 = tmpY;
            }
        }

        else if (tmpX.equals(1) && tmpY.equals(0) && !flag){
            if (oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY+1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+1;
            }
             if (oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+2].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY+2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+2].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+2];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY+2; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+2;
            }
             if (oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY+1; tmpX2 = tmpX; tmpY2 = tmpY+1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY+2].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+2].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+2];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY+2; tmpX2 = tmpX; tmpY2 = tmpY+2; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
        }

        else if (tmpX.equals(1) && tmpY.equals(1) && !flag){
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY-1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-1;
            }
             if (oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY+1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+1;
            }
             if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY-1; tmpX2 = tmpX; tmpY2 = tmpY-1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY+1; tmpX2 = tmpX; tmpY2 = tmpY+1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
        }

        else if (tmpX.equals(1) && tmpY.equals(2) && !flag){
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY-1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-1;
            }
             if (oporMatrix[tmpX][tmpY-2].compareTo(0)==1 && oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY-2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-2].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-2];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY-2; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-2;
            }
             if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY-1; tmpX2 = tmpX; tmpY2 = tmpY-1; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
             if (oporMatrix[tmpX][tmpY-2].compareTo(0)==1 && oporMatrix[tmpX+1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX+1][tmpY-2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-2].compareTo(oporMatrix[tmpX+1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-2];
                else
                    minWeight = oporMatrix[tmpX+1][tmpY];
                tmpX1 = tmpX+1; tmpY1 = tmpY-2; tmpX2 = tmpX; tmpY2 = tmpY-2; tmpX3 = tmpX+1; tmpY3 = tmpY;
            }
        }

        else if (tmpX.equals(2) && tmpY.equals(0) && !flag){
            if (oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY+1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+1;
            }
            if (oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+2].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY+2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+2].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+2];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY+2; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+2;
            }
            if (oporMatrix[tmpX-2][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-2][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX-2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX-2][tmpY];
                tmpX1 = tmpX-2; tmpY1 = tmpY+1; tmpX2 = tmpX-2; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+1;
            }
            if (oporMatrix[tmpX-2][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+2].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-2][tmpY+2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+2].compareTo(oporMatrix[tmpX-2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+2];
                else
                    minWeight = oporMatrix[tmpX-2][tmpY];
                tmpX1 = tmpX-2; tmpY1 = tmpY+2; tmpX2 = tmpX-2; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+2;
            }
        }

        else if (tmpX.equals(2) && tmpY.equals(1) && !flag){
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY-1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-1;
            }
            if (oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY+1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+1;
            }
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX-2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-2][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX-2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-2; tmpY1 = tmpY-1; tmpX2 = tmpX-2; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-1;
            }
            if (oporMatrix[tmpX-2][tmpY].compareTo(0)==1 && oporMatrix[tmpX][tmpY+1].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-2][tmpY+1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY+1].compareTo(oporMatrix[tmpX-2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY+1];
                else
                    minWeight = oporMatrix[tmpX-2][tmpY];
                tmpX1 = tmpX-2; tmpY1 = tmpY+1; tmpX2 = tmpX-2; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY+1;
            }
        }

        else if (tmpX.equals(2) && tmpY.equals(2) && !flag){
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY-1; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-1;
            }
            if (oporMatrix[tmpX][tmpY-2].compareTo(0)==1 && oporMatrix[tmpX-1][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-1][tmpY-2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-2].compareTo(oporMatrix[tmpX-1][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-2];
                else
                    minWeight = oporMatrix[tmpX-1][tmpY];
                tmpX1 = tmpX-1; tmpY1 = tmpY-2; tmpX2 = tmpX-1; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-2;
            }
            if (oporMatrix[tmpX][tmpY-1].compareTo(0)==1 && oporMatrix[tmpX-2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-2][tmpY-1].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-1].compareTo(oporMatrix[tmpX-2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-1];
                else
                    minWeight = oporMatrix[tmpX-2][tmpY];
                tmpX1 = tmpX-2; tmpY1 = tmpY-1; tmpX2 = tmpX-2; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-1;
            }
            if (oporMatrix[tmpX][tmpY-2].compareTo(0)==1 && oporMatrix[tmpX-2][tmpY].compareTo(0)==1 && !flag){
                if (oporMatrix[tmpX-2][tmpY-2].compareTo(0)==1)
                    flag = true;
                if (oporMatrix[tmpX][tmpY-2].compareTo(oporMatrix[tmpX-2][tmpY])==-1)
                    minWeight = oporMatrix[tmpX][tmpY-2];
                else
                    minWeight = oporMatrix[tmpX-2][tmpY];
                tmpX1 = tmpX-2; tmpY1 = tmpY-2; tmpX2 = tmpX-2; tmpY2 = tmpY; tmpX3 = tmpX; tmpY3 = tmpY-2;
            }
        }

        //System.out.println(tmpX+" "+ tmpY+"     "+tmpX1+" "+tmpY1+"          "+tmpX2+" "+tmpY2+"    "+tmpX3+" "+tmpY3+"     "+minWeight);

        oporMatrix[tmpX][tmpY] += minWeight;
        oporMatrix[tmpX1][tmpY1] += minWeight;
        oporMatrix[tmpX2][tmpY2] -= minWeight;
        oporMatrix[tmpX3][tmpY3] -= minWeight;

        return oporMatrix;
    }

    public Boolean checkLoop(int x1, int x2, int x3){
        return (x1>0 && x2>0 && x3>0);
    }
}
