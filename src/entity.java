import java.awt.*;
public class entity {
    public int randomNumGen1 = (int)(Math.random()*2);
    public int randomNumGen2 = (int)(Math.random()*2);
    public String name;
    public int x;
    public int y;
    public int dx;
    public int dy;
    public int height = 100;
    public int width = 100;
    public Rectangle rec;
    public boolean isAlive = true;
    public entity(String paramName,int paramX,int paramY){
        name = paramName;
        x = paramX;
        y = paramY;
        rec = new Rectangle(x,y,width,height);
    }
    public void bounce(){
        x += dx;
        y += dy;
        if(x>900||x<0){
            dx = -dx;
        }
        if(y>600||y<0){
            dy = -dy;
        }
        rec = new Rectangle(x,y,width,height);
    }
    public void wrap(){
        x=x+dx;
        y=y+dy;
        if(x>1000){
            x=0-width;
        }
        if(x<0-width){
            x=1000;
        }
        if(y>700){
            y=0-width;
        }
        if(x<0-width){
            y=700;
        }
        rec = new Rectangle(x,y,width,height);
    }
    public void determineDx() {
        if (randomNumGen1 == 0){
            dx = -((int)(Math.random()*2+1));
        }
        else {
         dx = (int)(Math.random()*2+1);
        }
    }
    public void determineDy() {
        if (randomNumGen2 == 0){
            dy = -((int)(Math.random()*2+1));
        }
        else {
            dy = (int)(Math.random()*2+1);
        }
    }
}
