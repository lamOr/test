package Main;

import javax.microedition.lcdui.*;

/**
 *
 * @author lam
 */
public class Setting implements CommandListener,ItemStateListener{

      private Form settingForm;
      private Command exitCommand;
      private Command backCommand;
      private Command okCommand;

      private Gauge volume;
      private Gauge difficulty;

      private StringItem difficultyStr = new StringItem("Current:","normal");

      public Setting(){
        exitCommand = new Command("Exit", Command.EXIT, 0);
        okCommand = new Command("OK",Command.OK,0);
        backCommand = new Command("Back",Command.BACK,0);
      }

      public void showSettingMenu(){
        if(settingForm == null){
            settingForm = new Form("setting Form");

            volume = new Gauge("Volume",true,6,3);
            volume.setLayout(Item.LAYOUT_CENTER);
            settingForm.append(volume);

            difficulty = new Gauge("Difficulty",true,2,1);
            difficulty.setLayout(Item.LAYOUT_CENTER);
            settingForm.append(difficulty);
            settingForm.append(difficultyStr);

            settingForm.addCommand(backCommand);
            settingForm.addCommand(exitCommand);
            settingForm.setCommandListener(this);
            settingForm.setItemStateListener(this);
        }
        TouristMIDlet.getDisplay().setCurrent(settingForm);
    }

    public void commandAction(Command c, Displayable d) {
        if (c.getCommandType() == Command.EXIT )
            TouristMIDlet.getInstance().notifyDestroyed();
        if (c.getCommandType() == Command.BACK)
            TouristMIDlet.getInstance().getMenu().showMainMenu();
    }

    public int getVolume(){
        return volume.getValue();
    }

    public int getDifficulty(){
        return difficulty.getValue();
    }

    public void itemStateChanged(Item item) {
        if(item == difficulty){
            int diff = difficulty.getValue();
            if(diff == 0 )
                difficultyStr.setText("easy");
            else if (diff == 1)
                difficultyStr.setText("normal");
            else
                difficultyStr.setText("difficult");

        }

    }
}
