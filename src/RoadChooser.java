public class RoadChooser {
    public static void main(String[] args) {
        Integer[] makeArr = {2000, 1700, 1600};
        Integer[] needArr = {2000, 1000, 2300};
        Integer[][] priceMatrix = {{3637,3043,4386},
                                   {3793,3165,4711},
                                   {4509,3714,5607}};
        MyChooser newChooser = new MyChooser();
        //Integer[][] oporArr = newChooser.methodMinimalElement(makeArr,needArr);
        Integer[][] oporArr = newChooser.methodNorthEast(makeArr,needArr);
        System.out.println("Опорная матрица");
        for (int i =0; i<3; i++){
            for (int j=0; j<3; j++){
                System.out.print(oporArr[i][j]+" ");
            }
            System.out.print("\n");
        }

        int x=1;
        //System.out.println(newChooser.checkPlan(oporArr,priceMatrix));
        while (!newChooser.checkPlan(oporArr,priceMatrix)){
            System.out.println("\nШаг "+x); x++;
            oporArr = newChooser.getNewMatrix(oporArr);
            System.out.println("Опорная матрица (измененная)");
            for (int i =0; i<3; i++){
                for (int j=0; j<3; j++){
                    System.out.print(oporArr[i][j]+" ");
                }
                System.out.print("\n");
            }
        }

        System.out.println("\nИтог:");
        for (int i =0; i<3; i++){
            for (int j=0; j<3; j++){
                System.out.print(oporArr[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
}
