public class Main {
    public static void main(String[] args) {
        TSPFileReader tspFileReader = new TSPFileReader();
        tspFileReader.TSPReadFile();
        BranchAndBound branchAndBound = new BranchAndBound();
        branchAndBound.runTSP(tspFileReader);

    }
}
