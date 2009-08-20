package GUI;

import GOLF.Point;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.location.QualifiedCoordinates;
import Main.Utils;
import GPS.TouristData;
import Main.TouristMIDlet;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Image;

/**
 * Viewer class that renders current location updates.
 */
public class TouristUI extends Canvas implements CommandListener
{
    /** The current state of the location provider as a String */
    private String providerState = "Unknown";

    /** Proximity monitoring state. */
    private String proximityState = "Waiting";


    private QualifiedCoordinates coord;

    private float speed;

    /** Command that shows compass canvas */
    private Command back = new Command("BACK", Command.BACK, 1);

   Image mapImage,player,ball;
   double lat1,lat2,log1,log3,latball,logball;

    /** Command that shows Landmark editor UI */
  //  private Command editorCmd = new Command("Editor", Command.STOP, 1);

    /** Rerefence to the Landmark editor UI */

    /** Rerefence to the Compass UI */

    public TouristUI(TouristData data)
    {
        setCommandListener(this);
        addCommand(back);
    //    addCommand(editorCmd);
    }

    /*
    public static TouristUI getTouristUI(){
        return this;
    }*/
    
    public void setProviderState(String state)
    {
        providerState = state;
    }

    public void setProximityState(String state)
    {
        proximityState = state;
    }

    public void setInfo( QualifiedCoordinates coord,float speed)
    {
        this.coord = coord;
        this.speed = speed;
    }

    /**
     * Renders the canvas.
     * 
     * @param g -
     *            the Graphics object to be used for rendering the Canvas
     */
    protected void paint(Graphics g)
    {
        Font f = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
                Font.SIZE_SMALL);
        g.setFont(f);

        // use font height as a line height
        int lineHeight = f.getHeight();
        // current line counter
        int line = 0;

        // clean the backround
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
//51.170119027186,-115.5625483348
        try {
                setMap(51.170563,51.168081,-115.563308,-115.555991);        
                drawMap(g);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        g.setColor(0x000000);
        g.drawString("Provider state: " + providerState, 0, lineHeight*(line++), Graphics.LEFT | Graphics.TOP);
        if (coord != null)
        {
            double lat = coord.getLatitude();
            double lon = coord.getLongitude();

            g.drawString("Lat:" + Utils.formatDouble(lat, 9), 0, lineHeight* (line++), Graphics.TOP | Graphics.LEFT);
            g.drawString("Lon:" + Utils.formatDouble(lon, 9), 0, lineHeight* (line++), Graphics.TOP | Graphics.LEFT);
            g.drawString("Speed: " + Utils.formatDouble(speed, 9) + " m/s", 0,
                    lineHeight * (line++), Graphics.TOP | Graphics.LEFT);
        }
        /////////////////////////////////
        
        drawDirection(g,30);
        
    }
    public void setMap(double lat1,double lat2,double log1,double log3){
        this.lat1=lat1;
        this.lat2=lat2;
        this.log1=log1;
        this.log3=log3;
       
    }
    public void setBall(Point point){
        this.latball=point.getLat();
        this.logball=point.getLog();
    }

    public void drawMap(Graphics g) throws IOException{

        if(providerState.equalsIgnoreCase("Available")&&coord != null){
                mapImage = Image.createImage("/BanffMap.jpg");
                player = Image.createImage("/visitoron.png");
                ball = Image.createImage("/ball.png");

                double width=mapImage.getWidth();
                double height=mapImage.getHeight();
                double coodX=Math.abs(log1-coord.getLongitude())/Math.abs(log3-log1)*width-getWidth()/3;
                double coodY=Math.abs(lat1-coord.getLatitude())/Math.abs(lat2-lat1)*height-getHeight()/2;
                double coodBallX = Math.abs(log1-logball)/Math.abs(log3-log1)*width-coodX;
                double coodBallY = Math.abs(lat1-latball)/Math.abs(lat2-lat1)*height-coodY;
                g.drawImage(mapImage,(int)-coodX, (int)-coodY, Graphics.TOP | Graphics.LEFT);
                g.drawImage(player, getWidth()/3, getHeight()/2, Graphics.VCENTER | Graphics.HCENTER);

                g.drawImage(ball,(int)coodBallX,(int)coodBallY,Graphics.VCENTER | Graphics.HCENTER);

        }
        else{
                player = Image.createImage("/visitoroff.png");
                g.drawImage(player, getWidth()/3, getHeight()/2, Graphics.VCENTER | Graphics.HCENTER);
        }
    }
    
    public void drawDirection(Graphics g,double degree){
        int radiu=getWidth()/4;
        int x = getWidth()/2;
        int y = getHeight()/2;
        int cx = x + radiu/2;
        int cy = y + radiu/2;
        g.setColor(255, 0, 0);
        g.drawArc(x, y, radiu,radiu, 0, 360);
        g.setColor(0, 0, 255);
        g.drawArc(x-2, y-2, radiu+4, radiu+4, 0, 360);
        g.setColor(0, 0, 0);

        double rad = Math.toRadians(degree);
        double rad2 = Math.toRadians(degree + 165);
        double rad3 = Math.toRadians(degree - 165);
        g.fillTriangle((int)(cx + (radiu*Math.sin(rad))/2), (int)(cy - (radiu*Math.cos(rad))/2),
            (int)(cx + (radiu*Math.sin(rad2))/2), (int)(cy - (radiu*Math.cos(rad2))/2),
            (int)(cx + (radiu*Math.sin(rad3))/2), (int)(cy - (radiu*Math.cos(rad3))/2));
    }

    public void commandAction(Command c, Displayable d) {
        if(c==back){
            TouristMIDlet.getInstance().getMenu().showMainMenu();
        }
    }

}