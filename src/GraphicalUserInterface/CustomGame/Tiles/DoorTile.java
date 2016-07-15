package GraphicalUserInterface.CustomGame.Tiles;

/**
 * Created by rezab on 11/07/2016.
 */
public class DoorTile extends Tiles {

    public enum Direction {upward, downward, leftward, rightward}
    private Direction direction;
    private int number;
    private boolean isLocked;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }


    public DoorTile(Direction direction, int number) {
        this.direction = direction;
        this.number = number;
    }

}
