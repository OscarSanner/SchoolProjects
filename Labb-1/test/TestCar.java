import Labb.*;
import org.junit.Test;

public class TestCar {
    private Volvo240 v1;
    private Saab95 s1;

    @Test
    public void testTurnLeftVolvo(){
        v1 = new Volvo240();
        v1.turnLeft();
        assert v1.getCurrentDirection() == Direction.LEFT;
    }

    @Test
    public void testRotateLeftVolvo(){
        v1 = new Volvo240();
        Direction d = v1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            v1.turnLeft();
        }
        assert v1.getCurrentDirection() == d;
    }

    @Test
    public void testTurnRightVolvo(){
        v1 = new Volvo240();
        v1.turnRight();
        assert v1.getCurrentDirection() == Direction.RIGHT;
    }

    @Test
    public void testRotateRightVolvo(){
        v1 = new Volvo240();
        Direction d = v1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            v1.turnRight();
        }
        assert v1.getCurrentDirection() == d;
    }

    @Test
    public void testMoveUp(){
        v1 = new Volvo240();
        double i = v1.getY();
        v1.startEngine();
        v1.move();
        assert v1.getY() > i;
    }

    @Test
    public void testMoveLeft(){
        v1 = new Volvo240();
        double i = v1.getX();
        v1.startEngine();
        v1.turnLeft();
        v1.move();
        assert v1.getX() < i;
    }

    @Test
    public void testMoveDown(){
        v1 = new Volvo240();
        double i = v1.getY();
        v1.startEngine();
        v1.turnLeft();
        v1.turnLeft();
        v1.move();
        assert v1.getY() < i;
    }

    @Test
    public void testMoveRight(){
        v1 = new Volvo240();
        double i = v1.getX();
        v1.startEngine();
        v1.turnRight();
        v1.move();
        assert v1.getX() > i;
    }

    @Test
    public void testTurnLeftSaab(){
        s1 = new Saab95();
        s1.turnLeft();
        assert s1.getCurrentDirection() == Direction.LEFT;
    }

    @Test
    public void testRotateLeftSaab(){
        s1 = new Saab95();
        Direction d = s1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            s1.turnLeft();
        }
        assert s1.getCurrentDirection() == d;
    }

    @Test
    public void testTurnRightSaab(){
        s1 = new Saab95();
        s1.turnRight();
        assert s1.getCurrentDirection() == Direction.RIGHT;
    }

    @Test
    public void testRotateRightSaab(){
        s1 = new Saab95();
        Direction d = s1.getCurrentDirection();
        for (int i = 0; i < 4; i++){
            s1.turnRight();
        }
        assert s1.getCurrentDirection() == d;
    }

    @Test
    public void testMoveUpSaab(){
        s1 = new Saab95();
        double i = s1.getY();
        s1.startEngine();
        s1.move();
        assert s1.getY() > i;
    }

    @Test
    public void testMoveLeftSaab(){
        s1 = new Saab95();
        double i = s1.getX();
        s1.startEngine();
        s1.turnLeft();
        s1.move();
        assert s1.getX() < i;
    }

    @Test
    public void testMoveDownSaab(){
        s1 = new Saab95();
        double i = s1.getY();
        s1.startEngine();
        s1.turnLeft();
        s1.turnLeft();
        s1.move();
        assert s1.getY() < i;
    }

    @Test
    public void testMoveRightSaab(){
        s1 = new Saab95();
        double i = s1.getX();
        s1.startEngine();
        s1.turnRight();
        s1.move();
        assert s1.getX() > i;
    }
}
