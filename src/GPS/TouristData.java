package GPS;


import GOLF.Play;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;
import GUI.TouristUI;
import javax.microedition.location.Coordinates;

/**
 * Model class that handles LocationListeners and ProximityListeners events.
 */
public class TouristData implements LocationListener//, ProximityListener
{
    /** A Reference to Tourist UI Canvas */
    private TouristUI touristUI = null;

    /** Coordinate detection threshold radius in meters */
    public static final float PROXIMITY_RADIUS = 100.0f;

    /** Previous coordinates outside the distance threshold area (20m) */
    private Coordinates prevCoordinates = null;

    /** The first location update done. */
    private boolean firstLocationUpdate = false;

    private ProviderStatusListener statusListener = null;
    private Play play;

    /**
     * Construct instance of this model class.
     */
    public TouristData(ProviderStatusListener listener)
    {   play = new Play();
        statusListener = listener;

        ConfigurationProvider config = ConfigurationProvider.getInstance();

        // 1. Register LocationListener
        LocationProvider provider = config.getSelectedProvider();
        if (provider != null)
        {
            int interval = -1; // default interval of this provider
            int timeout = 0; // parameter has no effect.
            int maxage = 0; // parameter has no effect.
            provider.setLocationListener(this, interval, timeout, maxage);
        }
    }

    /**
     * Setter method to TouristUI reference.
     * 
     * @param ui -
     *            Reference to TouristUI.
     */
    public void setTouristUI(TouristUI ui)
    {
        touristUI = ui;
    }

    /**
     * Adds new ProximityListener to the location provider. This method is
     * called when constructing instance of this calss and when a new landmark
     * is saved by using LandmarkEditorUI.
     * 
     * @param coordinates
     */
    /*
    public void createProximityListener(Coordinates coordinates)
    {
        try
        {
            LocationProvider.addProximityListener(this, coordinates,
                    PROXIMITY_RADIUS);
        }
        catch (LocationException e)
        {
            // Platform does not have resources to add a new listener
            // and coordinates to be monitored or does not support
            // proximity monitoring at all
        }
    }*/

    /**
     * locationUpdated event from LocationListener interface. This method starts
     * a new thread to prevent blocking, because listener method MUST return
     * quickly and should not perform any extensive processing.
     * 
     * Location parameter is set final, so that the anonymous Thread class can
     * access the value.
     */
    public void locationUpdated(LocationProvider provider,
            final Location location)
    {
        // First location update arrived, so we may show the UI (TouristUI)
        if (!firstLocationUpdate)
        {
            firstLocationUpdate = true;
            statusListener.firstLocationUpdateEvent();
        }

        if (touristUI != null)
        {
            new Thread()
            {
                public void run()
                {
                    if (location != null && location.isValid())
                    {
                        QualifiedCoordinates coord = location.getQualifiedCoordinates();
                        float speed = location.getSpeed();
                        ////////////////////////////////////
                        play.playGame(coord.getLatitude(),coord.getLongitude(), speed, location.getCourse());                       
                        touristUI.setInfo(coord, speed);
                        touristUI.setBall(play.getBallPosition());
                        touristUI.setProviderState("Available");
                        touristUI.repaint();
                    }
                    else
                    {
                        touristUI.setProviderState("Not valid location data");
                        touristUI.repaint();
                    }
                }
            }.start();
        }
    }

    /**
     * providerStateChanged event from LocationListener interface. This method
     * starts a new thread to prevent blocking, because listener method MUST
     * return quickly and should not perform any extensive processing.
     * 
     * newState parameter is set final, so that the anonymous Thread class can
     * access the value.
     */
    public void providerStateChanged(LocationProvider provider,
            final int newState)
    {
        if (touristUI != null)
        {
            new Thread()
            {
                public void run()
                {
                    switch (newState) {
                        case LocationProvider.AVAILABLE:
                            touristUI.setProviderState("Available");
                            break;
                        case LocationProvider.OUT_OF_SERVICE:
                            touristUI.setProviderState("Out of service");
                            break;
                        case LocationProvider.TEMPORARILY_UNAVAILABLE:
                            touristUI.setProviderState("Temporarily unavailable");
                            break;
                        default:
                            touristUI.setProviderState("Unknown");
                            break;
                    }

                    touristUI.repaint();
                }
            }.start();
        }
    }

    /**
     * proximity event from ProximityListener interface. The listener is called
     * only once when the terminal enters the proximity of the registered
     * coordinates. That's why no this method should not need to start a new
     * thread.
     */
    /*
    public void proximityEvent(Coordinates coordinates, Location location)
    {
        if (touristUI != null)
        {
            touristUI.setProviderState("Control point found!");

           /* Landmark lm = ControlPoints.getInstance().findNearestLandMark(
                   coordinates);

            // landmark found from landmark store
            if (lm != null)
            {
                touristUI.setInfo(lm.getAddressInfo(), lm
                        .getQualifiedCoordinates(), location.getSpeed());
            }
            // landmark not found from the landmark store, this should not never
            // happen!
            else
            {
                touristUI.setInfo(location.getAddressInfo(), location
                        .getQualifiedCoordinates(), location.getSpeed());
            }

            touristUI.repaint();
        }
    }
    */
    /**
     * monitoringStateChanged event from ProximityListener interface. Notifies
     * that the state of the proximity monitoring has changed. That's why this
     * method should not need to start a new thread.
     */
    /*
    public void monitoringStateChanged(boolean isMonitoringActive)
    {
        if (touristUI != null)
        {
            if (isMonitoringActive)
            {
                // proximity monitoring is active
                touristUI.setProximityState("Active");
            }
            else
            {
                // proximity monitoring can't be done currently.
                touristUI.setProximityState("Off");
            }

            touristUI.repaint();
        }
    }*/
}
