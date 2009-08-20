package Main;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import GPS.ConfigurationProvider;
import GPS.ProviderStatusListener;
import GPS.TouristData;
import GUI.MessageUI;
import GUI.TouristUI;
import javax.microedition.lcdui.*;

/**
 * Tourist Route MIDlet class.
 */
public class TouristMIDlet extends MIDlet implements ProviderStatusListener{

    private static TouristMIDlet instance;
    /** A static reference to Display object. */
    private static Display display = null;
    /** A Reference to TouristData. */
    private TouristData data = null;
    /** Lock object */
    private Object mutex = new Object();

    private MainMenu menu = new MainMenu();
    private Setting set = new Setting();
    private Score score = new Score();
    public TouristMIDlet() {
        instance=this;
    }


    protected void startApp() throws MIDletStateChangeException {
        if (instance == null)
            instance = getInstance();
        if (display == null)
            display = Display.getDisplay(this);
        if (display.getCurrent() == null)
            menu.showMainMenu();
        //display = Display.getDisplay(this);

    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

    /**
     * Getter method for Display reference.
     *
     * @return reference to Display object.
     */
    public static Display getDisplay() {
        return display;
    }

    public static TouristMIDlet getInstance() {
        if (instance == null)
            instance = new TouristMIDlet();
        return instance;
    }

    public MainMenu getMenu(){
        return menu;
    }

    public Setting getSetting(){
        return set;
    }
    public Score getScore(){
        return score;
    }
    /**
     * Event indicating location provider is selected. MIDlet use may therefore
     * begin.
     *
     * @see com.nokia.example.location.tourist.model.ProviderSelectedListener#providerSelectedEvent()
     */
    public void providerSelectedEvent() {
        // Attempt to acquire the mutex
        synchronized (mutex) {
            // Start scanning location updates. Also set the TouristData
            // reference data.
            MessageUI.showLocationProviderState();

            // Inform the user that MIDlet is looking for location data.
            data = new TouristData((ProviderStatusListener) this);
        }
    }

    /**
     * Event indication about the first location update. This method sets
     * Tourist UI visible. By using mutex object, we ensure TouristaData (data)
     * is created on providerSelectedEvent.
     *
     * @see com.nokia.example.location.tourist.model.ProviderStatusListener#firstLocationUpdateEvent()
     */
    public void firstLocationUpdateEvent() {
        // Attempt to acquire the mutex
        synchronized (mutex) {
            TouristUI ui = new TouristUI(data);
            data.setTouristUI(ui);
            display.setCurrent(ui);
        }
    }
    
    public void start(){
        System.out.println("starting now");
        new GPSThread(this).start();
    }

    private class GPSThread extends Thread {

        private ProviderStatusListener psl;

        public GPSThread(ProviderStatusListener psl) {
            this.psl = psl;
        }

        public void run() {
             if (ConfigurationProvider.isLocationApiSupported()) {
                ConfigurationProvider.getInstance().autoSearch(psl);
            } else {
                MessageUI.showApiNotSupported();
            }
        }


    }

}