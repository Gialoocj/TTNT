import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class TSPFileReader {
    private int numVertices;
    private int[][] weightMatrix;
    private String[] vertexNames;
    private HashMap<Integer,String>vertexName;
    private LocalTime startTime;
    private String startPoint;

    public void TSPReadFile(){

        try {
            File file = new File("E:\\Java\\data.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            vertexName = new HashMap<>();

            // số đỉnh
            numVertices = Integer.parseInt(bufferedReader.readLine());

            //tên các đỉnh
            vertexNames = bufferedReader.readLine().split(" ");
            for(int i = 0;i<numVertices;i++){
                vertexName.put(i, vertexNames[i]);
            }

            // Tạo ma trận tr�?ng số
            weightMatrix = new int[numVertices][numVertices];
            for (int i = 0; i < numVertices; i++) {
                String[] weights = bufferedReader.readLine().split(" ");
                for (int j = 0; j < numVertices; j++) {
                    weightMatrix[i][j] = Integer.parseInt(weights[j]);
                }
            }
            //điểm bắt đầu
            startPoint = bufferedReader.readLine();

            // th�?i gian bắt đầu
            String timeString = bufferedReader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            timeString = timeString.trim();
            startTime = LocalTime.parse(timeString, formatter);

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int[][] getWeightMatrix() {
        return weightMatrix;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public HashMap<Integer,String> getVertexName(){
        return vertexName;
    }

    public String getStartPoint(){
        return startPoint;
    }

}
