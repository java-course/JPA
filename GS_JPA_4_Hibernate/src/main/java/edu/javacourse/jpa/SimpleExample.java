package edu.javacourse.jpa;

import edu.javacourse.jpa.entity.Region;
import edu.javacourse.jpa.manager.RegionManager;
import java.util.List;

public class SimpleExample {

    public static void main(String[] args) {
        RegionManager rm = new RegionManager();
        rm.init();
        firstSelect(rm);
        secondSelect(rm);
    }

    private static void firstSelect(RegionManager rm) {
        System.out.println("First Select ===>");
        List<Region> result = rm.getRegionList();
        for(Region r : result) {
            System.out.println(r);
        }
    }
    private static void secondSelect(RegionManager rm) {
        System.out.println("Second Select ===>");
        List<Region> result = rm.getRegionList2();
        for(Region r : result) {
            System.out.println(r);
        }
    }
}
