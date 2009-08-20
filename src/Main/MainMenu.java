package Main;

import javax.microedition.lcdui.*;
import java.io.IOException;
/**
 *
 * @author lam
 */
public class MainMenu  implements CommandListener, ItemCommandListener{

  private Form mForm;
  private Command exitCommand;
  private Command backCommand;
  private Command okCommand;
  private ImageItem item1;
  private StringItem stringItem ,stringItem2;

      public MainMenu(){
        exitCommand = new Command("Exit", Command.EXIT, 0);
        okCommand = new Command("OK",Command.OK,0);
        backCommand = new Command("Back",Command.BACK,0);
      }

    public void showMainMenu(){
        if(mForm == null){
                mForm = new Form("main Form");
                Image image1;
                try {
                    image1 = Image.createImage("/start.jpg");
                    item1 = new ImageItem("start golf game",image1,ImageItem.LAYOUT_CENTER,"Image not found");
                } catch (IOException ex) {
                    System.out.println("IO exception: "+ex);
                }
                stringItem = new StringItem("Setting","");
                stringItem2 = new StringItem("Score","");

                item1.addCommand(okCommand);
                stringItem.addCommand(okCommand);
                stringItem2.addCommand(okCommand);
                item1.setItemCommandListener(this);
                stringItem.setItemCommandListener(this);
                stringItem2.setItemCommandListener(this);

                mForm.insert(0, item1);
                mForm.insert(1, stringItem);
                mForm.insert(2, stringItem2);
                mForm.addCommand(exitCommand);
                mForm.setCommandListener(this);
        }
        TouristMIDlet.getDisplay().setCurrent(mForm);
    }


    public void commandAction(Command c, Displayable d) {
        if (c.getCommandType() == Command.EXIT )
            TouristMIDlet.getInstance().notifyDestroyed();
    }

    public void commandAction(Command c, Item item) {

        if(item == stringItem)
            TouristMIDlet.getInstance().getSetting().showSettingMenu();
        else if(item==item1){
            TouristMIDlet.getInstance().start();
        }
        else if(item==stringItem2){
             TouristMIDlet.getInstance().getScore().showScoreMenu();
        }
        
    }
}
