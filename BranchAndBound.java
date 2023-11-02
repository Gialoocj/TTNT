import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BranchAndBound {
    private int numVertices; // số đỉnh
    private int[][] weightMatrix;// ma trận trọng số
    private HashMap<Integer, String> vertexName;// tên các đỉnh
    private LocalTime starTime; // thời gian bắt đầu
    private LocalTime endTime;// thời gian kết thúc
    private static boolean[] visited; // kiểm tra đã đến thành phố hay chưa
    private static int bestCost;// chi phí tốt nhất
    private static ArrayList<Integer> curentPath;// đường đi hiện tại
    private int startPoint;

    private void initalize(TSPFileReader tspFileReader) {

        numVertices = tspFileReader.getNumVertices();
        weightMatrix = new int[numVertices][numVertices];
        visited = new boolean[numVertices];
        curentPath = new ArrayList<>();
        vertexName = new HashMap<>();

        weightMatrix = tspFileReader.getWeightMatrix();
        vertexName = tspFileReader.getVertexName();
        starTime = tspFileReader.getStartTime();

        for (HashMap.Entry<Integer, String> entry : vertexName.entrySet()) {
            if (entry.getValue().equals(tspFileReader.getStartPoint())) {
                startPoint = entry.getKey();
                break;
            }
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        visited[startPoint] = true;
        curentPath.add(startPoint);
    }

    // Tính toán giá trị cận dưới
    private int lowerBound(ArrayList<Integer> curentPath) {
        int lb = 0;

        // Tính tổng trọng số của các cạnh đã đi qua
        for (int i = 0; i < curentPath.size() - 1; i++) {
            lb += weightMatrix[curentPath.get(i)][curentPath.get(i + 1)];
        }

        // Tính trọng số của cạnh nhỏ nhất từ đỉnh cuối về đỉnh đầu
        lb += weightMatrix[curentPath.get(curentPath.size() - 1)][startPoint];

        // Đánh giá cận dưới
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                int min = Integer.MAX_VALUE;
                for (int j = 0; j < numVertices; j++) {
                    if (weightMatrix[i][j] < min) {
                        min = weightMatrix[i][j];
                    }
                }
                lb += min;
            }
        }

        return lb;
    }

    // Thuật toán nhánh cận
    private void tspBranchAndBound(int currentVertex, int currentCost, int level, ArrayList<Integer> currentPath) {
        if (level == numVertices - 1) {
            int totalCost = currentCost + weightMatrix[currentVertex][startPoint];
            if (totalCost < bestCost) {
                bestCost = totalCost;
                curentPath = new ArrayList<>(currentPath);
                curentPath.add(startPoint);
            }
            return;
        }

        for (int nextVertex = 0; nextVertex < numVertices; nextVertex++) {
            if (!visited[nextVertex] && weightMatrix[currentVertex][nextVertex] > 0) {
                visited[nextVertex] = true;
                currentPath.add(nextVertex);

                int lb = lowerBound(currentPath);
                if (currentCost + weightMatrix[currentVertex][nextVertex] + lb < bestCost) {
                    tspBranchAndBound(nextVertex, currentCost + weightMatrix[currentVertex][nextVertex], level + 1,
                            currentPath);
                }

                visited[nextVertex] = false;
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    // Khởi chạy thuật toán
    public void runTSP(TSPFileReader tspFileReader) {
        initalize(tspFileReader);
        bestCost = Integer.MAX_VALUE;

        tspBranchAndBound(startPoint, 0, 1, curentPath);

        // In đường đi tối ưu và chi phí tối ưu
        System.out.println("Duong di toi uu:");
        for (Integer vertex : curentPath) {
            System.out.print(vertexName.get(vertex) + " -> ");
        }
        System.out.println(vertexName.get(startPoint));
        System.out.println("Chi phí toi uu: " + bestCost);
    }

}