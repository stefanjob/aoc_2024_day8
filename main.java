import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class main {   

    private static String[] map = null;
    
    public static int countUniqueAntinodes(String[] map) {
        int rows = map.length;
        int cols = map[0].length();

        // Store antenna locations grouped by frequency
        Map<Character, List<int[]>> antennaLocations = new HashMap<>();

        // Populate antenna locations
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = map[r].charAt(c);
                if (ch != '.') {
                    antennaLocations.putIfAbsent(ch, new ArrayList<>());
                    antennaLocations.get(ch).add(new int[]{r, c});
                }
            }
        }

        // Set to store unique antinode locations
        Set<String> antinodes = new HashSet<>();

        // Calculate antinodes for each frequency group
        for (Map.Entry<Character, List<int[]>> entry : antennaLocations.entrySet()) {
            List<int[]> antennas = entry.getValue();

            // Check all pairs of antennas of the same frequency
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = 0; j < antennas.size(); j++) {
                    if (i == j) continue;

                    int[] a1 = antennas.get(i);
                    int[] a2 = antennas.get(j);

                    int dr = a2[0] - a1[0];
                    int dc = a2[1] - a1[1];

                    // Check if a2 is twice as far from a1
                    int midR = a1[0] - dr;
                    int midC = a1[1] - dc;

                    if (isInBounds(midR, midC, rows, cols)) {
                        antinodes.add(midR + "," + midC);
                    }

                    int farR = a2[0] + dr;
                    int farC = a2[1] + dc;

                    if (isInBounds(farR, farC, rows, cols)) {
                        antinodes.add(farR + "," + farC);
                    }
                }
            }
        }

        return antinodes.size();
    }

    public static int countUniqueAntinodes2(String[] map) {
        int rows = map.length;
        int cols = map[0].length();

        // Store antenna locations grouped by frequency
        Map<Character, List<int[]>> antennaLocations = new HashMap<>();

        // Populate antenna locations
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = map[r].charAt(c);
                if (ch != '.') {
                    antennaLocations.putIfAbsent(ch, new ArrayList<>());
                    antennaLocations.get(ch).add(new int[]{r, c});
                }
            }
        }

        // Set to store unique antinode locations
        Set<String> antinodes = new HashSet<>();

        // Calculate antinodes for each frequency group
        for (Map.Entry<Character, List<int[]>> entry : antennaLocations.entrySet()) {
            List<int[]> antennas = entry.getValue();

            // Add all antenna locations as antinodes
            for (int[] antenna : antennas) {
                antinodes.add(antenna[0] + "," + antenna[1]);
            }

            // Check all pairs of antennas of the same frequency
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    int[] a1 = antennas.get(i);
                    int[] a2 = antennas.get(j);

                    // Mark all grid positions in line with both antennas
                    int dr = a2[0] - a1[0];
                    int dc = a2[1] - a1[1];

                    int nextR = a1[0];
                    int nextC = a1[1];

                    while (isInBounds(nextR, nextC, rows, cols)) {
                        antinodes.add(nextR + "," + nextC);
                        nextR += dr;
                        nextC += dc;
                    }

                    nextR = a1[0] - dr;
                    nextC = a1[1] - dc;

                    while (isInBounds(nextR, nextC, rows, cols)) {
                        antinodes.add(nextR + "," + nextC);
                        nextR -= dr;
                        nextC -= dc;
                    }
                }
            }
        }

        return antinodes.size();
    }

    private static boolean isInBounds(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
    
    public static void part1() {
        System.out.println("AoC Day 8 Part 1");
       
        boolean full = false; 
        List<String> lines = new ArrayList<>();

        try {
            if (full) {
                lines = Files.readAllLines(Paths.get("input_full.txt"));
            } else {
                lines = Files.readAllLines(Paths.get("input_test.txt"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    
        map = new String[lines.size()];
        map = lines.toArray(String[]::new);
          
        System.out.println("Unique antinode locations: " + countUniqueAntinodes(map));
    }

    public static void part2() {
        System.out.println("AoC Day 8 Part 2");
       
        boolean full = true; 
        List<String> lines = new ArrayList<>();

        try {
            if (full) {
                lines = Files.readAllLines(Paths.get("input_full.txt"));
            } else {
                lines = Files.readAllLines(Paths.get("input_test.txt"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    
        map = new String[lines.size()];
        map = lines.toArray(String[]::new);
          
        System.out.println("Unique antinode locations: " + countUniqueAntinodes2(map));
    }
 
   public static void main(String[] args) {
    part2();
   }
}